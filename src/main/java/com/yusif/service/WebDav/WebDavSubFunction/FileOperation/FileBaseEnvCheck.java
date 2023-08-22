package com.yusif.service.WebDav.WebDavSubFunction.FileOperation;


import com.yusif.Tool.DicCheck;
import com.yusif.service.WebDav.WebDavSubFunction.WebDavSubFunctionBaseEnvInit;
import com.yusif.config.FileOperationConfig;
import com.yusif.constant.ActionLog;
import com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService.FileSort;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class FileBaseEnvCheck implements WebDavSubFunctionBaseEnvInit {
    @Autowired
    DicCheck dicCheck;
    @Autowired
    FileOperationConfig fileOperationConfig;

    @Autowired
    FileSort fileSort;

    @Override
    public void baseEnvInit() throws Exception {
        String[] dirs = {
                "res/memory",
                "res/webdav/orderstructfile/mp4",
                "res/webdav/orderstructfile/pic"
        };
        dicCheck.check(dirs);  //目录是否存在

        //排序文件检测
        Path path = Paths.get(fileOperationConfig.getTargetMp4());
        Path path2 = Paths.get(fileOperationConfig.getTargetPic());

//        if (!fileObserverConfig.isBoolsort()) {
//            log.info("mermory文件排序已经关闭");
//        } else
        if (checkdic(path) && checkdic(path2)) {
            fileSort.sortfile();
            log.info(ActionLog.FileOrderOk.getMsg());
        } else {
            log.info(ActionLog.FileHavedExist.getMsg());
        }
    }

    public boolean checkdic(Path path) throws IOException {
        File[] files = path.toFile().listFiles();
        return files.length == 0;
    }
}