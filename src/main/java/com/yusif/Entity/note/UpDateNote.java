package com.yusif.Entity.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpDateNote {
    private boolean flag; //真为创建 假为更改
//    private String filename;//升级文件名
    private File file; //变化的文件
}
