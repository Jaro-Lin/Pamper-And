package com.nyw.pets.activity.util;

import java.util.List;

public class GetMyFansDataUtil {

    /**
     * code : 1
     * message : 获取成功!
     * data : {"total":1,"list":[{"uid":"OA1yBS6SrThAoUf3mWelbTnXaonGpJqY","server":"http://qiniu.lovelovepets.com/","avatar":"2020/04/d320d202004141743482394.png","nickname":"测试者","spe":"这个人什么都没有留下...","if_follow":0}]}
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
         * list : [{"uid":"OA1yBS6SrThAoUf3mWelbTnXaonGpJqY","server":"http://qiniu.lovelovepets.com/","avatar":"2020/04/d320d202004141743482394.png","nickname":"测试者","spe":"这个人什么都没有留下...","if_follow":0}]
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
             * uid : OA1yBS6SrThAoUf3mWelbTnXaonGpJqY
             * server : http://qiniu.lovelovepets.com/
             * avatar : 2020/04/d320d202004141743482394.png
             * nickname : 测试者
             * spe : 这个人什么都没有留下...
             * if_follow : 0
             */

            private String uid;
            private String server;
            private String avatar;
            private String nickname;
            private String spe;
            private int if_follow;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getSpe() {
                return spe;
            }

            public void setSpe(String spe) {
                this.spe = spe;
            }

            public int getIf_follow() {
                return if_follow;
            }

            public void setIf_follow(int if_follow) {
                this.if_follow = if_follow;
            }
        }
    }
}
