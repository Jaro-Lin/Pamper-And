package com.nyw.pets.activity.shop.util;

import java.util.List;

public class GetShopOrderInfoDataUtil {


    /**
     * code : 1
     * message : 下单成功！
     * data : {"list":[{"price":"129.00","spe_id":"2","shop_id":"1","uid":"z4K20Xq7twQn2renyIwde6X2CgrSWwOx","number":"1","order_id":"20200524114404420587"}],"order":{"uid":"z4K20Xq7twQn2renyIwde6X2CgrSWwOx","order_id":"20200524114404420587","address_id":"7","coupon_id":0,"shop_total_price":"129.00","freight_price":0,"coupon_price":0,"pay":"129.00","mark":"","create_time":1590291844,"province":"四川省","city":"成都市","area":"青羊区","details_address":"9966336666","addressee":"gay and","telephone":"18977092827","id":"38"}}
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
         * list : [{"price":"129.00","spe_id":"2","shop_id":"1","uid":"z4K20Xq7twQn2renyIwde6X2CgrSWwOx","number":"1","order_id":"20200524114404420587"}]
         * order : {"uid":"z4K20Xq7twQn2renyIwde6X2CgrSWwOx","order_id":"20200524114404420587","address_id":"7","coupon_id":0,"shop_total_price":"129.00","freight_price":0,"coupon_price":0,"pay":"129.00","mark":"","create_time":1590291844,"province":"四川省","city":"成都市","area":"青羊区","details_address":"9966336666","addressee":"gay and","telephone":"18977092827","id":"38"}
         */

        private OrderBean order;
        private List<ListBean> list;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class OrderBean {
            /**
             * uid : z4K20Xq7twQn2renyIwde6X2CgrSWwOx
             * order_id : 20200524114404420587
             * address_id : 7
             * coupon_id : 0
             * shop_total_price : 129.00
             * freight_price : 0
             * coupon_price : 0
             * pay : 129.00
             * mark :
             * create_time : 1590291844
             * province : 四川省
             * city : 成都市
             * area : 青羊区
             * details_address : 9966336666
             * addressee : gay and
             * telephone : 18977092827
             * id : 38
             */

            private String uid;
            private String order_id;
            private String address_id;
            private int coupon_id;
            private String shop_total_price;
            private String freight_price;
            private String coupon_price;
            private String pay;
            private String mark;
            private int create_time;
            private String province;
            private String city;
            private String area;
            private String details_address;
            private String addressee;
            private String telephone;
            private String id;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public int getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(int coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getShop_total_price() {
                return shop_total_price;
            }

            public void setShop_total_price(String shop_total_price) {
                this.shop_total_price = shop_total_price;
            }

            public String getFreight_price() {
                return freight_price;
            }

            public void setFreight_price(String freight_price) {
                this.freight_price = freight_price;
            }

            public String getCoupon_price() {
                return coupon_price;
            }

            public void setCoupon_price(String coupon_price) {
                this.coupon_price = coupon_price;
            }

            public String getPay() {
                return pay;
            }

            public void setPay(String pay) {
                this.pay = pay;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getDetails_address() {
                return details_address;
            }

            public void setDetails_address(String details_address) {
                this.details_address = details_address;
            }

            public String getAddressee() {
                return addressee;
            }

            public void setAddressee(String addressee) {
                this.addressee = addressee;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class ListBean {
            /**
             * price : 129.00
             * spe_id : 2
             * shop_id : 1
             * uid : z4K20Xq7twQn2renyIwde6X2CgrSWwOx
             * number : 1
             * order_id : 20200524114404420587
             */

            private String price;
            private String spe_id;
            private String shop_id;
            private String uid;
            private String number;
            private String order_id;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSpe_id() {
                return spe_id;
            }

            public void setSpe_id(String spe_id) {
                this.spe_id = spe_id;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }
        }
    }
}
