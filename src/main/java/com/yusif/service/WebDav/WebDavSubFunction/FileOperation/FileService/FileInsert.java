package com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService;


import com.yusif.utils.MyDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
public class FileInsert {
    @Autowired
    MyDateTime myDate;
    private File current;
    public  Path getCurrentFile(String father) { //返回插入的目录
//        String father = "res/webdav/orderstructfile/pic";
        File file = new File(father);
        File[] files = file.listFiles();
        for (File f : files
        ) {
            if (f.getName().contains("--"))  //永远存在一个包含 -- 的文件夹
                current = f;
        }
        return current.toPath();
    }
}
