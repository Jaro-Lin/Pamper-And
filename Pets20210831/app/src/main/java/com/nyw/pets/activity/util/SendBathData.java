package com.nyw.pets.activity.util;

public class SendBathData {
    //宠物id
    String mypets_id;
    //
    String token;
    //洗澡间隔
    String bath_inter;
    //驱虫间隔
    String expelling_inter;

    public String getMypets_id() {
        return mypets_id;
    }

    public void setMypets_id(String mypets_id) {
        this.mypets_id = mypets_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBath_inter() {
        return bath_inter;
    }

    public void setBath_inter(String bath_inter) {
        this.bath_inter = bath_inter;
    }

    public String getExpelling_inter() {
        return expelling_inter;
    }

    public void setExpelling_inter(String expelling_inter) {
        this.expelling_inter = expelling_inter;
    }
}
