package com.nyw.pets.activity.shop.util;

import java.util.List;

public class GetUserAdressListDataUtil {


    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":1,"list":[{"id":20,"phone":"18977092827","username":"宁一旺","province":"北京市","city":"北京市","area":"东城区","detailed":"1111","is_default":1}]}
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
         * list : [{"id":20,"phone":"18977092827","username":"宁一旺","province":"北京市","city":"北京市","area":"东城区","detailed":"1111","is_default":1}]
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
             * id : 20
             * phone : 18977092827
             * username : 宁一旺
             * province : 北京市
             * city : 北京市
             * area : 东城区
             * detailed : 1111
             * is_default : 1
             */

            private int id;
            private String phone;
            private String username;
            private String province;
            private String city;
            private String area;
            private String detailed;
            private int is_default;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
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

            public String getDetailed() {
                return detailed;
            }

            public void setDetailed(String detailed) {
                this.detailed = detailed;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }
        }
    }
}
