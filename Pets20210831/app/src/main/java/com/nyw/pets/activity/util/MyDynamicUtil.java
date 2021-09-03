package com.nyw.pets.activity.util;

import com.nyw.pets.fragment.util.MyMedia;

import java.util.ArrayList;

public class MyDynamicUtil {
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

    boolean isVideo;
    boolean isImg;
    String if_good;
    String if_collection;
    String server;
    String uid;
    String post_id;
    String comment_id;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isImg() {
        return isImg;
    }

    public void setImg(boolean img) {
        isImg = img;
    }

    public String getIf_good() {
        return if_good;
    }

    public void setIf_good(String if_good) {
        this.if_good = if_good;
    }

    public String getIf_collection() {
        return if_collection;
    }

    public void setIf_collection(String if_collection) {
        this.if_collection = if_collection;
    }

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
