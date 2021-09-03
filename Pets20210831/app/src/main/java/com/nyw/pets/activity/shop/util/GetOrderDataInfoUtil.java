package com.nyw.pets.activity.shop.util;

import java.util.List;

public class GetOrderDataInfoUtil {


    /**
     * code : 1
     * message : 获取成功!
     * data : {"list":[{"id":206,"order_id":"20200624183022455715","is_pay":1,"is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"55566666566","put_type":"顺丰快递","shop_list":[{"id":212,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}},{"id":213,"shop_id":28,"number":1,"price":"169.00","shop":{"id":28,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/7a4dcc6b1c914cc31fefd62b9e9c1046.jpg"},"spe":{"id":39,"spe":"成犬15KG","price":169}}],"total_price":"211.90","shop_total":2,"server":"http://qiniu.lovelovepets.com/"},{"id":205,"order_id":"20200624182947578264","is_pay":1,"is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"899456556565","put_type":"天天速递","shop_list":[{"id":211,"shop_id":28,"number":1,"price":"169.00","shop":{"id":28,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/7a4dcc6b1c914cc31fefd62b9e9c1046.jpg"},"spe":{"id":39,"spe":"成犬15KG","price":169}}],"total_price":"169.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":204,"order_id":"20200624182922477384","is_pay":1,"is_send":0,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":null,"put_type":"","shop_list":[{"id":210,"shop_id":34,"number":1,"price":"128.00","shop":{"id":34,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/caced0060c35634b72c2cd3a37b7c42e.jpg"},"spe":{"id":54,"spe":"成犬15KG","price":128}}],"total_price":"128.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":203,"order_id":"20200624182904321092","is_pay":1,"is_send":0,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":null,"put_type":"","shop_list":[{"id":209,"shop_id":35,"number":1,"price":"12.00","shop":{"id":35,"pets_id":2,"type_id":10,"title":"麦富迪 狗狗零食磨牙棒 混合味220g","icon":"2020/06/3523fdd9723f94ea56d13e58e3b4a8a6.jpg"},"spe":{"id":57,"spe":"混合口味220G","price":12}}],"total_price":"12.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":202,"order_id":"20200624182845411549","is_pay":1,"is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"68965121554","put_type":"韵达快递","shop_list":[{"id":208,"shop_id":35,"number":1,"price":"12.00","shop":{"id":35,"pets_id":2,"type_id":10,"title":"麦富迪 狗狗零食磨牙棒 混合味220g","icon":"2020/06/3523fdd9723f94ea56d13e58e3b4a8a6.jpg"},"spe":{"id":57,"spe":"混合口味220G","price":12}}],"total_price":"12.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":201,"order_id":"20200624182819131321","is_pay":1,"is_send":0,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":null,"put_type":"","shop_list":[{"id":207,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}}],"total_price":"42.90","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":200,"order_id":"20200624182750220386","is_pay":1,"is_send":0,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":null,"put_type":"","shop_list":[{"id":206,"shop_id":34,"number":1,"price":"128.00","shop":{"id":34,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/caced0060c35634b72c2cd3a37b7c42e.jpg"},"spe":{"id":54,"spe":"成犬15KG","price":128}}],"total_price":"128.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":197,"order_id":"20200624175737845712","is_pay":1,"is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"13212132121321","put_type":"中通快递","shop_list":[{"id":203,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}}],"total_price":"42.90","shop_total":1,"server":"http://qiniu.lovelovepets.com/"}],"total":8}
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
         * list : [{"id":206,"order_id":"20200624183022455715","is_pay":1,"is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"55566666566","put_type":"顺丰快递","shop_list":[{"id":212,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}},{"id":213,"shop_id":28,"number":1,"price":"169.00","shop":{"id":28,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/7a4dcc6b1c914cc31fefd62b9e9c1046.jpg"},"spe":{"id":39,"spe":"成犬15KG","price":169}}],"total_price":"211.90","shop_total":2,"server":"http://qiniu.lovelovepets.com/"},{"id":205,"order_id":"20200624182947578264","is_pay":1,"is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"899456556565","put_type":"天天速递","shop_list":[{"id":211,"shop_id":28,"number":1,"price":"169.00","shop":{"id":28,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/7a4dcc6b1c914cc31fefd62b9e9c1046.jpg"},"spe":{"id":39,"spe":"成犬15KG","price":169}}],"total_price":"169.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":204,"order_id":"20200624182922477384","is_pay":1,"is_send":0,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":null,"put_type":"","shop_list":[{"id":210,"shop_id":34,"number":1,"price":"128.00","shop":{"id":34,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/caced0060c35634b72c2cd3a37b7c42e.jpg"},"spe":{"id":54,"spe":"成犬15KG","price":128}}],"total_price":"128.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":203,"order_id":"20200624182904321092","is_pay":1,"is_send":0,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":null,"put_type":"","shop_list":[{"id":209,"shop_id":35,"number":1,"price":"12.00","shop":{"id":35,"pets_id":2,"type_id":10,"title":"麦富迪 狗狗零食磨牙棒 混合味220g","icon":"2020/06/3523fdd9723f94ea56d13e58e3b4a8a6.jpg"},"spe":{"id":57,"spe":"混合口味220G","price":12}}],"total_price":"12.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":202,"order_id":"20200624182845411549","is_pay":1,"is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"68965121554","put_type":"韵达快递","shop_list":[{"id":208,"shop_id":35,"number":1,"price":"12.00","shop":{"id":35,"pets_id":2,"type_id":10,"title":"麦富迪 狗狗零食磨牙棒 混合味220g","icon":"2020/06/3523fdd9723f94ea56d13e58e3b4a8a6.jpg"},"spe":{"id":57,"spe":"混合口味220G","price":12}}],"total_price":"12.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":201,"order_id":"20200624182819131321","is_pay":1,"is_send":0,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":null,"put_type":"","shop_list":[{"id":207,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}}],"total_price":"42.90","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":200,"order_id":"20200624182750220386","is_pay":1,"is_send":0,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":null,"put_type":"","shop_list":[{"id":206,"shop_id":34,"number":1,"price":"128.00","shop":{"id":34,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/caced0060c35634b72c2cd3a37b7c42e.jpg"},"spe":{"id":54,"spe":"成犬15KG","price":128}}],"total_price":"128.00","shop_total":1,"server":"http://qiniu.lovelovepets.com/"},{"id":197,"order_id":"20200624175737845712","is_pay":1,"is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"13212132121321","put_type":"中通快递","shop_list":[{"id":203,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}}],"total_price":"42.90","shop_total":1,"server":"http://qiniu.lovelovepets.com/"}]
         * total : 8
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
             * id : 206
             * order_id : 20200624183022455715
             * is_pay : 1
             * is_send : 1
             * is_put : 0
             * is_comment : 0
             * is_cancel : 0
             * is_salelate : 0
             * logistics_id : 55566666566
             * put_type : 顺丰快递
             * shop_list : [{"id":212,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}},{"id":213,"shop_id":28,"number":1,"price":"169.00","shop":{"id":28,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/7a4dcc6b1c914cc31fefd62b9e9c1046.jpg"},"spe":{"id":39,"spe":"成犬15KG","price":169}}]
             * total_price : 211.90
             * shop_total : 2
             * server : http://qiniu.lovelovepets.com/
             */

