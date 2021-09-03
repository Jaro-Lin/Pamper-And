package com.nyw.pets.activity.util;

import com.nyw.pets.fragment.util.MyMedia;

import java.util.ArrayList;

public class CommunityListUtil {
    String id;
    String img;
    String name;
    String time;
    String msg;
    String label;
    String comment;
    String thumbs;
    String collection;
    String share;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getThumbs() {
        return thumbs;
    }

    public void setThumbs(String thumbs) {
        this.thumbs = thumbs;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }
}
