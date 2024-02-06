package com.yusif.Tool;

import com.yusif.config.FileOperationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@ConditionalOnProperty(value = "sync.file", havingValue = "true")
@Component
@Slf4j
public class DicCheck {


    @Autowired
    FileOperationConfig fileOperationConfig;

    public void check( List<String> dirs) throws Exception {
        //文件夹检测
        for (String s:dirs
        ) {
            Path dir = Paths.get(s);
            if (Files.exists(dir)){
                log.info( String.format("%s 文件夹存在",s));
            }else {
                Path directory = Files.createDirectory(dir);
                if (Files.exists(directory))
                    log.info(String.format("创建%s目录成功！",directory.getFileName()));
                else
                    throw new RuntimeException(String.format("%s 文件夹不存在且创建失败",s));
            }
        }


    }



}
