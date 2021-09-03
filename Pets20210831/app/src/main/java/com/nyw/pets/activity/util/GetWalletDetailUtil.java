package com.nyw.pets.activity.util;

import java.util.List;

public class GetWalletDetailUtil {

    /**
     * msg : 获取成功
     * code : 0
     * data : {"total":6,"per_page":15,"current_page":1,"last_page":1,"data":[{"id":102,"status_id":1,"in_out":1,"uid":3,"type":"cashout","paytype":"wallet","order_id":"201910191452216727683045","money":200,"create_time":1571365921,"title":"余额提现","explain":null,"more":"{\"form_uid\":\"3\",\"money\":1,\"target_uid\":0,\"more\":{\"no\":\"13457707229\"}}"},{"id":101,"status_id":1,"in_out":1,"uid":3,"type":"cashout","paytype":"wallet","order_id":"201910191452216727683023","money":1000000,"create_time":1572325574,"title":"余额提现","explain":"","more":"{\"form_uid\":\"3\",\"money\":1,\"target_uid\":0,\"more\":{\"no\":\"13457707229\"}}"},{"id":80,"status_id":1,"in_out":1,"uid":3,"type":"buy","paytype":"wallet","order_id":"201910182351359195907512","money":200,"create_time":1571413895,"title":"项目购买","explain":"","more":"[{\"id\":11,\"name\":\"\\u53d1\\u5341\\u5927\\u53d1\\u5c04\\u70b9\",\"price\":20000,\"num\":1,\"body\":{\"uid\":\"3\",\"pro_id\":\"11\"},\"show_url\":\"\"}]"},{"id":78,"status_id":1,"in_out":1,"uid":3,"type":"buy","paytype":"wallet","order_id":"201910182342366900097327","money":2147483647,"create_time":1571413356,"title":"项目购买","explain":"","more":"[{\"id\":11,\"name\":\"\\u53d1\\u5341\\u5927\\u53d1\\u5c04\\u70b9\",\"price\":20000,\"num\":1,\"body\":{\"uid\":\"3\",\"pro_id\":\"11\"},\"show_url\":\"\"}]"},{"id":76,"status_id":1,"in_out":1,"uid":3,"type":"buy","paytype":"wallet","order_id":"201910182342366900097354","money":2147483647,"create_time":1571413356,"title":"项目购买","explain":"","more":"[{\"id\":11,\"name\":\"\\u53d1\\u5341\\u5927\\u53d1\\u5c04\\u70b9\",\"price\":20000,\"num\":1,\"body\":{\"uid\":\"3\",\"pro_id\":\"11\"},\"show_url\":\"\"}]"},{"id":74,"status_id":1,"in_out":1,"uid":3,"type":"buy","paytype":"wallet","order_id":"201910182342366900097324","money":2147483647,"create_time":1571413356,"title":"项目购买","explain":"","more":"[{\"id\":11,\"name\":\"\\u53d1\\u5341\\u5927\\u53d1\\u5c04\\u70b9\",\"price\":20000,\"num\":1,\"body\":{\"uid\":\"3\",\"pro_id\":\"11\"},\"show_url\":\"\"}]"}]}
     */

    private String msg;
    private int code;
    private DataBeanX data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 6
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"id":102,"status_id":1,"in_out":1,"uid":3,"type":"cashout","paytype":"wallet","order_id":"201910191452216727683045","money":200,"create_time":1571365921,"title":"余额提现","explain":null,"more":"{\"form_uid\":\"3\",\"money\":1,\"target_uid\":0,\"more\":{\"no\":\"13457707229\"}}"},{"id":101,"status_id":1,"in_out":1,"uid":3,"type":"cashout","paytype":"wallet","order_id":"201910191452216727683023","money":1000000,"create_time":1572325574,"title":"余额提现","explain":"","more":"{\"form_uid\":\"3\",\"money\":1,\"target_uid\":0,\"more\":{\"no\":\"13457707229\"}}"},{"id":80,"status_id":1,"in_out":1,"uid":3,"type":"buy","paytype":"wallet","order_id":"201910182351359195907512","money":200,"create_time":1571413895,"title":"项目购买","explain":"","more":"[{\"id\":11,\"name\":\"\\u53d1\\u5341\\u5927\\u53d1\\u5c04\\u70b9\",\"price\":20000,\"num\":1,\"body\":{\"uid\":\"3\",\"pro_id\":\"11\"},\"show_url\":\"\"}]"},{"id":78,"status_id":1,"in_out":1,"uid":3,"type":"buy","paytype":"wallet","order_id":"201910182342366900097327","money":2147483647,"create_time":1571413356,"title":"项目购买","explain":"","more":"[{\"id\":11,\"name\":\"\\u53d1\\u5341\\u5927\\u53d1\\u5c04\\u70b9\",\"price\":20000,\"num\":1,\"body\":{\"uid\":\"3\",\"pro_id\":\"11\"},\"show_url\":\"\"}]"},{"id":76,"status_id":1,"in_out":1,"uid":3,"type":"buy","paytype":"wallet","order_id":"201910182342366900097354","money":2147483647,"create_time":1571413356,"title":"项目购买","explain":"","more":"[{\"id\":11,\"name\":\"\\u53d1\\u5341\\u5927\\u53d1\\u5c04\\u70b9\",\"price\":20000,\"num\":1,\"body\":{\"uid\":\"3\",\"pro_id\":\"11\"},\"show_url\":\"\"}]"},{"id":74,"status_id":1,"in_out":1,"uid":3,"type":"buy","paytype":"wallet","order_id":"201910182342366900097324","money":2147483647,"create_time":1571413356,"title":"项目购买","explain":"","more":"[{\"id\":11,\"name\":\"\\u53d1\\u5341\\u5927\\u53d1\\u5c04\\u70b9\",\"price\":20000,\"num\":1,\"body\":{\"uid\":\"3\",\"pro_id\":\"11\"},\"show_url\":\"\"}]"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 102
             * status_id : 1
             * in_out : 1
             * uid : 3
             * type : cashout
             * paytype : wallet
             * order_id : 201910191452216727683045
             * money : 200
             * create_time : 1571365921
             * title : 余额提现
             * explain : null
             * more : {"form_uid":"3","money":1,"target_uid":0,"more":{"no":"13457707229"}}
             */

            private int id;
            private int status_id;
            private int in_out;
            private int uid;
            private String type;
            private String paytype;
            private String order_id;
            private int money;
            private int create_time;
            private String title;
            private Object explain;
            private String more;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStatus_id() {
                return status_id;
            }

            public void setStatus_id(int status_id) {
                this.status_id = status_id;
            }

            public int getIn_out() {
                return in_out;
            }

            public void setIn_out(int in_out) {
                this.in_out = in_out;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPaytype() {
                return paytype;
            }

            public void setPaytype(String paytype) {
                this.paytype = paytype;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getExplain() {
                return explain;
            }

            public void setExplain(Object explain) {
                this.explain = explain;
            }

            public String getMore() {
                return more;
            }

            public void setMore(String more) {
                this.more = more;
            }
        }
    }
}
