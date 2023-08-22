package com.yusif.Entity.note;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleKeep {
    private boolean isPinned;
    private String textContent;
    private String createdTimestampUsec;
}
