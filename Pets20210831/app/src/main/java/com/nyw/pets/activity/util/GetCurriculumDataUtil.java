package com.nyw.pets.activity.util;

public class GetCurriculumDataUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : {"id":2,"title":"如何喂养猫","content":"如何喂养猫。。。。。","img":null,"type_id":1,"server":"http://qiniu.lovelovepets.com/","video":"2020/04/1c01e202004261530218648.mp4","study_number":13,"follow":0}
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
         * id : 2
         * title : 如何喂养猫
         * content : 如何喂养猫。。。。。
         * img : null
         * type_id : 1
         * server : http://qiniu.lovelovepets.com/
         * video : 2020/04/1c01e202004261530218648.mp4
         * study_number : 13
         * follow : 0
         */

        private int id;
        private String title;
        private String content;
        private String img;
        private int type_id;
        private String server;
        private String video;
        private int study_number;
        private int follow;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public int getStudy_number() {
            return study_number;
        }

        public void setStudy_number(int study_number) {
            this.study_number = study_number;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }
    }
}
