package com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.NoteService;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.impl.BeeNoteService;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.impl.GoogleNoteService;
import com.yusif.Dao.MyNoteMapper;
import com.yusif.Entity.note.MyNote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class MyNoteInsert {
    @Autowired
    BeeNoteService beeNoteService;
    @Autowired
    GoogleNoteService googleService;

    ObjectMapper objectMapper;
    MyNoteMapper myNoteMapper;
    @Autowired
    public MyNoteInsert(ObjectMapper objectMapper, MyNoteMapper myNoteMapper){
        this.objectMapper=objectMapper;
        this.myNoteMapper=myNoteMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

   private void insertNote(NoteService noteService) throws IOException {
       List<MyNote> notes=noteService.toMyNote();
       for (MyNote n:notes
       ) {
           myNoteMapper.insert(n);
       }
   }

   public void run() throws IOException {
       insertNote(googleService);
       insertNote(beeNoteService);
       log.info("mynote初始化完成！");
    }


}
