package com.yusif.MyWarraper;

public class IntegerWapper {
    int i;
    public IntegerWapper(int i){
        this.i=i;
    }
    public void increment(int p){
        i=i+p;
    }

    public int getI() {
        return i;
    }
}
