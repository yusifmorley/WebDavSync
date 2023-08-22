package com.yusif.service.WebDav.WebDavSubFunction.FileOperation;

import com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService.FileInsert;
import com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService.InsertAndCons;
import com.yusif.service.WebDav.WebDavSubFunction.WebDavSubFunctionMonitor;
import com.yusif.config.FileOperationConfig;
import com.yusif.config.SingleBean;
import com.yusif.constant.Type;
import com.yusif.Entity.File.FileUpdate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
public class FileMonitor implements WebDavSubFunctionMonitor {
    @Autowired
    InsertAndCons insertAndCons;

    LinkedBlockingQueue<FileUpdate> linkedBlockingQueue = SingleBean.getBlockingFileQueue();

    private  FileAlterationMonitor monitor;
    private FileOperationConfig fileOperationConfig;
    @Autowired
    public FileMonitor(FileOperationConfig fileOperationConfig, FileInsert fileInsert){
        this.fileOperationConfig = fileOperationConfig;
        this.monitor=new FileAlterationMonitor(fileOperationConfig.getInterval());
    }
    private void init(){ //注册监听器
        List<String> obpicandmp4 = fileOperationConfig.getObPicAndMp4();
        obpicandmp4.forEach(e->{
//            log.info("监听器生成了");
            FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(e);
            fileAlterationObserver.addListener(new FileAlterationListenerAdaptor(){
                @Override
                public void onFileCreate(File file) {
                        log.info("有新文件生成！");
                        FileUpdate fileUpdate = new FileUpdate();
                        fileUpdate.setFile(file);
                        if (file.getName().endsWith("mp4"))
                            fileUpdate.setType(Type.MP4);
                        else
                            fileUpdate.setType(Type.Pic);
                        linkedBlockingQueue.add(fileUpdate);
                }
            }
            );
            monitor.addObserver(fileAlterationObserver);
            log.info(String.format("为%s创建监听成功",e));
        });
    }


    @Override
    public void monitorInit() throws Exception {
        init();
    }

    @Override
    public void monitorStart() throws Exception {
        monitor.start();
        log.info("开始监听");
    }

    @Override
    public void monitorStop() throws Exception {
        monitor.stop();
    }
}
