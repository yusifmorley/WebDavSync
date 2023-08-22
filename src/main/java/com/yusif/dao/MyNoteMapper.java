package com.yusif.Dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yusif.Entity.note.MyNote;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyNoteMapper extends BaseMapper<MyNote> {
    int createtables();
    List<String> gettables();
}
