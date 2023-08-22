package com.yusif.Entity.note;


public class BeeNote {
    private long createdAt; //创建日期
    private  String objectId;
    private boolean pinedTime;
    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isPinedTime() {
        return pinedTime;
    }

    public void setPinedTime(long pinedTime) {
        if (pinedTime!=0)
            this.pinedTime=true;
        else
            this.pinedTime=false;
    }




}
