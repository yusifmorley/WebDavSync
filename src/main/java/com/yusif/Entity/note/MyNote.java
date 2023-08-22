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
   @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer ispinned;
    private String textcontent;
    private String createdtimestampusec;

    @Override
    public boolean equals(Object myNote) {
        if (this.createdtimestampusec.equals(((MyNote) myNote).createdtimestampusec)){
            return true;
        }else return false;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ispinned != null ? ispinned.hashCode() : 0);
        result = 31 * result + textcontent.hashCode();
        result = 31 * result + (createdtimestampusec != null ? createdtimestampusec.hashCode() : 0);
        return result;
    }
}
