package com.nyw.pets.activity.util;

import java.util.List;

public class GetCustomerServiceDataUtil {

    /**
     * msg : 获取成功
     * code : 0
     * data : [{"id":1,"name":"手机号","value":"13457707226","type":1,"create_time":"1970-01-15 14:02:01","update_time":"2019-10-26 00:35:31"},{"id":2,"name":"微信号","value":"12313213","type":2,"create_time":"1970-01-02 18:12:03","update_time":"1970-01-15 14:00:31"},{"id":3,"name":"注册回调","value":"321312313sdsaf","type":3,"create_time":"1970-01-02 18:12:13","update_time":"1970-01-15 14:00:31"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
         * name : 手机号
         * value : 13457707226
         * type : 1
         * create_time : 1970-01-15 14:02:01
         * update_time : 2019-10-26 00:35:31
         */

        private int id;
        private String name;
        private String value;
        private int type;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
