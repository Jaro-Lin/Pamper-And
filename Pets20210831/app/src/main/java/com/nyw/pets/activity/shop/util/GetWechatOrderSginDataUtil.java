package com.nyw.pets.activity.shop.util;

import com.google.gson.annotations.SerializedName;

public class GetWechatOrderSginDataUtil {

    /**
     * code : 1
     * message : 成功
     * data : {"data":{"appid":"wx85c00bd8719e895c","partnerid":"1514600891","prepayid":"wx2412193334458041859315fe1425694900","package":"Sign=WXPay","noncestr":"5aoQc","timestamp":1590293973,"sign":"5082C134930C34C5898F16B5F591D319"}}
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
         * data : {"appid":"wx85c00bd8719e895c","partnerid":"1514600891","prepayid":"wx2412193334458041859315fe1425694900","package":"Sign=WXPay","noncestr":"5aoQc","timestamp":1590293973,"sign":"5082C134930C34C5898F16B5F591D319"}
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
             * appid : wx85c00bd8719e895c
             * partnerid : 1514600891
             * prepayid : wx2412193334458041859315fe1425694900
             * package : Sign=WXPay
             * noncestr : 5aoQc
             * timestamp : 1590293973
             * sign : 5082C134930C34C5898F16B5F591D319
             */

            private String appid;
            private String partnerid;
            private String prepayid;
            @SerializedName("package")
            private String packageX;
            private String noncestr;
            private int timestamp;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
