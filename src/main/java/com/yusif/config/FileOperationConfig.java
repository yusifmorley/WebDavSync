package com.yusif.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "file-observer") //这里用 必须用 — 分割
public class FileOperationConfig {
    private  boolean boolSort;
    private long interval;
    private String obrecord;
    private List<String> obPicAndMp4;
    private String hnotesPath;
    private String hnoteDateMetaPath;
    private String targetMp4;
    private String taoPath;
    private String gkPath;
    private  String targetPic;
    private long consumeTime;
}
