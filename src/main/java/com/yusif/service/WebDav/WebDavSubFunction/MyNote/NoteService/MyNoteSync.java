package com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteFunctions.impl.BeeNoteService;
import com.yusif.dao.MyNoteMapper;
import com.yusif.Entity.note.MyNote;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
@Slf4j
@Component
@Lazy
public class MyNoteSync {


    BeeNoteService beeNoteService;
    //Googlekeep 在项目启动时 和数据库只对比一次  dev
    //beenote 为间隔时间监听  为Scheduled 调用 prod
    MyNoteMapper myNoteMapper;
    MyNoteInsert myNoteInsert;
    List<MyNote> pre;
    List<MyNote> aft;
    @Autowired
    public MyNoteSync(MyNoteInsert myNoteInsert, MyNoteMapper myNoteMapper, BeeNoteService beeNoteService) throws IOException {
        this.beeNoteService=beeNoteService;
        this.myNoteMapper=myNoteMapper;
        this.myNoteInsert = myNoteInsert;
        pre=myNoteMapper.selectList(new QueryWrapper<>());
   }

   @PostConstruct
   public  void initSync() throws IOException {
       log.info("正在检测和同步 beeNote");
       beeNoteCheckAndSync();
       log.info("同步完成");
   }
   public void beeNoteCheckAndSync() throws IOException {
       aft=beeNoteService.toMyNote();
       //比较
       List<MyNote> myNotes = ListUtils.removeAll(aft, pre);
       if (myNotes.size()!=0){
           for (MyNote my:myNotes
                ) {
               myNoteMapper.insert(my);
           }
           log.info("数据库更新mynote成功！");
       }
       //对比完之后
       //pre=aft;
       pre=myNoteMapper.selectList(new QueryWrapper<>());
   }


}
