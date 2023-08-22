package com.yusif.service.WebDav.WebDavSubFunction.MyNote;

import com.yusif.dao.MyNoteMapper;

import com.yusif.Entity.note.UpDateNote;
import com.yusif.config.FileOperationConfig;
import com.yusif.config.SingleBean;
import com.yusif.service.WebDav.WebDavSubFunction.WebDavSubFunctionMonitor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
@Slf4j
@Component
public class MyNoteMonitor implements WebDavSubFunctionMonitor {

    private  FileAlterationMonitor monitor;
    private LinkedBlockingQueue<UpDateNote> linkedBlockingQueue= SingleBean.getBlockingNoteQueue();
    @Autowired
    MyNoteMapper myNoteMapper;

    @Autowired
    FileOperationConfig fileOperationConfig;
    @Autowired
    public MyNoteMonitor(FileOperationConfig fileOperationConfig) {
        monitor=new FileAlterationMonitor(fileOperationConfig.getInterval());
    }
    @Override
    public void monitorInit() throws Exception {
        String dic= fileOperationConfig.getHnotesPath();
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(dic);
        fileAlterationObserver.addListener(new FileAlterationListenerAdaptor() {
            //多线程解决
            // 收集一定量的创建 然后一次性读取 meta
            @Override
            public void onFileCreate(File file) {
                UpDateNote upDateNote = new UpDateNote();
                upDateNote.setFile(file);
                upDateNote.setFlag(true);
                linkedBlockingQueue.add(upDateNote);
                log.info("有note创建");
            }

            @Override
            public void onFileChange(File file) {
                //通过文件名 更改数据路内容
                UpDateNote upDateNote = new UpDateNote();
                upDateNote.setFile(file);
                upDateNote.setFlag(false);

                linkedBlockingQueue.add(upDateNote);
                log.info("note 改变了");
            }
        });
        monitor.addObserver(fileAlterationObserver);
    }

    @Override
    public void monitorStart() throws Exception {

        monitor.start();
        log.info("为note创建监听成功");
    }

    @Override
    public void monitorStop() throws Exception {
        monitor.stop();
    }
}
