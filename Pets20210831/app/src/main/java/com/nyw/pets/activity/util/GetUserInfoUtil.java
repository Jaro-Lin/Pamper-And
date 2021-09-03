package com.nyw.pets.activity.util;

public class GetUserInfoUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : {"phone":"18977092827","log_time":"1586845711","last_login_time":null,"last_login_phone":"android","nickname":"哈哈哈","server":"http://qiniu.lovelovepets.com/","avatar":"2020/04/7907a202004281048577350.jpg","sex":1,"city":"南宁市","birthday":"20200413","spe":"我是做开发的","fans_count":0,"follow_count":2,"post_count":4,"coupon_count":3,"sign_in_count":1,"last_sign_day":"2020-06-14"}
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
         * phone : 18977092827
         * log_time : 1586845711
         * last_login_time : null
         * last_login_phone : android
         * nickname : 哈哈哈
         * server : http://qiniu.lovelovepets.com/
         * avatar : 2020/04/7907a202004281048577350.jpg
         * sex : 1
         * city : 南宁市
         * birthday : 20200413
         * spe : 我是做开发的
         * fans_count : 0
         * follow_count : 2
         * post_count : 4
         * coupon_count : 3
         * sign_in_count : 1
         * last_sign_day : 2020-06-14
         */

        private String phone;
        private String log_time;
        private Object last_login_time;
        private String last_login_phone;
        private String nickname;
        private String server;
        private String avatar;
        private int sex;
        private String city;
        private String birthday;
        private String spe;
        private int fans_count;
        private int follow_count;
        private int post_count;
        private int coupon_count;
        private int sign_in_count;
        private String last_sign_day;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLog_time() {
            return log_time;
        }

        public void setLog_time(String log_time) {
            this.log_time = log_time;
        }

        public Object getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(Object last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getLast_login_phone() {
            return last_login_phone;
        }

        public void setLast_login_phone(String last_login_phone) {
            this.last_login_phone = last_login_phone;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSpe() {
            return spe;
        }

        public void setSpe(String spe) {
            this.spe = spe;
        }

        public int getFans_count() {
            return fans_count;
        }

        public void setFans_count(int fans_count) {
            this.fans_count = fans_count;
        }

        public int getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(int follow_count) {
            this.follow_count = follow_count;
        }

        public int getPost_count() {
            return post_count;
        }

        public void setPost_count(int post_count) {
            this.post_count = post_count;
        }

        public int getCoupon_count() {
            return coupon_count;
        }

        public void setCoupon_count(int coupon_count) {
            this.coupon_count = coupon_count;
        }

        public int getSign_in_count() {
            return sign_in_count;
        }

        public void setSign_in_count(int sign_in_count) {
            this.sign_in_count = sign_in_count;
        }

        public String getLast_sign_day() {
            return last_sign_day;
        }

        public void setLast_sign_day(String last_sign_day) {
            this.last_sign_day = last_sign_day;
        }
    }
}
