package com.nyw.pets.activity.util;

import java.util.List;

public class GetTopicDynamicUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":1,"list":[{"id":51,"content":"黄明昊","city":"南宁市","nickname":"哦lol里咯","server":"http://qiniu.lovelovepets.com/","avatar":"2020/06/63e85202006030808148908.png","uid":"nIjjKhIbsfyObmxmuYIdtaObCCOypGCz","theme_id":2,"theme_title":"美图分享","post_time":"1591142423","images":[{"image":"2020/06/f6bde202006030800087863.png"}],"video":[{"address":"123.mp4"}],"good":0,"comment":0,"collection":3,"if_good":0,"if_collection":1,"comment_id":42}]}
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
         * list : [{"id":51,"content":"黄明昊","city":"南宁市","nickname":"哦lol里咯","server":"http://qiniu.lovelovepets.com/","avatar":"2020/06/63e85202006030808148908.png","uid":"nIjjKhIbsfyObmxmuYIdtaObCCOypGCz","theme_id":2,"theme_title":"美图分享","post_time":"1591142423","images":[{"image":"2020/06/f6bde202006030800087863.png"}],"video":[{"address":"123.mp4"}],"good":0,"comment":0,"collection":3,"if_good":0,"if_collection":1,"comment_id":42}]
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
             * id : 51
             * content : 黄明昊
             * city : 南宁市
             * nickname : 哦lol里咯
             * server : http://qiniu.lovelovepets.com/
             * avatar : 2020/06/63e85202006030808148908.png
             * uid : nIjjKhIbsfyObmxmuYIdtaObCCOypGCz
             * theme_id : 2
             * theme_title : 美图分享
             * post_time : 1591142423
             * images : [{"image":"2020/06/f6bde202006030800087863.png"}]
             * video : [{"address":"123.mp4"}]
             * good : 0
             * comment : 0
             * collection : 3
             * if_good : 0
             * if_collection : 1
             * comment_id : 42
             */

            private int id;
            private String content;
            private String city;
            private String nickname;
            private String server;
            private String avatar;
            private String uid;
            private int theme_id;
            private String theme_title;
            private String post_time;
            private int good;
            private int comment;
            private int collection;
            private int if_good;
            private int if_collection;
            private int comment_id;
            private List<ImagesBean> images;
            private List<VideoBean> video;

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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
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

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
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
                 * image : 2020/06/f6bde202006030800087863.png
                 */

                private String image;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }
            }

            public static class VideoBean {
                /**
                 * address : 123.mp4
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
