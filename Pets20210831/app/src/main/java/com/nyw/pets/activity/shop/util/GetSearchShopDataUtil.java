package com.nyw.pets.activity.shop.util;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSearchShopDataUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : {"page":1,"limit":15,"total":3,"list":[{"id":1,"pets_id":1,"type_id":1,"title":"猫粮","brief":"奥良。。。。","price":"99.00","stock":100,"sale":0,"icon":"2020/05/e3c7a202005281856083082.jpg","discount":0,"origin_price":"0.00","default_spe":{"id":3,"shop_id":1,"spe":"961","price":"99.00","default":1,"origin_price":"0.00","discount":0,"stock":938}},{"id":6,"pets_id":1,"type_id":1,"title":"火腿肠","brief":"麦富迪 宠物狗狗零食 鸡肉干400g 食力宠爱，部分商品每满199元","price":"30.00","stock":500,"sale":0,"icon":"2020/04/eab42202004030926519580.png","discount":1,"origin_price":"30.00","default_spe":{"id":18,"shop_id":6,"spe":"30","price":"30.00","default":1,"origin_price":"30.00","discount":1,"stock":29}},{"id":7,"pets_id":1,"type_id":1,"title":"鸡肉干","brief":"宠物狗狗零食 鸡肉干400g*3 食力宠爱，部分商品每满199元","price":"30.00","stock":200,"sale":12,"icon":"2020/04/eab42202004030926519580.png","discount":1,"origin_price":"30.00","default_spe":{"id":17,"shop_id":7,"spe":"30","price":"30.00","default":1,"origin_price":"30.00","discount":1,"stock":28}}]}
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
         * page : 1
         * limit : 15
         * total : 3
         * list : [{"id":1,"pets_id":1,"type_id":1,"title":"猫粮","brief":"奥良。。。。","price":"99.00","stock":100,"sale":0,"icon":"2020/05/e3c7a202005281856083082.jpg","discount":0,"origin_price":"0.00","default_spe":{"id":3,"shop_id":1,"spe":"961","price":"99.00","default":1,"origin_price":"0.00","discount":0,"stock":938}},{"id":6,"pets_id":1,"type_id":1,"title":"火腿肠","brief":"麦富迪 宠物狗狗零食 鸡肉干400g 食力宠爱，部分商品每满199元","price":"30.00","stock":500,"sale":0,"icon":"2020/04/eab42202004030926519580.png","discount":1,"origin_price":"30.00","default_spe":{"id":18,"shop_id":6,"spe":"30","price":"30.00","default":1,"origin_price":"30.00","discount":1,"stock":29}},{"id":7,"pets_id":1,"type_id":1,"title":"鸡肉干","brief":"宠物狗狗零食 鸡肉干400g*3 食力宠爱，部分商品每满199元","price":"30.00","stock":200,"sale":12,"icon":"2020/04/eab42202004030926519580.png","discount":1,"origin_price":"30.00","default_spe":{"id":17,"shop_id":7,"spe":"30","price":"30.00","default":1,"origin_price":"30.00","discount":1,"stock":28}}]
         */

        private int page;
        private int limit;
        private int total;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

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
             * default_spe : {"id":3,"shop_id":1,"spe":"961","price":"99.00","default":1,"origin_price":"0.00","discount":0,"stock":938}
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

            public static class DefaultSpeBean {
                /**
                 * id : 3
                 * shop_id : 1
                 * spe : 961
                 * price : 99.00
                 * default : 1
                 * origin_price : 0.00
                 * discount : 0
                 * stock : 938
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
}
