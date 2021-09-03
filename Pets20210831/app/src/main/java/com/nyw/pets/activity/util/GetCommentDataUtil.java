package com.nyw.pets.activity.util;

import java.util.List;

public class GetCommentDataUtil {


    /**
     * code : 1
     * message : 成功
     * data : {"datalist":[{"id":246,"content":"333","parent_id":244,"imgs":[],"total_like":0,"comment_total":0,"extend":[],"create_time":1594976667,"user":{"id":12,"nickname":"哈哈哈","avatar":"2020/06/3c62f202006161620583873.png"},"reply":{"avatar":"","id":0,"nickname":""},"is_good":false,"is_owner":1}],"limit":"15","page":1,"total":1}
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
         * datalist : [{"id":246,"content":"333","parent_id":244,"imgs":[],"total_like":0,"comment_total":0,"extend":[],"create_time":1594976667,"user":{"id":12,"nickname":"哈哈哈","avatar":"2020/06/3c62f202006161620583873.png"},"reply":{"avatar":"","id":0,"nickname":""},"is_good":false,"is_owner":1}]
         * limit : 15
         * page : 1
         * total : 1
         */

        private String limit;
        private int page;
        private int total;
        private List<DatalistBean> datalist;

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
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
             * id : 246
             * content : 333
             * parent_id : 244
             * imgs : []
             * total_like : 0
             * comment_total : 0
             * extend : []
             * create_time : 1594976667
             * user : {"id":12,"nickname":"哈哈哈","avatar":"2020/06/3c62f202006161620583873.png"}
             * reply : {"avatar":"","id":0,"nickname":""}
             * is_good : false
             * is_owner : 1
             */

            private int id;
            private String content;
            private int parent_id;
            private int total_like;
            private int comment_total;
            private int create_time;
            private UserBean user;
            private ReplyBean reply;
            private boolean is_good;
            private int is_owner;
            private List<?> imgs;
            private List<?> extend;

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

            public int getIs_owner() {
                return is_owner;
            }

            public void setIs_owner(int is_owner) {
                this.is_owner = is_owner;
            }

            public List<?> getImgs() {
                return imgs;
            }

            public void setImgs(List<?> imgs) {
                this.imgs = imgs;
            }

            public List<?> getExtend() {
                return extend;
            }

            public void setExtend(List<?> extend) {
                this.extend = extend;
            }

            public static class UserBean {
                /**
                 * id : 12
                 * nickname : 哈哈哈
                 * avatar : 2020/06/3c62f202006161620583873.png
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
