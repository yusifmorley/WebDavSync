package com.yusif.service.WebDav.WebDavSubFunction.FileOperation;

import com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService.InsertAndCons;
import com.yusif.service.WebDav.WebDavSubFunction.ThreadProcessService;
import com.yusif.Entity.File.FileUpdate;
import com.yusif.config.SingleBean;
import com.yusif.constant.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
@Slf4j
@Component
public class FileThreadProcess implements ThreadProcessService<FileUpdate> {
    LinkedBlockingQueue<FileUpdate> linkedBlockingQueue= SingleBean.getBlockingFileQueue();
    @Autowired
    InsertAndCons insertAndCons;
    @Override
    public void resovleElement() throws IOException {
        if(!linkedBlockingQueue.isEmpty()){
            log.info("开始更新文件");
            FileUpdate file;
            while ((file=linkedBlockingQueue.poll())!=null) {
                if (file.getType()== Type.MP4){
                    insertAndCons.insertAndCons(file.getFile());
                }else {
                    insertAndCons.insertAndCons(file.getFile());
                }
            }

        }

    }
}
