package com.yusif.service.WebDav.WebDavSubFunction.FileOperation.FileService;

import com.yusif.Entity.File.MyFileInfo;
import com.yusif.Tool.MyDateTime;
import com.yusif.config.FileOperationConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Component
public class FileSort {
    @Autowired
    MyDateTime myDate;
    @Autowired
    FileOperationConfig fileOperationConfig;

    public List<MyFileInfo> allpic;
    public List<MyFileInfo> allmp4;

    @PostConstruct
    public void initSort() {
        allpic = new ArrayList<>();
        allmp4 = new ArrayList<>();
        // 获取 源目标 文件夹
        List<String> obpicandmp4 = fileOperationConfig.getObPicAndMp4();
        obpicandmp4.forEach(e -> {
            try {
                Files.walkFileTree(Paths.get(e), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        String filename = file.getFileName().toString();
                        //                if (filename.endsWith(".jpg")||filename.endsWith(".png")||
                        //                        filename.endsWith(".JPG")||filename.endsWith(".jpeg")||filename.endsWith(".webp")
                        //                )
                        if (StringUtils.containsAnyIgnoreCase(filename, new String[]{".jpg", ".png", ".jpeg", ".webp"})) {

                            allpic.add(new MyFileInfo(file.toFile(), filename));
                        } else if (StringUtils.containsIgnoreCase(filename, "mp4")) {

                            allmp4.add(new MyFileInfo(file.toFile(), filename));
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

}


//    public void sortfile() throws Exception {
//		allpic.sort(new MyCompare());
//		allmp4.sort(new MyCompare());
//
//		Path path = Paths.get(fileOperationConfig.getTargetMp4());
//		move(allmp4,path);
//
//		Path path1 = Paths.get(fileOperationConfig.getTargetPic());
//		move(allpic,path1);
//    }
//
//
//    private void move(List<Path> paths,Path fatherpath) throws IOException {
//        List<Path> allAsArrayList = new ArrayList<>(paths);
//        Path currentpath=null;
//        for (int i = 0; i <allAsArrayList.size() ; i++) {
//            if (i%100==0) {
//                String from = myDate.getTimeFromMIll(
//                        allAsArrayList.get(i).toFile().lastModified());
//                String to=null;
//                if (i+99>=allAsArrayList.size()) {
//                    to = myDate.getTimeFromMIll(
//                            allAsArrayList.get(allAsArrayList.size()-1).toFile().lastModified());
//                }else {
//                    to = myDate.getTimeFromMIll(
//                            allAsArrayList.get(i+99).toFile().lastModified());
//                }
//
//                currentpath=fatherpath.resolve(String.format("%s 至 %s",from,to));
//                if (Files.notExists(currentpath))
//                    Files.createDirectory(currentpath);
//            }
//            if (Files.notExists(currentpath.resolve(allAsArrayList.get(i).getFileName())))
//                Files.copy(allAsArrayList.get(i),currentpath.resolve(allAsArrayList.get(i).getFileName()));
//        }
//    }
//  public static class MyCompare implements Comparator<Path>{
//        @Override
//        public int compare(Path o1, Path o2) {
//            if (o1.toFile().lastModified()==o2.toFile().lastModified())
//                return 0;
//            if (o1.toFile().lastModified()>o2.toFile().lastModified()) {
//                return 1;
//            }
//            else {
//                return -1;
//            }
//        }
//    }
//}
