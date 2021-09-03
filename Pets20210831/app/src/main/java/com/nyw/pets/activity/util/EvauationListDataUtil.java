package com.nyw.pets.activity.util;

import com.nyw.pets.fragment.util.MyMedia;

import java.util.ArrayList;

public class EvauationListDataUtil {
    String id;
    String shopId;
    String userImg;
    String name;
    String time;
    String specifications;
    String msg;
    String praiseNumber;
    String evaluationNumber;
    boolean if_good;
    String thumbs;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getThumbs() {
        return thumbs;
    }

    public void setThumbs(String thumbs) {
        this.thumbs = thumbs;
    }

    public boolean getIf_good() {
        return if_good;
    }

    public void setIf_good(boolean if_good) {
        this.if_good = if_good;
    }

    private ArrayList<MyMedia> mediaList;

    public ArrayList<MyMedia> getMediaList() {
        return mediaList;
    }

    public void setMediaList(ArrayList<MyMedia> mediaList) {
        this.mediaList = mediaList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(String praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public String getEvaluationNumber() {
        return evaluationNumber;
    }

    public void setEvaluationNumber(String evaluationNumber) {
        this.evaluationNumber = evaluationNumber;
    }
}
