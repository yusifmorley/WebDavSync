package com.yusif.service.WebDav.WebDavSubFunction.MyNote;

import com.yusif.config.TableConfig;
import com.yusif.dao.MyNoteMapper;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteInsert;
import com.yusif.service.WebDav.WebDavSubFunction.WebDavSubFunctionBaseEnvInit;
import com.yusif.Tool.DicCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MyNoteBaseEnvCheck implements WebDavSubFunctionBaseEnvInit {
    @Autowired
    MyNoteMapper myNoteMapper;
    @Autowired
    TableConfig tableConfig;
    @Autowired
    MyNoteInsert myNoteInsert;
    @Autowired
    DicCheck dicCheck;
    @Override
    public void baseEnvInit() throws Exception {
        String[] dirs={
                "res/Google Keep",
                "res/webdav/hnote-data-v2",
        };
        dicCheck.check(dirs);
        //数据库检测
        List<String> gettables = myNoteMapper.gettables();
        List<String> alltable = tableConfig.getAlltable();
        boolean b = alltable.stream().allMatch(e ->
                gettables.contains(e)
        );

        if (!b){
            log.info("正在创建表");
            int c1 = myNoteMapper.createtables();
            if (c1==1){
                log.info("创建表成功！");
            }
            log.info("正在初始化note...");

            myNoteInsert.run();
        }
        else {
            log.info("数据库表完整");
        }

    }
}
