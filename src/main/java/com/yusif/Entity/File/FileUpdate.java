package com.yusif.Entity.File;

import com.yusif.constant.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUpdate {
  private Type type; //文件种类
  private File file;

}
