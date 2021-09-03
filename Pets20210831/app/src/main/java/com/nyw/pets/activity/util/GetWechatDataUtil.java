package com.nyw.pets.activity.util;

public class GetWechatDataUtil {

    /**
     * code : 1
     * message : 获取成功！
     * data : {"serve_wechat":"wechat_num"}
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
         * serve_wechat : wechat_num
         */

        private String serve_wechat;

        public String getServe_wechat() {
            return serve_wechat;
        }

        public void setServe_wechat(String serve_wechat) {
            this.serve_wechat = serve_wechat;
        }
    }
}
