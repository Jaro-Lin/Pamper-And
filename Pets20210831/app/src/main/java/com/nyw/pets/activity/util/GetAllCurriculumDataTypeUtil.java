package com.nyw.pets.activity.util;

import java.util.List;

public class GetAllCurriculumDataTypeUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":1,"list":[{"id":5,"type_title":"驱虫指南"}]}
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
         * total : 1
         * list : [{"id":5,"type_title":"驱虫指南"}]
         */

        private int total;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 5
             * type_title : 驱虫指南
             */

            private int id;
            private String type_title;

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
        }
    }
}
