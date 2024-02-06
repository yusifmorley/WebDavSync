package com.yusif.Entity.note;

import lombok.Data;

@Data
public class BeeNote {
    private long createdAt; //创建日期
    private  String objectId;
    private boolean pinedTime;
    private long updatedAt;

    public void setPinedTime(long pinedTime) {
        if (pinedTime!=0)
            this.pinedTime=true;
        else
            this.pinedTime=false;
  }




}
