package com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService;

import com.yusif.Entity.File.FileUpdate;
import com.yusif.Entity.File.MyFileInfo;
import com.yusif.config.SingleBean;
import com.yusif.constant.Type;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

//对 滞后文件进行同步
@Slf4j
@Component
@ConditionalOnProperty(value = "sync.file", havingValue = "true")
public class FileSync {
   // 目标文件夹 和源文件夹比较 把相差的文件 作为新文件的生产 进行消费
   LinkedBlockingQueue<FileUpdate> linkedBlockingQueue = SingleBean.getBlockingFileQueue();
   private  String tartDic="res/webdav/orderstructfile";
   private   List<MyFileInfo> aftpic;
   private   List<MyFileInfo> aftmp4;

   private   List<MyFileInfo> prepic;
   private   List<MyFileInfo> premp4;
   @Autowired
   FileSort fileSort;

   @PostConstruct
   public  void  initSync(){
      premp4=fileSort.allmp4;
      prepic=fileSort.allpic;


      aftpic =new ArrayList<>();
      aftmp4 =new ArrayList<>();
      // 获取 源目标 文件夹
         try {
            Files.walkFileTree(Paths.get(tartDic),new SimpleFileVisitor<Path>(){
               @Override
               public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                  String filename=file.getFileName().toString();
                  //                if (filename.endsWith(".jpg")||filename.endsWith(".png")||
                  //                        filename.endsWith(".JPG")||filename.endsWith(".jpeg")||filename.endsWith(".webp")
                  //                )
                  if(StringUtils.containsAnyIgnoreCase(filename,new String[]{".jpg",".png",".jpeg",".webp"})){
                     aftpic.add(new MyFileInfo(file.toFile(), filename));
                  }
                  else if (StringUtils.containsIgnoreCase(filename,"mp4") ) {
                     aftmp4.add(new MyFileInfo(file.toFile(), filename));
                  }
                  return FileVisitResult.CONTINUE;
               }
            });
         } catch (IOException ex) {
            throw new RuntimeException(ex);
         };
      List<MyFileInfo> pathMp4 = ListUtils.removeAll(premp4, aftmp4);
      List<MyFileInfo> pathPic = ListUtils.removeAll(prepic, aftpic);
      pathMp4.forEach(e->{
         linkedBlockingQueue.add(new FileUpdate(Type.MP4,e.getFile()));
      });
      pathPic.forEach(e->{
         linkedBlockingQueue.add(new FileUpdate(Type.Pic,e.getFile()));
      });
   }

}
