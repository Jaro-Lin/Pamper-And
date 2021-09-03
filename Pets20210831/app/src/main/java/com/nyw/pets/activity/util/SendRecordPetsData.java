package com.nyw.pets.activity.util;

public class SendRecordPetsData {
    String token;
    //0为疫苗 1为洗澡 2为驱虫
    String type;
    //如果是疫苗 0为三联疫苗或者六联疫苗 1为狂犬疫苗 其他固定写字符串true
    String value;
    //宠物id
    String mypets_id;
    String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMypets_id() {
        return mypets_id;
    }

    public void setMypets_id(String mypets_id) {
        this.mypets_id = mypets_id;
    }
}
