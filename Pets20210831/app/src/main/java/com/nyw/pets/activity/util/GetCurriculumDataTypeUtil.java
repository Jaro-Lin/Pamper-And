package com.nyw.pets.activity.util;

import java.util.List;

public class GetCurriculumDataTypeUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : [{"id":5,"type_title":"驱虫指南","server":"http://qiniu.lovelovepets.com/","icon":null}]
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
         * id : 5
         * type_title : 驱虫指南
         * server : http://qiniu.lovelovepets.com/
         * icon : null
         */

        private int id;
        private String type_title;
        private String server;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType_title() {
            return type_title;
        }

        public void setType_title(String type_title) {
            this.type_title = type_title;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
