package com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yusif.config.FileOperationConfig;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.NoteService;
import com.yusif.Entity.note.BeeNote;
import com.yusif.Entity.note.MyNote;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@Component
public class BeeNoteService implements NoteService {

    @Autowired
    FileOperationConfig fileOperationConfig;
    @Autowired
    ObjectMapper objectMapper;
    public List<MyNote> toMyNote() throws IOException {
        Map<String, Path> map=getMap();
        //最终未 所有 beeNote对象
        List<MyNote>  beenotes=new ArrayList<>();
        String s = IOUtils.toString(Files.newBufferedReader(Paths.get(fileOperationConfig.getHnoteDateMetaPath())));
        JsonNode jsonNode = objectMapper.readTree(s);
        JsonNode notes = jsonNode.get("notes");
        notes.forEach((e)->{
            try {
                BeeNote beeNote = objectMapper.readValue(e.toString(), BeeNote.class);

                MyNote myNote = new MyNote();
                myNote.setUptimestampusec(String.valueOf(beeNote.getUpdatedAt()));
                myNote.setIspinned(beeNote.isPinedTime()?1:0);
                Path path = map.get(beeNote.getObjectId());
                if (Objects.nonNull(path)) {
                    String s1 = IOUtils.toString(Files.newBufferedReader(path));
                    myNote.setTextcontent(s1);
                }else {
                    System.out.println(beeNote.getObjectId());
                }
                myNote.setCreatedtimestampusec(String.valueOf(beeNote.getCreatedAt()));

                beenotes.add(myNote);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        return beenotes;
    }
    public Map<String, Path> getMap() throws IOException {
        Map<String, Path> map=new HashMap<>();
        Files.walkFileTree(Paths.get(fileOperationConfig.getHnotesPath()),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String s = StringUtils.substringBefore(String.valueOf(file.getFileName()), ".");
                map.put(s,file); //文件名 ： 文件对象的 map
                return FileVisitResult.CONTINUE;
            }
        });
        return  map;
    }

    public Map<String,BeeNote> getBeeCreateAtMap() throws IOException {
        Map<String ,BeeNote> stringStringMap=new HashMap<>();
        String s = IOUtils.toString(Files.newBufferedReader(Paths.get(fileOperationConfig.getHnoteDateMetaPath())));
        JsonNode jsonNode = objectMapper.readTree(s);
        JsonNode notes = jsonNode.get("notes");
        notes.forEach((e)->{
            try {
                BeeNote beeNote = objectMapper.readValue(e.toString(), BeeNote.class);
                stringStringMap.put(beeNote.getObjectId(),beeNote);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return  stringStringMap;
    }
}
