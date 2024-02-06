package com.yusif.service.WebDav.WebDavSubFunction.MyNote;

import com.yusif.config.FileOperationConfig;
import com.yusif.config.TableConfig;
import com.yusif.dao.MyNoteMapper;
import com.yusif.service.WebDav.WebDavSubFunction.MyNote.NoteService.MyNoteInsert;
import com.yusif.service.WebDav.WebDavSubFunction.WebDavSubFunctionBaseEnvInit;
import com.yusif.Tool.DicCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@ConditionalOnProperty(value = "sync.file", havingValue = "true")
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
    FileOperationConfig fileOperationConfig;
    @Autowired
    DicCheck dicCheck;
    @Override
    public void baseEnvInit() throws Exception {
        //文件夹检测
        List<String> lidir=new ArrayList<>();
        lidir.add(fileOperationConfig.getGkPath());
        lidir.add(fileOperationConfig.getHnotesPath());
        dicCheck.check(lidir);

        //数据库检测
        List<String> gettables = myNoteMapper.gettables(); //数据库中的表
        List<String> alltable = tableConfig.getAlltable(); //配置的表
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

            myNoteInsert.run(); //初始化表
        }
        else {
            log.info("数据库表完整");
        }

    }
}
