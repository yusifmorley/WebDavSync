package com.yusif.constant;

//日志中的 活动 字段
public enum ActionLog {
    BlockedIPManyReq("访问次数过多"),
    IPERROR("非法ip"),
    BlockedIP("被封禁的ip"),
    NoResp("Telegram服务器未响应"),
    NativeCheckError("原生查询错误"),
    WhoisError("Whois查询错误"),
    FileHavedExist("文件排序存在"),
    FileOrderOk("文件夹排序完成"),
    PathIsNotExist("此路径不存在"),
    WrongQuery("非法请求"),
    PathIsNotDirectory("此路径不是目录");

    private String  msg;
    ActionLog(String msg){
    this.msg=msg;
}
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
