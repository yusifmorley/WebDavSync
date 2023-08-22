package com.yusif.service.WebDav.WebDavSubFunction.MyNote;

import com.yusif.Dao.MyNoteMapper;

import com.yusif.Entity.note.UpDateNote;
import com.yusif.config.FileOperationConfig;
import com.yusif.config.SingleBean;
import com.yusif.service.WebDav.WebDavSubFunction.WebDavSubFunctionMonitor;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

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
            }

            @Override
            public void onFileChange(File file) {
                //通过文件名 更改数据路内容
                UpDateNote upDateNote = new UpDateNote();
                upDateNote.setFile(file);
                upDateNote.setFlag(false);
                linkedBlockingQueue.add(upDateNote);
            }
        });
        monitor.addObserver(fileAlterationObserver);
    }

    @Override
    public void monitorStart() throws Exception {
        monitor.start();
    }

    @Override
    public void monitorStop() throws Exception {
        monitor.stop();
    }
}
