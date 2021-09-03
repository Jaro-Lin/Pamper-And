package com.nyw.pets.activity.util;

import java.util.List;

public class GetPreferentialDataUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":4,"list":[{"id":1,"pets_id":1,"type_id":1,"title":"猫粮","sale":0,"icon":"2020/05/e3c7a202005281856083082.jpg","spe_id":24,"price":159,"stock":983,"discount":0,"origin_price":"0.00"},{"id":20,"pets_id":2,"type_id":10,"title":"不吃包退】好主人狗粮 全犬种通用5斤泰迪金毛拉布拉多比熊萨摩耶边牧法斗柯基阿拉斯加幼犬狗粮天然粮","sale":0,"icon":"2020/05/ee48f202005281906156443.jpg","spe_id":30,"price":50,"stock":30,"discount":1,"origin_price":"30.00"},{"id":2,"pets_id":1,"type_id":2,"title":"小鱼干","sale":0,"icon":"2020/04/eab42202004030926519580.png","spe_id":22,"price":45,"stock":987,"discount":0,"origin_price":"0.00"},{"id":1,"pets_id":1,"type_id":1,"title":"猫粮","sale":0,"icon":"2020/05/e3c7a202005281856083082.jpg","spe_id":23,"price":129,"stock":940,"discount":1,"origin_price":"888.00"}]}
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
         * list : [{"id":1,"pets_id":1,"type_id":1,"title":"猫粮","sale":0,"icon":"2020/05/e3c7a202005281856083082.jpg","spe_id":24,"price":159,"stock":983,"discount":0,"origin_price":"0.00"},{"id":20,"pets_id":2,"type_id":10,"title":"不吃包退】好主人狗粮 全犬种通用5斤泰迪金毛拉布拉多比熊萨摩耶边牧法斗柯基阿拉斯加幼犬狗粮天然粮","sale":0,"icon":"2020/05/ee48f202005281906156443.jpg","spe_id":30,"price":50,"stock":30,"discount":1,"origin_price":"30.00"},{"id":2,"pets_id":1,"type_id":2,"title":"小鱼干","sale":0,"icon":"2020/04/eab42202004030926519580.png","spe_id":22,"price":45,"stock":987,"discount":0,"origin_price":"0.00"},{"id":1,"pets_id":1,"type_id":1,"title":"猫粮","sale":0,"icon":"2020/05/e3c7a202005281856083082.jpg","spe_id":23,"price":129,"stock":940,"discount":1,"origin_price":"888.00"}]
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
             * pets_id : 1
             * type_id : 1
             * title : 猫粮
             * sale : 0
             * icon : 2020/05/e3c7a202005281856083082.jpg
             * spe_id : 24
             * price : 159
             * stock : 983
             * discount : 0
             * origin_price : 0.00
             */

            private int id;
            private int pets_id;
            private int type_id;
            private String title;
            private int sale;
            private String icon;
            private int spe_id;
            private int price;
            private int stock;
            private int discount;
            private String origin_price;

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

            public int getSpe_id() {
                return spe_id;
            }

            public void setSpe_id(int spe_id) {
                this.spe_id = spe_id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
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
        }
    }
}
