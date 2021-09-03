package com.nyw.pets.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.nyw.pets.net.util.MD5Encoder;


/**
 * 获取签名
 */

public class SignConfig {
    private String secret = "8d0fadda544c07d0e959d11a62f7acaa";
    /*
    没有用户ID的签名
     */
    public String getSign(Context context, String key) {
        // 私钥
        String strSign = secret + key;
        // MD5加密
        MD5Encoder md5Encoder = new MD5Encoder();
        String sign = md5Encoder.encode(strSign);
        return sign.toUpperCase();
    }
    /*
    有用户ID的签名
     */
    public String getUidSign(Context context, String key) {
        // 获取账号ID，用户token
        SharedPreferences sp = context.getSharedPreferences(SaveAPPData.USER_ID_Token,Context.MODE_PRIVATE);
        String token = sp.getString(SaveAPPData.TOKEN, null);
        // 私钥
        String strSign = secret + key + token;
        // MD5加密
        MD5Encoder md5Encoder = new MD5Encoder();
        String sign = md5Encoder.encode(strSign);
        return sign.toUpperCase();
    }
}
