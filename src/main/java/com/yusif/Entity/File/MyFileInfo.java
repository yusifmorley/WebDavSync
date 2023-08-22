package com.yusif.Entity.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo {
  private   String filename;//文件名
  private String filehash;  // 文件哈希
  public String hashCode;
}
