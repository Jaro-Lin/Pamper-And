package com.nyw.pets.activity.util;

import java.util.List;

public class GetDynamicOtherDataUtil {


    /**
     * code : 1
     * message : 获取成功!
     * data : {"total":2,"list":[{"post_id":50,"uid":"lOQZftRIn6cYfiYE883au9MQF83XhBo1","avatar":"2020/06/302d6202006030957264658.png","server":"http://qiniu.lovelovepets.com/","nickname":"昵称","content":"呵呵","post_city":"","theme_id":1,"theme_title":"","add_time":"1591094417","comment_total":0,"good":2,"if_good":0,"collection":3,"if_collection":1,"image":[{"address":"2020/06/2cf7c202006021840091750.png"}],"video":[{"address":"2020/04/83ca8202004261541219849.mp4"}],"comment_id":41}]}
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
         * list : [{"post_id":50,"uid":"lOQZftRIn6cYfiYE883au9MQF83XhBo1","avatar":"2020/06/302d6202006030957264658.png","server":"http://qiniu.lovelovepets.com/","nickname":"昵称","content":"呵呵","post_city":"","theme_id":1,"theme_title":"","add_time":"1591094417","comment_total":0,"good":2,"if_good":0,"collection":3,"if_collection":1,"image":[{"address":"2020/06/2cf7c202006021840091750.png"}],"video":[{"address":"2020/04/83ca8202004261541219849.mp4"}],"comment_id":41}]
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
             * post_id : 50
             * uid : lOQZftRIn6cYfiYE883au9MQF83XhBo1
             * avatar : 2020/06/302d6202006030957264658.png
             * server : http://qiniu.lovelovepets.com/
             * nickname : 昵称
             * content : 呵呵
             * post_city :
             * theme_id : 1
             * theme_title :
             * add_time : 1591094417
             * comment_total : 0
             * good : 2
             * if_good : 0
             * collection : 3
             * if_collection : 1
             * image : [{"address":"2020/06/2cf7c202006021840091750.png"}]
             * video : [{"address":"2020/04/83ca8202004261541219849.mp4"}]
             * comment_id : 41
             */

            private int post_id;
            private String uid;
            private String avatar;
            private String server;
            private String nickname;
            private String content;
            private String post_city;
            private int theme_id;
            private String theme_title;
            private String add_time;
            private int comment_total;
            private int good;
            private int if_good;
            private int collection;
            private int if_collection;
            private int comment_id;
            private List<ImageBean> image;
            private List<VideoBean> video;

            public int getPost_id() {
                return post_id;
            }

            public void setPost_id(int post_id) {
                this.post_id = post_id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPost_city() {
                return post_city;
            }

            public void setPost_city(String post_city) {
                this.post_city = post_city;
            }

            public int getTheme_id() {
                return theme_id;
            }

            public void setTheme_id(int theme_id) {
                this.theme_id = theme_id;
            }

            public String getTheme_title() {
                return theme_title;
            }

            public void setTheme_title(String theme_title) {
                this.theme_title = theme_title;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
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

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
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
                 * address : 2020/06/2cf7c202006021840091750.png
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
                 * address : 2020/04/83ca8202004261541219849.mp4
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
