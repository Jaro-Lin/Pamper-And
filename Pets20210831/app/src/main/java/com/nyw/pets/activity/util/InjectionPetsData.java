package com.nyw.pets.activity.util;

import com.google.gson.annotations.SerializedName;

public class InjectionPetsData {
        /**
         * 0 : {"vaccin_time":"2020-06-08","vaccin_count":"1","vaccin_type":"0"}
         * 1 : {"vaccin_time":"2020-06-08","vaccin_count":"1","vaccin_type":"1"}
         */

        @SerializedName("0")
        private InjectionData one;
        @SerializedName("1")
        private InjectionData two;

    public InjectionData getOne() {
        return one;
    }

    public void setOne(InjectionData one) {
        this.one = one;
    }

    public InjectionData getTwo() {
        return two;
    }

    public void setTwo(InjectionData two) {
        this.two = two;
    }
}
