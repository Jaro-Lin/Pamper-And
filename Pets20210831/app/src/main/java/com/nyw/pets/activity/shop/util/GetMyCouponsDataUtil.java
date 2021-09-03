package com.nyw.pets.activity.shop.util;

import java.util.List;

public class GetMyCouponsDataUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : {"total":1,"list":[{"id":1,"title":"满30元可用","content":"五一回馈用户优惠！","price":"3.00","has_price":"30.00","start_time":"1588905266","end_time":"1589905266"}]}
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
         * list : [{"id":1,"title":"满30元可用","content":"五一回馈用户优惠！","price":"3.00","has_price":"30.00","start_time":"1588905266","end_time":"1589905266"}]
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
             * title : 满30元可用
             * content : 五一回馈用户优惠！
             * price : 3.00
             * has_price : 30.00
             * start_time : 1588905266
             * end_time : 1589905266
             */

            private int id;
            private String title;
            private String content;
            private String price;
            private String has_price;
            private String start_time;
            private String end_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
        }
    }
}
