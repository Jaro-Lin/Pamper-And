package com.nyw.pets.activity.util;

import java.util.List;

public class GetHotVideoDataUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":4,"list":[{"id":1,"title":"如何给猫咪洗澡","server":"http://qiniu.lovelovepets.com/","img":null,"type_id":1,"study_number":43},{"id":2,"title":"如何喂养猫","server":"http://qiniu.lovelovepets.com/","img":null,"type_id":1,"study_number":18},{"id":3,"title":"如何驱虫","server":"http://qiniu.lovelovepets.com/","img":null,"type_id":2,"study_number":2},{"id":4,"title":"如何吃药","server":"http://qiniu.lovelovepets.com/","img":null,"type_id":2,"study_number":2}]}
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
         * total : 4
         * list : [{"id":1,"title":"如何给猫咪洗澡","server":"http://qiniu.lovelovepets.com/","img":null,"type_id":1,"study_number":43},{"id":2,"title":"如何喂养猫","server":"http://qiniu.lovelovepets.com/","img":null,"type_id":1,"study_number":18},{"id":3,"title":"如何驱虫","server":"http://qiniu.lovelovepets.com/","img":null,"type_id":2,"study_number":2},{"id":4,"title":"如何吃药","server":"http://qiniu.lovelovepets.com/","img":null,"type_id":2,"study_number":2}]
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
             * id : 1
             * title : 如何给猫咪洗澡
             * server : http://qiniu.lovelovepets.com/
             * img : null
             * type_id : 1
             * study_number : 43
             */

            private int id;
            private String title;
            private String server;
            private Object img;
            private int type_id;
            private int study_number;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public int getStudy_number() {
                return study_number;
            }

            public void setStudy_number(int study_number) {
                this.study_number = study_number;
            }
        }
    }
}
