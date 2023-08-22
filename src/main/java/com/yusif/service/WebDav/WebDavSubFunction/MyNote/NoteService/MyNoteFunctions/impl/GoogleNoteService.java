package com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yusif.config.FileOperationConfig;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.NoteService;
import com.yusif.Entity.note.GoogleKeep;
import com.yusif.Entity.note.MyNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
@Component
public class GoogleNoteService implements NoteService {
    @Autowired
    FileOperationConfig fileOperationConfig;
    @Autowired
    ObjectMapper objectMapper;
    public List<MyNote> toMyNote() throws IOException {
        List<MyNote> googlenotes=new ArrayList<>();
        Files.walkFileTree(Paths.get(fileOperationConfig.getGkPath()),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if (file.toString().endsWith(".json")){
                    GoogleKeep googleKeep = objectMapper.readValue(file.toFile(), GoogleKeep.class);
                    MyNote myNote = new MyNote();
                    myNote.setIspinned(googleKeep.isPinned()?1:0);
                    myNote.setTextcontent(googleKeep.getTextContent());
                    myNote.setCreatedtimestampusec(googleKeep.getCreatedTimestampUsec());
                    googlenotes.add(myNote);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return googlenotes;
    }
}
