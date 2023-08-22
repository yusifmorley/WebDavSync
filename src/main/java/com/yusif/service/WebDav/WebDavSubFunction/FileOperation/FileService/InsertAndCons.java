package com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService;

import com.yusif.MyWarraper.IntegerWapper;

import com.yusif.config.FileOperationConfig;
import com.yusif.utils.MyDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.LongStream;
@Slf4j
@Component
//插入文件
public class InsertAndCons {

    FileInsert fileInsert;

    MyDateTime myDate;

    FileOperationConfig fileOperationConfig;
    public Path currentmp4;
    public Path currentpic;
    private IntegerWapper countmp4;
    private IntegerWapper  countpic;
    @Autowired
    public  InsertAndCons(FileOperationConfig fileOperationConfig,FileInsert fileInsert,MyDateTime myDate){
        this.fileOperationConfig=fileOperationConfig;
        this.fileInsert=fileInsert;
        this.myDate=myDate;
        currentmp4 =fileInsert.getCurrentFile(fileOperationConfig.getTargetMp4());
        currentpic=fileInsert.getCurrentFile(fileOperationConfig.getTargetPic());
        countmp4=getCountmp4();
        countpic=getCountpic();
    }

    //current  为绝对路径
    public IntegerWapper getCountmp4(){ //获取目前目录下文件数
        String[] list = new File(currentmp4.toUri()).list();
        return new IntegerWapper(list.length);
    }

    public IntegerWapper getCountpic() { //获取目前目录下文件数

        String[] list = new File(currentpic.toUri()).list();
        return new IntegerWapper(list.length);
    }

    public  void insertAndCons(File file) throws IOException {
        if (Files.exists(currentmp4)&&Files.exists(currentpic)){
            log.info("存在可操作文件 包含 -- ");
        }
        else {
            throw new  RuntimeException("找不到可操作文件！");
        }

        Path current;
        IntegerWapper count;//引用数据类型
        int flag; //标记当前文件是是mp4 或mp3
        if (StringUtils.containsIgnoreCase(file.getName(),".mp4")) //功能的复用 可同时操作 mp4 和pic
        {   flag=0;
            current=currentmp4;
            count=countmp4;
        }else {
            flag=1;
            current=currentpic;
            count=countpic;
        }
        //复制文件
        Files.copy(file.toPath(), current.resolve(file.getName()));
        log.info("移动文件成功！");
        count.increment(1);
        if (count.getI() > 100) {
            //我们默认 文件 永远存在 -- 文件夹
            //而且永远只存在一个
            //也就是永远只能向后延伸
            //则在文件后添加最大文件时间
            DirectoryStream<Path> paths = Files.newDirectoryStream(current);
            LongStream.Builder builder = LongStream.builder();
            for (Path p : paths
            ) {
                builder.add(p.toFile().lastModified());
            }
            LongStream build = builder.build();
            long asLong = build.max().getAsLong();//寻找出目录最大文件的时间
            String timeFromMIll = myDate.getTimeFromMIll(asLong);
//
            String newstring = current.getFileName().toString().replace("--", "至 " + timeFromMIll);
            //目录改名
            Path move = Files.move(current, current.resolveSibling(newstring));
            if (Files.exists(move)) {
                log.info("修改目录名成功！");
            } else {
                log.warn("修改目录名失败！");
            }
            //创建 --文件夹
            Path directory = Files.createDirectory(current.resolveSibling(timeFromMIll + " --"));
            if (Files.exists(directory)) {
                log.info("创建文件夹成功!");
            } else {
                log.warn("创建文件夹失败!");
            }
            if (flag==0){
                currentmp4=directory;
                countmp4=getCountmp4();
            }else {
                currentpic=directory;
                countpic=getCountpic();
            }
        }
    }



}