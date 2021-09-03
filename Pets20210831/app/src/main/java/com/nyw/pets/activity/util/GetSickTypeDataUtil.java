package com.nyw.pets.activity.util;

import java.util.List;

public class GetSickTypeDataUtil {


    /**
     * code : 1
     * message : ok
     * data : {"datalist":[{"id":2,"title":"肠胃","child":[{"id":2,"cate_id":2,"title":"拉稀"}]}]}
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
        private List<DatalistBean> datalist;

        public List<DatalistBean> getDatalist() {
            return datalist;
        }

        public void setDatalist(List<DatalistBean> datalist) {
            this.datalist = datalist;
        }

        public static class DatalistBean {
            /**
             * id : 2
             * title : 肠胃
             * child : [{"id":2,"cate_id":2,"title":"拉稀"}]
             */

            private int id;
            private String title;
            private List<ChildBean> child;

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

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * id : 2
                 * cate_id : 2
                 * title : 拉稀
                 */

                private int id;
                private int cate_id;
                private String title;

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
            }
        }
    }
}
