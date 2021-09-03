package com.nyw.pets.config;

import java.util.Arrays;

/**
 * 首字母排序获取签名需要的key值
 */

public class InitialSort {

    public String getKey(String[][] str) {
        //String[][] str = { { "abc", "111" }, { "bca", "232" },{ "cab", "112" }, { "cba", "482" }, { "aaa", "118" },{ "ABC", "718" } };
        String[] strSort = new String[str.length];
        for (int i=0; i<str.length; i++) {
            strSort[i] = str[i][0];
        }
        Arrays.sort(strSort);
        String key = "";
        for (int i = 0; i < strSort.length; i++) {
            for (int j=0; j<str.length; j++) {
                if (strSort[i] == str[j][0]) {
                    key = key + strSort[i] + str[j][1];
                }
            }
        }
        return key;
    }

}
