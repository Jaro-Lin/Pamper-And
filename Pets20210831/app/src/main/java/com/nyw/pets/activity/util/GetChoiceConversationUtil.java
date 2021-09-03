package com.nyw.pets.activity.util;

import java.util.List;

public class GetChoiceConversationUtil {


    /**
     * code : 1
     * message : 请求成功！
     * data : {"total":3,"list":[{"id":4,"use_num":5,"theme_title":"每日新鲜事","is_follow":"0"},{"id":5,"use_num":0,"theme_title":"每日一说","is_follow":"0"},{"id":8,"use_num":0,"theme_title":"每日正能量分享","is_follow":"0"}]}
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
         * total : 3
         * list : [{"id":4,"use_num":5,"theme_title":"每日新鲜事","is_follow":"0"},{"id":5,"use_num":0,"theme_title":"每日一说","is_follow":"0"},{"id":8,"use_num":0,"theme_title":"每日正能量分享","is_follow":"0"}]
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
             * id : 4
             * use_num : 5
             * theme_title : 每日新鲜事
             * is_follow : 0
             */

            private int id;
            private int use_num;
            private String theme_title;
            private String is_follow;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUse_num() {
                return use_num;
            }

            public void setUse_num(int use_num) {
                this.use_num = use_num;
            }

            public String getTheme_title() {
                return theme_title;
            }

            public void setTheme_title(String theme_title) {
                this.theme_title = theme_title;
            }

            public String getIs_follow() {
                return is_follow;
            }

            public void setIs_follow(String is_follow) {
                this.is_follow = is_follow;
            }
        }
    }
}
