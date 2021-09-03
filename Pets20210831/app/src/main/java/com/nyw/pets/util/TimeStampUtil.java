package com.nyw.pets.util;

/**
 * 获取时间戳
 */

public class TimeStampUtil {

    public String getTimeStamp() {
        long currentTimeMillis = System.currentTimeMillis()/1000;
        String StrTime = String.valueOf(currentTimeMillis);
        return StrTime;
    }

}
