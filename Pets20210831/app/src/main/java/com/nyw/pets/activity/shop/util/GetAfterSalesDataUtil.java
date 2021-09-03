package com.nyw.pets.activity.shop.util;

import java.util.List;

public class GetAfterSalesDataUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":2,"list":[{"shop":[{"title":"猫粮","icon":"2020/05/e3c7a202005281856083082.jpg","spe":"961","number":1}],"type":"退货并退款","price":"99.00","refund_status":0,"server":"http://qiniu.lovelovepets.com/","order_id":"20200606232436501517","state":0},{"shop":[{"title":"小鱼干","icon":"2020/04/eab42202004030926519580.png","spe":"1000","number":1}],"type":"申请退款退货","price":"13.00","refund_status":0,"server":"http://qiniu.lovelovepets.com/","order_id":"20200612115301982212","state":0}]}
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
         * total : 2
         * list : [{"shop":[{"title":"猫粮","icon":"2020/05/e3c7a202005281856083082.jpg","spe":"961","number":1}],"type":"退货并退款","price":"99.00","refund_status":0,"server":"http://qiniu.lovelovepets.com/","order_id":"20200606232436501517","state":0},{"shop":[{"title":"小鱼干","icon":"2020/04/eab42202004030926519580.png","spe":"1000","number":1}],"type":"申请退款退货","price":"13.00","refund_status":0,"server":"http://qiniu.lovelovepets.com/","order_id":"20200612115301982212","state":0}]
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
             * shop : [{"title":"猫粮","icon":"2020/05/e3c7a202005281856083082.jpg","spe":"961","number":1}]
             * type : 退货并退款
             * price : 99.00
             * refund_status : 0
             * server : http://qiniu.lovelovepets.com/
             * order_id : 20200606232436501517
             * state : 0
             */

            private String type;
            private String price;
            private int refund_status;
            private String server;
            private String order_id;
            private int state;
            private List<ShopBean> shop;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getRefund_status() {
                return refund_status;
            }

            public void setRefund_status(int refund_status) {
                this.refund_status = refund_status;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public List<ShopBean> getShop() {
                return shop;
            }

            public void setShop(List<ShopBean> shop) {
                this.shop = shop;
            }

            public static class ShopBean {
                /**
                 * title : 猫粮
                 * icon : 2020/05/e3c7a202005281856083082.jpg
                 * spe : 961
                 * number : 1
                 */

                private String title;
                private String icon;
                private String spe;
                private int number;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
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

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }
            }
        }
    }
}
