package com.nyw.pets.activity.util;

public class GetBasePetsDataUtil {


    /**
     * code : 1
     * message : 成功
     * data : {"data":{"id":53,"type_id":2,"varieties_name":"标准型雪纳瑞","weight":14,"meal_add":"加餐","feed":"喂养","weight_1_max":"16.00","weight_1_min":"14.00","weight_0_max":"16.00","weight_0_min":"14.00","shower":"洗澡","vaccin":"疫苗信息","expelling":"驱虫"}}
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
         * data : {"id":53,"type_id":2,"varieties_name":"标准型雪纳瑞","weight":14,"meal_add":"加餐","feed":"喂养","weight_1_max":"16.00","weight_1_min":"14.00","weight_0_max":"16.00","weight_0_min":"14.00","shower":"洗澡","vaccin":"疫苗信息","expelling":"驱虫"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 53
             * type_id : 2
             * varieties_name : 标准型雪纳瑞
             * weight : 14
             * meal_add : 加餐
             * feed : 喂养
             * weight_1_max : 16.00
             * weight_1_min : 14.00
             * weight_0_max : 16.00
             * weight_0_min : 14.00
             * shower : 洗澡
             * vaccin : 疫苗信息
             * expelling : 驱虫
             */

            private int id;
            private int type_id;
            private String varieties_name;
            private int weight;
            private String meal_add;
            private String feed;
            private String weight_1_max;
            private String weight_1_min;
            private String weight_0_max;
            private String weight_0_min;
            private String shower;
            private String vaccin;
            private String expelling;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType_id() {
                return type_id;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public String getVarieties_name() {
                return varieties_name;
            }

            public void setVarieties_name(String varieties_name) {
                this.varieties_name = varieties_name;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public String getMeal_add() {
                return meal_add;
            }

            public void setMeal_add(String meal_add) {
                this.meal_add = meal_add;
            }

            public String getFeed() {
                return feed;
            }

            public void setFeed(String feed) {
                this.feed = feed;
            }

            public String getWeight_1_max() {
                return weight_1_max;
            }

            public void setWeight_1_max(String weight_1_max) {
                this.weight_1_max = weight_1_max;
            }

            public String getWeight_1_min() {
                return weight_1_min;
            }

            public void setWeight_1_min(String weight_1_min) {
                this.weight_1_min = weight_1_min;
            }

            public String getWeight_0_max() {
                return weight_0_max;
            }

            public void setWeight_0_max(String weight_0_max) {
                this.weight_0_max = weight_0_max;
            }

            public String getWeight_0_min() {
                return weight_0_min;
            }

            public void setWeight_0_min(String weight_0_min) {
                this.weight_0_min = weight_0_min;
            }

            public String getShower() {
                return shower;
            }

            public void setShower(String shower) {
                this.shower = shower;
            }

            public String getVaccin() {
                return vaccin;
            }

            public void setVaccin(String vaccin) {
                this.vaccin = vaccin;
            }

            public String getExpelling() {
                return expelling;
            }

            public void setExpelling(String expelling) {
                this.expelling = expelling;
            }
        }
    }
}
