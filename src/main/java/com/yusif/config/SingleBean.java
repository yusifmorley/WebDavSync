package com.yusif.config;

import com.yusif.Entity.File.FileUpdate;
import com.yusif.Entity.note.UpDateNote;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

//单例模式
public class SingleBean {
    private static LinkedBlockingQueue<UpDateNote> blockingNoteQueue;
    private static LinkedBlockingQueue<FileUpdate> blockingFileQueue;
    public static LinkedBlockingQueue<FileUpdate> getBlockingFileQueue(){
    if (Objects.isNull(blockingFileQueue)){
        blockingFileQueue =new LinkedBlockingQueue<>();
        }
    return blockingFileQueue;
    }

    public static LinkedBlockingQueue<UpDateNote> getBlockingNoteQueue(){
        if (Objects.isNull(blockingNoteQueue)){
            blockingNoteQueue =new LinkedBlockingQueue<>();
        }
        return  blockingNoteQueue ;
    }


}
