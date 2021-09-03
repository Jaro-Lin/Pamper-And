package com.nyw.pets.activity.shop.util;

import java.util.List;

public class GetShopCartDataUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":4,"data":[{"pets_id":1,"type_id":1,"shop_id":1,"spe_id":1,"title":"猫粮","server":"http://qiniu.lovelovepets.com/","icon":"2020/04/eab42202004030926519580.png","spe":"500g","price":"99.00","number":8},{"pets_id":1,"type_id":1,"shop_id":1,"spe_id":3,"title":"猫粮","server":"http://qiniu.lovelovepets.com/","icon":"2020/04/eab42202004030926519580.png","spe":"900g","price":"159.00","number":7},{"pets_id":1,"type_id":1,"shop_id":1,"spe_id":2,"title":"猫粮","server":"http://qiniu.lovelovepets.com/","icon":"2020/04/eab42202004030926519580.png","spe":"500g","price":"129.00","number":3},{"pets_id":1,"type_id":2,"shop_id":2,"spe_id":5,"title":"小鱼干","server":"http://qiniu.lovelovepets.com/","icon":"2020/04/eab42202004030926519580.png","spe":"1000g/包","price":"45.00","number":1}]}
     */

    private int code;
    private String message;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 4
         * data : [{"pets_id":1,"type_id":1,"shop_id":1,"spe_id":1,"title":"猫粮","server":"http://qiniu.lovelovepets.com/","icon":"2020/04/eab42202004030926519580.png","spe":"500g","price":"99.00","number":8},{"pets_id":1,"type_id":1,"shop_id":1,"spe_id":3,"title":"猫粮","server":"http://qiniu.lovelovepets.com/","icon":"2020/04/eab42202004030926519580.png","spe":"900g","price":"159.00","number":7},{"pets_id":1,"type_id":1,"shop_id":1,"spe_id":2,"title":"猫粮","server":"http://qiniu.lovelovepets.com/","icon":"2020/04/eab42202004030926519580.png","spe":"500g","price":"129.00","number":3},{"pets_id":1,"type_id":2,"shop_id":2,"spe_id":5,"title":"小鱼干","server":"http://qiniu.lovelovepets.com/","icon":"2020/04/eab42202004030926519580.png","spe":"1000g/包","price":"45.00","number":1}]
         */

        private int total;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * pets_id : 1
             * type_id : 1
             * shop_id : 1
             * spe_id : 1
             * title : 猫粮
             * server : http://qiniu.lovelovepets.com/
             * icon : 2020/04/eab42202004030926519580.png
             * spe : 500g
             * price : 99.00
             * number : 8
             */

            private int id;
            private int pets_id;
            private int type_id;
            private int shop_id;
            private int spe_id;
            private String title;
            private String server;
            private String icon;
            private String spe;
            private String price;
            private int number;

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

            public int getShop_id() {
                return shop_id;
            }

            public void setShop_id(int shop_id) {
                this.shop_id = shop_id;
            }

            public int getSpe_id() {
                return spe_id;
            }

            public void setSpe_id(int spe_id) {
                this.spe_id = spe_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
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

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }
        }
    }
}
