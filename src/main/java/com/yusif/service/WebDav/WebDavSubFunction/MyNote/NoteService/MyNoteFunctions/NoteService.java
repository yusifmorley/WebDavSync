package com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions;



import com.yusif.Entity.note.MyNote;

import java.io.IOException;
import java.util.List;

public interface NoteService {
    List<MyNote> toMyNote()  throws IOException;
}
