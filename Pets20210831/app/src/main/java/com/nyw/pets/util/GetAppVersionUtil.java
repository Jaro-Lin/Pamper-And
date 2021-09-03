package com.nyw.pets.util;

public class GetAppVersionUtil {


    /**
     * code : 1
     * message : 成功
     * data : {"data":{"id":3,"version":"v1.0.1","version_name":"版本名称","title":"超好吃的零","desc":"阿萨大师","url":"超好吃的零","create_time":0,"force":0}}
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
         * data : {"id":3,"version":"v1.0.1","version_name":"版本名称","title":"超好吃的零","desc":"阿萨大师","url":"超好吃的零","create_time":0,"force":0}
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
             * id : 3
             * version : v1.0.1
             * version_name : 版本名称
             * title : 超好吃的零
             * desc : 阿萨大师
             * url : 超好吃的零
             * create_time : 0
             * force : 0
             */

            private int id;
            private String version;
            private String version_name;
            private String title;
            private String desc;
            private String url;
            private int create_time;
            private int force;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getVersion_name() {
                return version_name;
            }

            public void setVersion_name(String version_name) {
                this.version_name = version_name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getForce() {
                return force;
            }

            public void setForce(int force) {
                this.force = force;
            }
        }
    }
}
