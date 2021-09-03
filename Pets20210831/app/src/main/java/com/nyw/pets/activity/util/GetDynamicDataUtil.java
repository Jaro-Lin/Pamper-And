package com.nyw.pets.activity.util;

import java.util.List;

public class GetDynamicDataUtil {


    /**
     * code : 1
     * message : 请求成功！
     * data : {"total":5,"list":[{"id":1,"content":"有志者，事竟成，破釜沉舟，百二秦关终归楚;苦心人，天不负， 卧薪尝胆，三千越甲可吞吴。早安！","city":"南宁","nickname":"测试者","server":"http://qiniu.lovelovepets.com/","avatar":"2020/04/d320d202004141743482394.png","theme_id":1,"theme_title":"每日正能量分享","post_time":"1759865435","images":[{"address":"2020/04/0d3c7202004261310514641.jpg"},{"address":"2020/04/0d3c7202004261310514641.jpg"},{"address":"2020/04/0d3c7202004261310514641.jpg"}],"video":[{"address":"2020/04/1c01e202004261530218648.mp4"}],"good":1,"comment":0,"collection":2}]}
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
         * total : 5
         * list : [{"id":1,"content":"有志者，事竟成，破釜沉舟，百二秦关终归楚;苦心人，天不负， 卧薪尝胆，三千越甲可吞吴。早安！","city":"南宁","nickname":"测试者","server":"http://qiniu.lovelovepets.com/","avatar":"2020/04/d320d202004141743482394.png","theme_id":1,"theme_title":"每日正能量分享","post_time":"1759865435","images":[{"address":"2020/04/0d3c7202004261310514641.jpg"},{"address":"2020/04/0d3c7202004261310514641.jpg"},{"address":"2020/04/0d3c7202004261310514641.jpg"}],"video":[{"address":"2020/04/1c01e202004261530218648.mp4"}],"good":1,"comment":0,"collection":2}]
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
             * content : 有志者，事竟成，破釜沉舟，百二秦关终归楚;苦心人，天不负， 卧薪尝胆，三千越甲可吞吴。早安！
             * city : 南宁
             * nickname : 测试者
             * server : http://qiniu.lovelovepets.com/
             * avatar : 2020/04/d320d202004141743482394.png
             * theme_id : 1
             * theme_title : 每日正能量分享
             * post_time : 1759865435
             * images : [{"address":"2020/04/0d3c7202004261310514641.jpg"},{"address":"2020/04/0d3c7202004261310514641.jpg"},{"address":"2020/04/0d3c7202004261310514641.jpg"}]
             * video : [{"address":"2020/04/1c01e202004261530218648.mp4"}]
             * good : 1
             * comment : 0
             * collection : 2
             */

            private int id;
            private String post_id;
            private String content;
            private String city;
            private String nickname;
            private String server;
            private String avatar;
            String uid;
            private int theme_id;
            private String theme_title;
            private String post_time;
            private int good;
            private int comment;
            private int collection;
            private int if_good;
            private int if_collection;
            private String comment_id;
            private List<ImagesBean> images;
            private List<VideoBean> video;

            public String getComment_id() {
                return comment_id;
            }

            public void setComment_id(String comment_id) {
                this.comment_id = comment_id;
            }

            public int getIf_good() {
                return if_good;
            }

            public void setIf_good(int if_good) {
                this.if_good = if_good;
            }

            public int getIf_collection() {
                return if_collection;
            }

            public void setIf_collection(int if_collection) {
                this.if_collection = if_collection;
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

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
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

            public String getPost_time() {
                return post_time;
            }

            public void setPost_time(String post_time) {
                this.post_time = post_time;
            }

            public int getGood() {
                return good;
            }

            public void setGood(int good) {
                this.good = good;
            }

            public int getComment() {
                return comment;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }

            public int getCollection() {
                return collection;
            }

            public void setCollection(int collection) {
                this.collection = collection;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public List<VideoBean> getVideo() {
                return video;
            }

            public void setVideo(List<VideoBean> video) {
                this.video = video;
            }

            public static class ImagesBean {
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
                 * address : 2020/04/1c01e202004261530218648.mp4
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
