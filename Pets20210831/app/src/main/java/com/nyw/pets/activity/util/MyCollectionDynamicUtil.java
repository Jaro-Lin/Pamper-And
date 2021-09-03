package com.nyw.pets.activity.util;

import java.util.List;

public class MyCollectionDynamicUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":4,"list":[{"post_id":"18","uid":"z4K20Xq7twQn2renyIwde6X2CgrSWwOx","nickname":"哈哈哈","server":"http://qiniu.lovelovepets.com/","avatar":"2020/04/7907a202004281048577350.jpg","content":"哈哈(ಡωಡ)hiahiahia","image":[{"address":"2020/04/0d3c7202004261310514641.jpg"}],"video":[{"address":"2020/04/0d3c7202004261310514641.jpg"}],"theme_id":4,"theme":"每日新鲜事","comment_total":0,"good":1,"if_good":1,"collection":1,"if_collection":1,"add_time":"1587877864"}]}
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
         * list : [{"post_id":"18","uid":"z4K20Xq7twQn2renyIwde6X2CgrSWwOx","nickname":"哈哈哈","server":"http://qiniu.lovelovepets.com/","avatar":"2020/04/7907a202004281048577350.jpg","content":"哈哈(ಡωಡ)hiahiahia","image":[{"address":"2020/04/0d3c7202004261310514641.jpg"}],"video":[{"address":"2020/04/0d3c7202004261310514641.jpg"}],"theme_id":4,"theme":"每日新鲜事","comment_total":0,"good":1,"if_good":1,"collection":1,"if_collection":1,"add_time":"1587877864"}]
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
             * post_id : 18
             * uid : z4K20Xq7twQn2renyIwde6X2CgrSWwOx
             * nickname : 哈哈哈
             * server : http://qiniu.lovelovepets.com/
             * avatar : 2020/04/7907a202004281048577350.jpg
             * content : 哈哈(ಡωಡ)hiahiahia
             * image : [{"address":"2020/04/0d3c7202004261310514641.jpg"}]
             * video : [{"address":"2020/04/0d3c7202004261310514641.jpg"}]
             * theme_id : 4
             * theme : 每日新鲜事
             * comment_total : 0
             * good : 1
             * if_good : 1
             * collection : 1
             * if_collection : 1
             * add_time : 1587877864
             */

            private String post_id;
            private String uid;
            private String nickname;
            private String server;
            private String avatar;
            private String content;
            private int theme_id;
            private String theme;
            private int comment_total;
            private int good;
            private int if_good;
            private int collection;
            private int if_collection;
            private String add_time;
            private List<ImageBean> image;
            private List<VideoBean> video;
            private String comment_id;

            public String getComment_id() {
                return comment_id;
            }

            public void setComment_id(String comment_id) {
                this.comment_id = comment_id;
            }

            public String getPost_id() {
                return post_id;
            }

            public void setPost_id(String post_id) {
                this.post_id = post_id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getTheme_id() {
                return theme_id;
            }

            public void setTheme_id(int theme_id) {
                this.theme_id = theme_id;
            }

            public String getTheme() {
                return theme;
            }

            public void setTheme(String theme) {
                this.theme = theme;
            }

            public int getComment_total() {
                return comment_total;
            }

            public void setComment_total(int comment_total) {
                this.comment_total = comment_total;
            }

            public int getGood() {
                return good;
            }

            public void setGood(int good) {
                this.good = good;
            }

            public int getIf_good() {
                return if_good;
            }

            public void setIf_good(int if_good) {
                this.if_good = if_good;
            }

            public int getCollection() {
                return collection;
            }

            public void setCollection(int collection) {
                this.collection = collection;
            }

            public int getIf_collection() {
                return if_collection;
            }

            public void setIf_collection(int if_collection) {
                this.if_collection = if_collection;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public List<ImageBean> getImage() {
                return image;
            }

            public void setImage(List<ImageBean> image) {
                this.image = image;
            }

            public List<VideoBean> getVideo() {
                return video;
            }

            public void setVideo(List<VideoBean> video) {
                this.video = video;
            }

            public static class ImageBean {
                /**
                 * address : 2020/04/0d3c7202004261310514641.jpg
                 */

                private String address;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }
            }

            public static class VideoBean {
                /**
                 * address : 2020/04/0d3c7202004261310514641.jpg
                 */

                private String address;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }
            }
        }
    }
}