            private int id;
            private String order_id;
            private int is_pay;
            private int is_send;
            private int is_put;
            private int is_comment;
            private int is_cancel;
            private int is_salelate;
            private String logistics_id;
            private String put_type;
            private String total_price;
            private int shop_total;
            private String server;
            private List<ShopListBean> shop_list;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public int getIs_pay() {
                return is_pay;
            }

            public void setIs_pay(int is_pay) {
                this.is_pay = is_pay;
            }

            public int getIs_send() {
                return is_send;
            }

            public void setIs_send(int is_send) {
                this.is_send = is_send;
            }

            public int getIs_put() {
                return is_put;
            }

            public void setIs_put(int is_put) {
                this.is_put = is_put;
            }

            public int getIs_comment() {
                return is_comment;
            }

            public void setIs_comment(int is_comment) {
                this.is_comment = is_comment;
            }

            public int getIs_cancel() {
                return is_cancel;
            }

            public void setIs_cancel(int is_cancel) {
                this.is_cancel = is_cancel;
            }

            public int getIs_salelate() {
                return is_salelate;
            }

            public void setIs_salelate(int is_salelate) {
                this.is_salelate = is_salelate;
            }

            public String getLogistics_id() {
                return logistics_id;
            }

            public void setLogistics_id(String logistics_id) {
                this.logistics_id = logistics_id;
            }

            public String getPut_type() {
                return put_type;
            }

            public void setPut_type(String put_type) {
                this.put_type = put_type;
            }

            public String getTotal_price() {
                return total_price;
            }

            public void setTotal_price(String total_price) {
                this.total_price = total_price;
            }

            public int getShop_total() {
                return shop_total;
            }

            public void setShop_total(int shop_total) {
                this.shop_total = shop_total;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public List<ShopListBean> getShop_list() {
                return shop_list;
            }

            public void setShop_list(List<ShopListBean> shop_list) {
                this.shop_list = shop_list;
            }

            public static class ShopListBean {
                /**
                 * id : 212
                 * shop_id : 29
                 * number : 1
                 * price : 42.90
                 * shop : {"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"}
                 * spe : {"id":41,"spe":"牛肉味1.8KG","price":42.9}
                 */

                private int id;
                private int shop_id;
                private int number;
                private String price;
                private ShopBean shop;
                private SpeBean spe;

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

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public ShopBean getShop() {
                    return shop;
                }

                public void setShop(ShopBean shop) {
                    this.shop = shop;
                }

                public SpeBean getSpe() {
                    return spe;
                }

                public void setSpe(SpeBean spe) {
                    this.spe = spe;
                }

                public static class ShopBean {
                    /**
                     * id : 29
                     * pets_id : 2
                     * type_id : 10
                     * title : 宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基
                     * icon : 2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg
                     */

                    private int id;
                    private int pets_id;
                    private int type_id;
                    private String title;
                    private String icon;

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

                    public String getIcon() {
                        return icon;
                    }

                    public void setIcon(String icon) {
                        this.icon = icon;
                    }
                }

                public static class SpeBean {
                    /**
                     * id : 41
                     * spe : 牛肉味1.8KG
                     * price : 42.9
                     */

                    private int id;
                    private String spe;
                    private double price;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getSpe() {
                        return spe;
                    }

                    public void setSpe(String spe) {
                        this.spe = spe;
                    }

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(double price) {
                        this.price = price;
                    }
                }
            }
        }
    }
}
