package com.nyw.pets.activity.util;

public class GetUserImgUtil {

    /**
     * code : 1
     * message : 上传成功！
     * data : {"server":"http://q7n8qb6fa.bkt.clouddn.com/","img":"2020/04/db108202004141756391468.jpg"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * server : http://q7n8qb6fa.bkt.clouddn.com/
         * img : 2020/04/db108202004141756391468.jpg
         */

        private String server;
        private String img;

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
