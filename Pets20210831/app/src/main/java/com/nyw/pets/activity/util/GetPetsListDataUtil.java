package com.nyw.pets.activity.util;

import java.util.List;

public class GetPetsListDataUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : [{"id":53,"pid":"fuaoiB5D1EwWhFHS","type_id":2,"server":"http://qiniu.lovelovepets.com/","avatar":"2020/07/145d9202007080019438706.jpg","nickname":"哈哈","sex":1,"brithday":"20200710","varieties_name":"阿根廷獒","varieties_id":18,"sterilization":1,"weight":1,"age":"0","is_init":1,"healthy":"生病"}]
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
         * id : 53
         * pid : fuaoiB5D1EwWhFHS
         * type_id : 2
         * server : http://qiniu.lovelovepets.com/
         * avatar : 2020/07/145d9202007080019438706.jpg
         * nickname : 哈哈
         * sex : 1
         * brithday : 20200710
         * varieties_name : 阿根廷獒
         * varieties_id : 18
         * sterilization : 1
         * weight : 1
         * age : 0
         * is_init : 1
         * healthy : 生病
         */

        private int id;
        private String pid;
        private int type_id;
        private String server;
        private String avatar;
        private String nickname;
        private int sex;
        private String brithday;
        private String varieties_name;
        private int varieties_id;
        private int sterilization;
        private int weight;
        private String age;
        private int is_init;
        private String healthy;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBrithday() {
            return brithday;
        }

        public void setBrithday(String brithday) {
            this.brithday = brithday;
        }

        public String getVarieties_name() {
            return varieties_name;
        }

        public void setVarieties_name(String varieties_name) {
            this.varieties_name = varieties_name;
        }

        public int getVarieties_id() {
            return varieties_id;
        }

        public void setVarieties_id(int varieties_id) {
            this.varieties_id = varieties_id;
        }

        public int getSterilization() {
            return sterilization;
        }

        public void setSterilization(int sterilization) {
            this.sterilization = sterilization;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public int getIs_init() {
            return is_init;
        }

        public void setIs_init(int is_init) {
            this.is_init = is_init;
        }

        public String getHealthy() {
            return healthy;
        }

        public void setHealthy(String healthy) {
            this.healthy = healthy;
        }
    }
}
