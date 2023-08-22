package com.yusif.Entity.File;

import com.yusif.config.FileOperationConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFileInfo {
  private File file;
  private String filename;//文件名

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MyFileInfo that = (MyFileInfo) o;

    return Objects.equals(filename, that.filename);
  }



}
