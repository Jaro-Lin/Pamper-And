package com.nyw.pets.activity.shop.util;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShopDataUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : {"id":1,"pets_id":1,"type_id":1,"title":"猫粮","brief":"奥良。。。。","price":"99.00","stock":100,"sale":0,"icon":"2020/05/e3c7a202005281856083082.jpg","discount":0,"origin_price":"0.00","details":[{"img":"2020/04/eab42202004030926519580.png"},{"img":"2020/04/eab42202004030926519580.png"}],"banner":[{"image":"2020/05/3878d20200528185639918.jpg"},{"image":"2020/05/dce8d202005281856447849.jpg"},{"image":"2020/05/509a0202005281856493549.jpg"},{"image":"2020/05/494f9202005281856549282.jpg"},{"image":"2020/05/76f49202005281856588383.jpg"}],"spe":[{"id":3,"shop_id":1,"spe":"961","price":"99.00","default":1,"origin_price":"0.00","discount":0,"stock":943},{"id":23,"shop_id":1,"spe":"952","price":"129.00","default":0,"origin_price":"888.00","discount":1,"stock":942},{"id":24,"shop_id":1,"spe":"987","price":"159.00","default":0,"origin_price":"0.00","discount":0,"stock":985}],"default_spe":{"id":3,"shop_id":1,"spe":"961","price":"99.00","default":1,"origin_price":"0.00","discount":0,"stock":943}}
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
         * id : 1
         * pets_id : 1
         * type_id : 1
         * title : 猫粮
         * brief : 奥良。。。。
         * price : 99.00
         * stock : 100
         * sale : 0
         * icon : 2020/05/e3c7a202005281856083082.jpg
         * discount : 0
         * origin_price : 0.00
         * details : [{"img":"2020/04/eab42202004030926519580.png"},{"img":"2020/04/eab42202004030926519580.png"}]
         * banner : [{"image":"2020/05/3878d20200528185639918.jpg"},{"image":"2020/05/dce8d202005281856447849.jpg"},{"image":"2020/05/509a0202005281856493549.jpg"},{"image":"2020/05/494f9202005281856549282.jpg"},{"image":"2020/05/76f49202005281856588383.jpg"}]
         * spe : [{"id":3,"shop_id":1,"spe":"961","price":"99.00","default":1,"origin_price":"0.00","discount":0,"stock":943},{"id":23,"shop_id":1,"spe":"952","price":"129.00","default":0,"origin_price":"888.00","discount":1,"stock":942},{"id":24,"shop_id":1,"spe":"987","price":"159.00","default":0,"origin_price":"0.00","discount":0,"stock":985}]
         * default_spe : {"id":3,"shop_id":1,"spe":"961","price":"99.00","default":1,"origin_price":"0.00","discount":0,"stock":943}
         */

        private int id;
        private int pets_id;
        private int type_id;
        private String title;
        private String brief;
        private String price;
        private int stock;
        private int sale;
        private String icon;
        private int discount;
        private String origin_price;
        private DefaultSpeBean default_spe;
        private List<DetailsBean> details;
        private List<BannerBean> banner;
        private List<SpeBean> spe;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getSale() {
            return sale;
        }

        public void setSale(int sale) {
            this.sale = sale;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public String getOrigin_price() {
            return origin_price;
        }

        public void setOrigin_price(String origin_price) {
            this.origin_price = origin_price;
        }

        public DefaultSpeBean getDefault_spe() {
            return default_spe;
        }

        public void setDefault_spe(DefaultSpeBean default_spe) {
            this.default_spe = default_spe;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<SpeBean> getSpe() {
            return spe;
        }

        public void setSpe(List<SpeBean> spe) {
            this.spe = spe;
        }

        public static class DefaultSpeBean {
            /**
             * id : 3
             * shop_id : 1
             * spe : 961
             * price : 99.00
             * default : 1
             * origin_price : 0.00
             * discount : 0
             * stock : 943
             */

            private int id;
            private int shop_id;
            private String spe;
            private String price;
            @SerializedName("default")
            private int defaultX;
            private String origin_price;
            private int discount;
            private int stock;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public String getSpe() {
                return spe;
            }

            public void setSpe(String spe) {
                this.spe = spe;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getDefaultX() {
                return defaultX;
            }

            public void setDefaultX(int defaultX) {
                this.defaultX = defaultX;
            }

            public String getOrigin_price() {
                return origin_price;
            }

            public void setOrigin_price(String origin_price) {
                this.origin_price = origin_price;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }
        }

        public static class DetailsBean {
            /**
             * img : 2020/04/eab42202004030926519580.png
             */

            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class BannerBean {
            /**
             * image : 2020/05/3878d20200528185639918.jpg
             */

            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }

        public static class SpeBean {
            /**
             * id : 3
             * shop_id : 1
             * spe : 961
             * price : 99.00
             * default : 1
             * origin_price : 0.00
             * discount : 0
             * stock : 943
             */

            private int id;
            private int shop_id;
            private String spe;
            private String price;
            @SerializedName("default")
            private int defaultX;
            private String origin_price;
            private int discount;
            private int stock;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public String getSpe() {
                return spe;
            }

            public void setSpe(String spe) {
                this.spe = spe;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getDefaultX() {
                return defaultX;
            }

            public void setDefaultX(int defaultX) {
                this.defaultX = defaultX;
            }

            public String getOrigin_price() {
                return origin_price;
            }

            public void setOrigin_price(String origin_price) {
                this.origin_price = origin_price;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }
        }
    }
}
