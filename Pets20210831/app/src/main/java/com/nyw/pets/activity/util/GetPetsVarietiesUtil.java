package com.nyw.pets.activity.util;

import java.util.List;

public class GetPetsVarietiesUtil {

    /**
     * code : 1
     * message : 请求成功！
     * data : [{"id":1,"type_id":1,"varieties_name":"狸花猫","weight":5.25},{"id":2,"type_id":1,"varieties_name":"加菲猫","weight":6}]
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
         * type_id : 1
         * varieties_name : 狸花猫
         * weight : 5.25
         */

        private int id;
        private int type_id;
        private String varieties_name;
        private double weight;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getVarieties_name() {
            return varieties_name;
        }

        public void setVarieties_name(String varieties_name) {
            this.varieties_name = varieties_name;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }
}
