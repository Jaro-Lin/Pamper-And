package com.nyw.pets.activity.util;

public class GetLoginUtil {


    /**
     * code : 1
     * message : 登陆成功！
     * data : {"token":"1hED5r66m6Bno89U6XhspEJRLuUH1YIW","nickname":"哈哈哈","avatar":"http://q7n8qb6fa.bkt.clouddn.com/2020/04/db4dd202004031004484472.png","sex":1,"birthday":"20200414","city":"北京市北京市东城区"}
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
         * token : 1hED5r66m6Bno89U6XhspEJRLuUH1YIW
         * nickname : 哈哈哈
         * avatar : http://q7n8qb6fa.bkt.clouddn.com/2020/04/db4dd202004031004484472.png
         * sex : 1
         * birthday : 20200414
         * city : 北京市北京市东城区
         */

        private String token;
        private String nickname;
        private String avatar;
        private int sex;
        private String birthday;
        private String city;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
