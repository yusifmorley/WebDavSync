package com.yusif.service.WebDav.WebDavSubFunction;

public interface WebDavSubFunctionMonitor {
    void monitorInit() throws  Exception;; //监听初始化
    void monitorStart() throws Exception;//监听开始方法
    void monitorStop() throws  Exception;
}
