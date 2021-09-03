package com.nyw.pets.activity.util;

import java.util.List;

public class GetBannerDataUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : [{"id":1,"title":"优惠大促销","pets_id":1,"type_id":1,"shop_id":1,"server":"http://qiniu.lovelovepets.com/","image":"2020/04/eab42202004030926519580.png"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * title : 优惠大促销
         * pets_id : 1
         * type_id : 1
         * shop_id : 1
         * server : http://qiniu.lovelovepets.com/
         * image : 2020/04/eab42202004030926519580.png
         */

        private int id;
        private String title;
        private int pets_id;
        private int type_id;
        private int shop_id;
        private String server;
        private String image;

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

        public int getPets_id() {
            return pets_id;
        }

        public void setPets_id(int pets_id) {
            this.pets_id = pets_id;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
