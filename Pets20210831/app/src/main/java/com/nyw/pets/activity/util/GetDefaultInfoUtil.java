package com.nyw.pets.activity.util;

public class GetDefaultInfoUtil {


    /**
     * code : 1
     * message : 获取成功
     * data : {"id":63,"type_id":2,"varieties_id":18,"log_time":"2020-07-10 09:28:36","pid":"BDQuG0gl8NDxyK0J","nickname":"王者","server":"http://qiniu.lovelovepets.com/","avatar":"2020/07/542a0202007100928138253.jpg","is_init":0,"age":"1","weight":15,"healthy":"健康","real_sex":1,"sex":"雄性","if_default":0,"body_status":{"feed":{"feed":"1.200","num":5},"weight":null},"do_some":{"bath":0,"expelling":0,"vaccin_1":0,"vaccin_0":0}}
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
         * id : 63
         * type_id : 2
         * varieties_id : 18
         * log_time : 2020-07-10 09:28:36
         * pid : BDQuG0gl8NDxyK0J
         * nickname : 王者
         * server : http://qiniu.lovelovepets.com/
         * avatar : 2020/07/542a0202007100928138253.jpg
         * is_init : 0
         * age : 1
         * weight : 15
         * healthy : 健康
         * real_sex : 1
         * sex : 雄性
         * if_default : 0
         * body_status : {"feed":{"feed":"1.200","num":5},"weight":null}
         * do_some : {"bath":0,"expelling":0,"vaccin_1":0,"vaccin_0":0}
         */

        private int id;
        private int type_id;
        private int varieties_id;
        private String log_time;
        private String pid;
        private String nickname;
        private String server;
        private String avatar;
        private int is_init;
        private String age;
        private int weight;
        private String healthy;
        private int real_sex;
        private String sex;
        private int if_default;
        private BodyStatusBean body_status;
        private DoSomeBean do_some;

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

        public int getVarieties_id() {
            return varieties_id;
        }

        public void setVarieties_id(int varieties_id) {
            this.varieties_id = varieties_id;
        }

        public String getLog_time() {
            return log_time;
        }

        public void setLog_time(String log_time) {
            this.log_time = log_time;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getIs_init() {
            return is_init;
        }

        public void setIs_init(int is_init) {
            this.is_init = is_init;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getHealthy() {
            return healthy;
        }

        public void setHealthy(String healthy) {
            this.healthy = healthy;
        }

        public int getReal_sex() {
            return real_sex;
        }

        public void setReal_sex(int real_sex) {
            this.real_sex = real_sex;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getIf_default() {
            return if_default;
        }

        public void setIf_default(int if_default) {
            this.if_default = if_default;
        }

        public BodyStatusBean getBody_status() {
            return body_status;
        }

        public void setBody_status(BodyStatusBean body_status) {
            this.body_status = body_status;
        }

        public DoSomeBean getDo_some() {
            return do_some;
        }

        public void setDo_some(DoSomeBean do_some) {
            this.do_some = do_some;
        }

        public static class BodyStatusBean {
            /**
             * feed : {"feed":"1.200","num":5}
             * weight : null
             */

            private FeedBean feed;
            private Object weight;

            public FeedBean getFeed() {
                return feed;
            }

            public void setFeed(FeedBean feed) {
                this.feed = feed;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }

            public static class FeedBean {
                /**
                 * feed : 1.200
                 * num : 5
                 */

                private String feed;
                private int num;

                public String getFeed() {
                    return feed;
                }

                public void setFeed(String feed) {
                    this.feed = feed;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }
            }
        }

        public static class DoSomeBean {
            /**
             * bath : 0
             * expelling : 0
             * vaccin_1 : 0
             * vaccin_0 : 0
             */

            private int bath;
            private int expelling;
            private int vaccin_1;
            private int vaccin_0;

            public int getBath() {
                return bath;
            }

            public void setBath(int bath) {
                this.bath = bath;
            }

            public int getExpelling() {
                return expelling;
            }

            public void setExpelling(int expelling) {
                this.expelling = expelling;
            }

            public int getVaccin_1() {
                return vaccin_1;
            }

            public void setVaccin_1(int vaccin_1) {
                this.vaccin_1 = vaccin_1;
            }

            public int getVaccin_0() {
                return vaccin_0;
            }

            public void setVaccin_0(int vaccin_0) {
                this.vaccin_0 = vaccin_0;
            }
        }
    }
}
