package com.nyw.pets.util;

public class GetPaySignUtil {


    /**
     * msg : 获取成功
     * code : 0
     * data : {"payid":"201911141519364408561820","randomstr":"e06b611606fb55113447a0f77e75dc27","paytime":1573715976,"paysign":"D4829846F3F41CFAF10C9A6E7672FDE7"}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * payid : 201911141519364408561820
         * randomstr : e06b611606fb55113447a0f77e75dc27
         * paytime : 1573715976
         * paysign : D4829846F3F41CFAF10C9A6E7672FDE7
         */

        private String payid;
        private String randomstr;
        private int paytime;
        private String paysign;

        public String getPayid() {
            return payid;
        }

        public void setPayid(String payid) {
            this.payid = payid;
        }

        public String getRandomstr() {
            return randomstr;
        }

        public void setRandomstr(String randomstr) {
            this.randomstr = randomstr;
        }

        public int getPaytime() {
            return paytime;
        }

        public void setPaytime(int paytime) {
            this.paytime = paytime;
        }

        public String getPaysign() {
            return paysign;
        }

        public void setPaysign(String paysign) {
            this.paysign = paysign;
        }
    }
}
