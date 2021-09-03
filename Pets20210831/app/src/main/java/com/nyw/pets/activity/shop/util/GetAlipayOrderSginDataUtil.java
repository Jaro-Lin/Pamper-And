package com.nyw.pets.activity.shop.util;

public class GetAlipayOrderSginDataUtil {

    /**
     * code : 1
     * message : 成功
     * data : {"data":"app_id=2021001155619621&method=alipay.trade.app.pay&format=JSON&sign_type=RSA2&timestamp=2020-05-24+12%3A16%3A15&charset=UTF-8&version=1.0&notify_url=http%3A%2F%2F47.115.46.39%2Fapi.php%2Fshop%2Fnotify%2Falipay&biz_content=%7B%22spbill_create_ip%22%3A%22113.16.163.56%22%2C%22out_trade_no%22%3A%2220200524121615365383%22%2C%22subject%22%3A%22%E4%B8%8B%E5%8D%95%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&sign=FSgaa75Ya79vEbApLRmH1i9wOFw94GXi4GZQnA5WG1Genxc8qbDBJn4GsaD2wxyxN2DUs5YvB7I8vxaeSu8cRMiPBDnwbRg38bRVqNMQjhQN7xY6aAYcPjpOaGf0H0GQX0CI4dPK%2BEhdJndeJx84c2evEEhwJgMslygVHR6suz1WyWQ47cDvOGSAFtxRAziNIf%2BTErTJd6BtN7n%2FokK54boqoIcLlU0P5i%2B0V3ArvSka05SzFJjtiF7BtB%2F%2By3aWD7KAR2RuoAxJvPDeWuj0PJjgtJb%2FJo9uS8QZ0jhh6K4z9N8EkQ6y2rBqoat6xyV8WlfZ7FO2Sho%2FW55%2B9mT8qQ%3D%3D"}
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
         * data : app_id=2021001155619621&method=alipay.trade.app.pay&format=JSON&sign_type=RSA2&timestamp=2020-05-24+12%3A16%3A15&charset=UTF-8&version=1.0&notify_url=http%3A%2F%2F47.115.46.39%2Fapi.php%2Fshop%2Fnotify%2Falipay&biz_content=%7B%22spbill_create_ip%22%3A%22113.16.163.56%22%2C%22out_trade_no%22%3A%2220200524121615365383%22%2C%22subject%22%3A%22%E4%B8%8B%E5%8D%95%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&sign=FSgaa75Ya79vEbApLRmH1i9wOFw94GXi4GZQnA5WG1Genxc8qbDBJn4GsaD2wxyxN2DUs5YvB7I8vxaeSu8cRMiPBDnwbRg38bRVqNMQjhQN7xY6aAYcPjpOaGf0H0GQX0CI4dPK%2BEhdJndeJx84c2evEEhwJgMslygVHR6suz1WyWQ47cDvOGSAFtxRAziNIf%2BTErTJd6BtN7n%2FokK54boqoIcLlU0P5i%2B0V3ArvSka05SzFJjtiF7BtB%2F%2By3aWD7KAR2RuoAxJvPDeWuj0PJjgtJb%2FJo9uS8QZ0jhh6K4z9N8EkQ6y2rBqoat6xyV8WlfZ7FO2Sho%2FW55%2B9mT8qQ%3D%3D
         */

        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
