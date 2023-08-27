package com.yusif.shecule;

import com.yusif.config.FileOperationConfig;
import com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileThreadProcess;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteTheadProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SchedulingConfig {

//    @Profile("prod")
//    @Scheduled(cron = "0 30 12 * * ?") // 每天 23:55:0执行
//    public void refresh(){
//       int recode= ipMapper.deleterecode();
//       log.info("数据库成功刷新 删除"+recode+"条记录");
//    }
@Autowired
FileOperationConfig fileOperationConfig;
    @Autowired
    FileThreadProcess fileThreadProcess;
    @Autowired
    NoteTheadProcess noteTheadProcess;

    @Scheduled( fixedRateString = "${file-observer.consumeTime}")
    public void mynoteMon() throws IOException {
        noteTheadProcess.resovleElement();
        fileThreadProcess.resovleElement();
        log.info("正在刷新");
    }
}

