package com.nyw.pets.activity.shop.util;

public class GetSginDataUtil   {

    /**
     * code : 1
     * message : 成功
     * data : {"total":1,"sign":true,"coupon":{"id":14,"coupon_id":1,"start_time":"1590053239","end_time":"1590312439","title":"满30元可用","content":"五一回馈用户优惠！","price":"3.00","has_price":"30.00"}}
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
         * sign : true
         * coupon : {"id":14,"coupon_id":1,"start_time":"1590053239","end_time":"1590312439","title":"满30元可用","content":"五一回馈用户优惠！","price":"3.00","has_price":"30.00"}
         */

        private int total;
        private int sign;
        private CouponBean coupon;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int isSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }

        public static class CouponBean {
            /**
             * id : 14
             * coupon_id : 1
             * start_time : 1590053239
             * end_time : 1590312439
             * title : 满30元可用
             * content : 五一回馈用户优惠！
             * price : 3.00
             * has_price : 30.00
             */

            private int id;
            private int coupon_id;
            private String start_time;
            private String end_time;
            private String title;
            private String content;
            private String price;
            private String has_price;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(int coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getHas_price() {
                return has_price;
            }

            public void setHas_price(String has_price) {
                this.has_price = has_price;
            }
        }
    }
}
