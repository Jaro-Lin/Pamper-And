package com.nyw.pets.activity.util;

import java.util.List;

public class GetReportDataUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : [{"id":1,"type":"垃圾营销"},{"id":2,"type":"涉黄信息"},{"id":3,"type":"不实信息"},{"id":4,"type":"人身攻击"},{"id":5,"type":"有害信息"},{"id":6,"type":"内容抄袭"},{"id":7,"type":"违法信息"},{"id":8,"type":"诈骗信息"}]
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
         * type : 垃圾营销
         */

        private int id;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
