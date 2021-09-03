package com.nyw.pets.activity.util;

import java.util.List;

public class GetShopCommentUtil {


    /**
     * code : 1
     * message : 成功
     * data : {"datalist":[{"id":38,"content":"阿哦哦哦哦中评","parent_id":0,"imgs":["2020/05/63d16202005281836459472.jpeg"],"total_like":1,"comment_total":0,"extend":{"star":"3","spe":null},"create_time":1591066275,"user":{"id":18,"nickname":"smile","avatar":"2020/05/7f995202005271459572226.png"},"reply":{"avatar":"","id":0,"nickname":""},"is_good":true},{"id":43,"content":"你好啊","parent_id":0,"imgs":[],"total_like":0,"comment_total":0,"extend":{"star":5,"spe":"961"},"create_time":1591428573,"user":{"id":18,"nickname":"smile","avatar":"2020/05/7f995202005271459572226.png"},"reply":{"avatar":"","id":0,"nickname":""},"is_good":false},{"id":72,"content":"测试评价，这哈真好","parent_id":0,"imgs":["2020/06/4c57e202006121146573182.jpg"],"total_like":0,"comment_total":0,"extend":{"star":"1","spe":"961"},"create_time":1591933622,"user":{"id":12,"nickname":"哈哈哈","avatar":"2020/04/7907a202004281048577350.jpg"},"reply":{"avatar":"","id":0,"nickname":""},"is_good":false}],"limit":15,"page":1,"total":3}
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
         * datalist : [{"id":38,"content":"阿哦哦哦哦中评","parent_id":0,"imgs":["2020/05/63d16202005281836459472.jpeg"],"total_like":1,"comment_total":0,"extend":{"star":"3","spe":null},"create_time":1591066275,"user":{"id":18,"nickname":"smile","avatar":"2020/05/7f995202005271459572226.png"},"reply":{"avatar":"","id":0,"nickname":""},"is_good":true},{"id":43,"content":"你好啊","parent_id":0,"imgs":[],"total_like":0,"comment_total":0,"extend":{"star":5,"spe":"961"},"create_time":1591428573,"user":{"id":18,"nickname":"smile","avatar":"2020/05/7f995202005271459572226.png"},"reply":{"avatar":"","id":0,"nickname":""},"is_good":false},{"id":72,"content":"测试评价，这哈真好","parent_id":0,"imgs":["2020/06/4c57e202006121146573182.jpg"],"total_like":0,"comment_total":0,"extend":{"star":"1","spe":"961"},"create_time":1591933622,"user":{"id":12,"nickname":"哈哈哈","avatar":"2020/04/7907a202004281048577350.jpg"},"reply":{"avatar":"","id":0,"nickname":""},"is_good":false}]
         * limit : 15
         * page : 1
         * total : 3
         */

        private int limit;
        private int page;
        private int total;
        private List<DatalistBean> datalist;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatalistBean> getDatalist() {
            return datalist;
        }

        public void setDatalist(List<DatalistBean> datalist) {
            this.datalist = datalist;
        }

        public static class DatalistBean {
            /**
             * id : 38
             * content : 阿哦哦哦哦中评
             * parent_id : 0
             * imgs : ["2020/05/63d16202005281836459472.jpeg"]
             * total_like : 1
             * comment_total : 0
             * extend : {"star":"3","spe":null}
             * create_time : 1591066275
             * user : {"id":18,"nickname":"smile","avatar":"2020/05/7f995202005271459572226.png"}
             * reply : {"avatar":"","id":0,"nickname":""}
             * is_good : true
             */

            private int id;
            private String content;
            private int parent_id;
            private int total_like;
            private int comment_total;
            private ExtendBean extend;
            private int create_time;
            private UserBean user;
            private ReplyBean reply;
            private boolean is_good;
            private List<String> imgs;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getTotal_like() {
                return total_like;
            }

            public void setTotal_like(int total_like) {
                this.total_like = total_like;
            }

            public int getComment_total() {
                return comment_total;
            }

            public void setComment_total(int comment_total) {
                this.comment_total = comment_total;
            }

            public ExtendBean getExtend() {
                return extend;
            }

            public void setExtend(ExtendBean extend) {
                this.extend = extend;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public ReplyBean getReply() {
                return reply;
            }

            public void setReply(ReplyBean reply) {
                this.reply = reply;
            }

            public boolean isIs_good() {
                return is_good;
            }

            public void setIs_good(boolean is_good) {
                this.is_good = is_good;
            }

            public List<String> getImgs() {
                return imgs;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }

            public static class ExtendBean {
                /**
                 * star : 3
                 * spe : null
                 */

                private String star;
                private Object spe;

                public String getStar() {
                    return star;
                }

                public void setStar(String star) {
                    this.star = star;
                }

                public Object getSpe() {
                    return spe;
                }

                public void setSpe(Object spe) {
                    this.spe = spe;
                }
            }

            public static class UserBean {
                /**
                 * id : 18
                 * nickname : smile
                 * avatar : 2020/05/7f995202005271459572226.png
                 */

                private int id;
                private String nickname;
                private String avatar;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }
            }

            public static class ReplyBean {
                /**
                 * avatar :
                 * id : 0
                 * nickname :
                 */

                private String avatar;
                private int id;
                private String nickname;

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }
            }
        }
    }
}
