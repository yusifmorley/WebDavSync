package com.yusif.shecule;

import com.yusif.config.FileOperationConfig;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteTheadProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
@ConditionalOnProperty(value = "sync.scheduling", havingValue = "true")
@Component
@Slf4j
public class SchedulingConfig {
@Autowired
FileOperationConfig fileOperationConfig;

    @Autowired
    NoteTheadProcess noteTheadProcess;

    @PostConstruct
    void init(){
        log.info("scheduling 已经开启");
    }

    @Scheduled( fixedRateString = "${file-observer.consumeTime}")
    public void mynoteMon() throws IOException {
        noteTheadProcess.resovleElement();
        log.info("正在刷新");
    }
}

