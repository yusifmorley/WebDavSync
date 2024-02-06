package com.yusif.Entity.note;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mynote")
public class MyNote implements Serializable {
    private Integer ispinned;
    private String textcontent;

    @TableId(type = IdType.INPUT)
    private String createdtimestampusec;
    private  String uptimestampusec;
    @Override
    public boolean equals(Object myNote) {
        return this.uptimestampusec.equals(((MyNote) myNote).uptimestampusec);
    }

}
