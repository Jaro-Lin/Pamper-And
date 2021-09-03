package com.nyw.pets.activity.util;

import java.util.List;

public class GetMyCollectionVideoDataUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":2,"list":[{"video_id":1,"type_id":1,"image":null,"title":"如何给猫咪洗澡","if_collection":1},{"video_id":2,"type_id":1,"image":null,"title":"如何喂养猫","if_collection":1}]}
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
         * total : 2
         * list : [{"video_id":1,"type_id":1,"image":null,"title":"如何给猫咪洗澡","if_collection":1},{"video_id":2,"type_id":1,"image":null,"title":"如何喂养猫","if_collection":1}]
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
             * video_id : 1
             * type_id : 1
             * image : null
             * title : 如何给猫咪洗澡
             * if_collection : 1
             */

            private int video_id;
            private int type_id;
            private String image;
            private String title;
            private int if_collection;

            public int getVideo_id() {
                return video_id;
            }

            public void setVideo_id(int video_id) {
                this.video_id = video_id;
            }

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIf_collection() {
                return if_collection;
            }

            public void setIf_collection(int if_collection) {
                this.if_collection = if_collection;
            }
        }
    }
}
