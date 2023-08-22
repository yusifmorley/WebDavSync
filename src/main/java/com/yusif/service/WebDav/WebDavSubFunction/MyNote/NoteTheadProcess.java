package com.yusif.service.WebDav.WebDavSubFunction.MyNote;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.impl.BeeNoteService;
import com.yusif.service.WebDav.WebDavSubFunction.ThreadProcessService;
import com.yusif.Dao.MyNoteMapper;
import com.yusif.Entity.note.BeeNote;
import com.yusif.Entity.note.MyNote;
import com.yusif.Entity.note.UpDateNote;
import com.yusif.config.SingleBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
public class NoteTheadProcess implements ThreadProcessService<UpDateNote> {
    LinkedBlockingQueue<UpDateNote> linkedBlockingQueue= SingleBean.getBlockingNoteQueue();
    @Autowired
    BeeNoteService beeNoteService;
    @Autowired
    MyNoteMapper myNoteMapper;
    @Override
    public void resovleElement() throws IOException {
        if (!linkedBlockingQueue.isEmpty()){
            Map<String, BeeNote> beeNoteMap = beeNoteService.getBeeCreateAtMap();
            Map<String, Path> map = beeNoteService.getMap();
            UpDateNote update;
            while ((update=linkedBlockingQueue.poll())!=null){
                MyNote myNote = new MyNote();
                if (update.isFlag()){
                    log.info("新文件note");
                    //如果是新文件
                    String filename = StringUtils.substringBefore(update.getFile().getName(), ".");
                    BeeNote beeNote=beeNoteMap.get(filename);
                    myNote.setIspinned(beeNote.isPinedTime()?1:0);
                    String s1 = IOUtils.toString(Files.newBufferedReader(map.get(beeNote.getObjectId())));
                    myNote.setTextcontent(s1);
                    myNote.setCreatedtimestampusec(String.valueOf(beeNote.getCreatedAt()));
                    int insert = myNoteMapper.insert(myNote);
                    if (insert==0)
                        log.error("插入了{}条记录",insert);
                    else
                        log.info("插入了{}条记录",insert);
                }else {     //如果是更改的文件
                    log.info("更新文件");
                    String filename = StringUtils.substringBefore(update.getFile().getName(), ".");
                    BeeNote beeNote = beeNoteMap.get(filename);

                    String createTime = String.valueOf(beeNote.getCreatedAt());
                    boolean boolpined=beeNote.isPinedTime();

                    String s1 = IOUtils.toString(Files.newBufferedReader(update.getFile().toPath()));

                    myNote.setTextcontent(s1);
                    myNote.setIspinned(boolpined ?1:0);
                    UpdateWrapper<MyNote> updateWrapper=new UpdateWrapper<>();
                    updateWrapper.eq("createdtimestampusec",createTime);
                    int update1 = myNoteMapper.update(myNote, updateWrapper);
                    if (update1==0)
                        log.error("更新了{}条记录",update1);
                    else
                        log.info("更新了{}条记录",update1);
//                    QueryWrapper<MyNote> myNoteQueryWrapper = new QueryWrapper<>();
//                    System.out.println(createTime);
//                    myNoteQueryWrapper.eq("createdtimestampusec",createTime);
//                    List<MyNote> myNotes = myNoteMapper.selectList(myNoteQueryWrapper);
//                    System.out.println(myNotes);

                }
            }

        }
    }
}
