package com.nyw.pets.activity.util;

import java.util.List;

public class GetShopTypeTitleUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : [{"id":1,"title":"主食","server":"http://qiniu.lovelovepets.com/","img":"2020/04/db4dd202004031004484472.png"},{"id":2,"title":"零食","server":"http://qiniu.lovelovepets.com/","img":"2020/04/db4dd202004031004484472.png"},{"id":3,"title":"清洁","server":"http://qiniu.lovelovepets.com/","img":"2020/04/db4dd202004031004484472.png"},{"id":4,"title":"营养","server":"http://qiniu.lovelovepets.com/","img":"2020/06/192ab202006021649556018.jpg"},{"id":5,"title":"养宠必备","server":"http://qiniu.lovelovepets.com/","img":"2020/04/db4dd202004031004484472.png"},{"id":6,"title":"零嘴","server":"http://qiniu.lovelovepets.com/","img":"2020/05/3702020200526150721176.jpg"},{"id":10,"title":"主食","server":"http://qiniu.lovelovepets.com/","img":"2020/05/a5a14202005281905354150.jpg"},{"id":11,"title":"清洁","server":"http://qiniu.lovelovepets.com/","img":"2020/06/5d65195608aaddc4de4487d7c1be4bc4.jpg"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : 主食
         * server : http://qiniu.lovelovepets.com/
         * img : 2020/04/db4dd202004031004484472.png
         */

        private int id;
        private String title;
        private String server;
        private String img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
