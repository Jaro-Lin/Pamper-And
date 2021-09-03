package com.nyw.pets.activity.shop.util;

import java.io.Serializable;
import java.util.List;

public class GetOrderDataDetailsUtil implements Serializable {


    /**
     * code : 1
     * message : 获取成功
     * data : {"id":206,"order_id":"20200624183022455715","is_pay":1,"pay_time":"1592994631","is_send":1,"is_put":0,"is_comment":0,"is_cancel":0,"is_salelate":0,"logistics_id":"55566666566","pay":"211.90","create_time":"1592994622","put_type":"顺丰快递","shop_list":[{"id":212,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}},{"id":213,"shop_id":28,"number":1,"price":"169.00","shop":{"id":28,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/7a4dcc6b1c914cc31fefd62b9e9c1046.jpg"},"spe":{"id":39,"spe":"成犬15KG","price":169}}],"address":{"province":"北京市","city":"北京市","area":"东城区","detailed":"科园大道9号","username":"宁一旺","phone":"18977092827"}}
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

    public static class DataBean implements Serializable{
        /**
         * id : 206
         * order_id : 20200624183022455715
         * is_pay : 1
         * pay_time : 1592994631
         * is_send : 1
         * is_put : 0
         * is_comment : 0
         * is_cancel : 0
         * is_salelate : 0
         * logistics_id : 55566666566
         * pay : 211.90
         * create_time : 1592994622
         * put_type : 顺丰快递
         * shop_list : [{"id":212,"shop_id":29,"number":1,"price":"42.90","shop":{"id":29,"pets_id":2,"type_id":10,"title":"宝路 宠物狗粮 成犬全价粮 中小型犬泰迪茶杯犬柯基 ","icon":"2020/06/0872b208616730f1f818dcc8d8f79fc3.jpg"},"spe":{"id":41,"spe":"牛肉味1.8KG","price":42.9}},{"id":213,"shop_id":28,"number":1,"price":"169.00","shop":{"id":28,"pets_id":2,"type_id":10,"title":" 麦富迪 宠物狗粮 藻趣儿通用成犬粮15kg","icon":"2020/06/7a4dcc6b1c914cc31fefd62b9e9c1046.jpg"},"spe":{"id":39,"spe":"成犬15KG","price":169}}]
         * address : {"province":"北京市","city":"北京市","area":"东城区","detailed":"科园大道9号","username":"宁一旺","phone":"18977092827"}
         */

        private int id;
        private String order_id;
        private int is_pay;
        private String pay_time;
        private int is_send;
        private int is_put;
        private int is_comment;
        private int is_cancel;
        private int is_salelate;
        private String logistics_id;
        private String pay;
        private String create_time;
        private String put_type;
        private AddressBean address;
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

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
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

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPut_type() {
            return put_type;
        }

        public void setPut_type(String put_type) {
            this.put_type = put_type;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public List<ShopListBean> getShop_list() {
            return shop_list;
        }

        public void setShop_list(List<ShopListBean> shop_list) {
            this.shop_list = shop_list;
        }

        public static class AddressBean {
            /**
             * province : 北京市
             * city : 北京市
             * area : 东城区
             * detailed : 科园大道9号
             * username : 宁一旺
             * phone : 18977092827
             */

            private String province;
            private String city;
            private String area;
            private String detailed;
            private String username;
            private String phone;

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

            public String getDetailed() {
                return detailed;
            }

            public void setDetailed(String detailed) {
                this.detailed = detailed;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }

        public static class ShopListBean implements Serializable {
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
