package com.yusif.service.WebDav.WebDavSubFunction.FileOperation;


import com.yusif.Tool.DicCheck;
import com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService.FileInsert;
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
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class FileBaseEnvCheck implements WebDavSubFunctionBaseEnvInit {
    @Autowired
    DicCheck dicCheck;
    @Autowired
    FileOperationConfig fileOperationConfig;

    @Autowired
    FileInsert fileInsert;

    @Autowired
    FileSort fileSort;

    @Override
    public void baseEnvInit() throws Exception {

        List<String> obPicAndMp4 = fileOperationConfig.getObPicAndMp4();

        obPicAndMp4.add(fileOperationConfig.getTargetMp4());
        obPicAndMp4.add(fileOperationConfig.getTargetPic());

        dicCheck.check(obPicAndMp4);  //目录是否存在 不存在则 创建

        if (Objects.nonNull(fileInsert.getCurrentFile(fileOperationConfig.getTargetMp4()))
                &&Objects.nonNull(fileInsert.getCurrentFile(fileOperationConfig.getTargetPic())))
        {
            log.info("存在 包含 -- 的文件");
        }
        else {
            throw new RuntimeException("目标有的文件夹不包含 --");
        }


//        //排序文件检测
//        Path path = Paths.get(fileOperationConfig.getTargetMp4());
//        Path path2 = Paths.get(fileOperationConfig.getTargetPic());
//        if (!fileObserverConfig.isBoolsort()) {
//            log.info("mermory文件排序已经关闭");
//        } else
//        if (checkdic(path) && checkdic(path2)) {
////            fileSort.sortfile();
//            log.info(ActionLog.FileOrderOk.getMsg());
//        } else {
//            log.info(ActionLog.FileHavedExist.getMsg());
//        }
    }

    public boolean checkdic(Path path) throws IOException {
        File[] files = path.toFile().listFiles();
        return files.length == 0;
    }
}