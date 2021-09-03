package com.nyw.pets.activity.util;

public class SendPetsInitData {
    String token;
    String mypets_id;
    String last_bath;
    String vaccin_type;
    String expelling_inter;
    String last_expelling;
    String bath_inter;
    InjectionPetsData vaccin;
    //三联/六联疫苗打了几次
    String vaccin_0_count;
    //狂犬疫苗打了几次
    String vaccin_1_count;
    //上一次打三联/六联疫时间
    String last_vaccin_0;
    //上一次打狂犬疫苗时间
    String last_vaccin_1;
    //最近一次打疫苗时间
    String Vaccin_time;

    public String getVaccin_time() {
        return Vaccin_time;
    }

    public void setVaccin_time(String vaccin_time) {
        Vaccin_time = vaccin_time;
    }

    public String getVaccin_0_count() {
        return vaccin_0_count;
    }

    public void setVaccin_0_count(String vaccin_0_count) {
        this.vaccin_0_count = vaccin_0_count;
    }

    public String getVaccin_1_count() {
        return vaccin_1_count;
    }

    public void setVaccin_1_count(String vaccin_1_count) {
        this.vaccin_1_count = vaccin_1_count;
    }

    public String getLast_vaccin_0() {
        return last_vaccin_0;
    }

    public void setLast_vaccin_0(String last_vaccin_0) {
        this.last_vaccin_0 = last_vaccin_0;
    }

    public String getLast_vaccin_1() {
        return last_vaccin_1;
    }

    public void setLast_vaccin_1(String last_vaccin_1) {
        this.last_vaccin_1 = last_vaccin_1;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMypets_id() {
        return mypets_id;
    }

    public void setMypets_id(String mypets_id) {
        this.mypets_id = mypets_id;
    }


    public String getVaccin_type() {
        return vaccin_type;
    }

    public void setVaccin_type(String vaccin_type) {
        this.vaccin_type = vaccin_type;
    }

    public String getExpelling_inter() {
        return expelling_inter;
    }

    public void setExpelling_inter(String expelling_inter) {
        this.expelling_inter = expelling_inter;
    }

    public String getBath_inter() {
        return bath_inter;
    }

    public void setBath_inter(String bath_inter) {
        this.bath_inter = bath_inter;
    }


    public String getLast_bath() {
        return last_bath;
    }

    public void setLast_bath(String last_bath) {
        this.last_bath = last_bath;
    }

    public String getLast_expelling() {
        return last_expelling;
    }

    public void setLast_expelling(String last_expelling) {
        this.last_expelling = last_expelling;
    }


    public InjectionPetsData getVaccin() {
        return vaccin;
    }

    public void setVaccin(InjectionPetsData vaccin) {
        this.vaccin = vaccin;
    }
}
