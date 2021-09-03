package com.nyw.pets.activity.util;

public class GetExplanationUtil {

    /**
     * code : 1
     * message : ok
     * data : {"data":{"id":1,"cate_id":1,"title":"感冒","content":"狗狗感冒后会表现出精神萎靡、食欲减退、打喷嚏、流鼻涕等症状，"}}
     */

    private int code;
    private String message;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : {"id":1,"cate_id":1,"title":"感冒","content":"狗狗感冒后会表现出精神萎靡、食欲减退、打喷嚏、流鼻涕等症状，"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * cate_id : 1
             * title : 感冒
             * content : 狗狗感冒后会表现出精神萎靡、食欲减退、打喷嚏、流鼻涕等症状，
             */

            private int id;
            private int cate_id;
            private String title;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCate_id() {
                return cate_id;
            }

            public void setCate_id(int cate_id) {
                this.cate_id = cate_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
