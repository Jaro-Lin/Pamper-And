package com.nyw.pets.config;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Base64加密
 */
public class MyBase64 {
    private  String encodeWord=null;


   public MyBase64(){

    }

    /**
     *Base64加密
     * @param data
     * @return
     */
    public String getBase64Data(String data){

        try {
             encodeWord = Base64.encodeToString(data.getBytes("utf-8"), Base64.NO_WRAP);
            Log.i("Tag", " encode wrods = " + encodeWord);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeWord;
    }


}
