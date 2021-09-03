package com.nyw.pets.activity.shop.util;

import java.util.List;

public class DelShopCartInfoData {
    String token;
    List <DelShopCartData> shop_spe;
   String [] id;

    public String[] getId() {
        return id;
    }

    public void setId(String[] id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DelShopCartData> getShop_spe() {
        return shop_spe;
    }

    public void setShop_spe(List<DelShopCartData> shop_spe) {
        this.shop_spe = shop_spe;
    }
}
