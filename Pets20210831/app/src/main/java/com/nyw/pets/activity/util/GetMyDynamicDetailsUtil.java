package com.nyw.pets.activity.util;

public class GetMyDynamicDetailsUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : {"uid":"kiXqAMvg6xOyu2DpCFIoY20SJVBXyecW","nickname":"用户ilb6mFUF","avatar":"2020/04/db4dd202004031004484472.png","server":"http://qiniu.lovelovepets.com/","city":"深圳","spe":"这个人什么都没有留下...","sex":1,"if_follow":0,"follow":0,"fans":0}
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
         * uid : kiXqAMvg6xOyu2DpCFIoY20SJVBXyecW
         * nickname : 用户ilb6mFUF
         * avatar : 2020/04/db4dd202004031004484472.png
         * server : http://qiniu.lovelovepets.com/
         * city : 深圳
         * spe : 这个人什么都没有留下...
         * sex : 1
         * if_follow : 0
         * follow : 0
         * fans : 0
         */

        private String uid;
        private String nickname;
        private String avatar;
        private String server;
        private String city;
        private String spe;
        private int sex;
        private int if_follow;
        private int follow;
        private int fans;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getSpe() {
            return spe;
        }

        public void setSpe(String spe) {
            this.spe = spe;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getIf_follow() {
            return if_follow;
        }

        public void setIf_follow(int if_follow) {
            this.if_follow = if_follow;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }
    }
}
