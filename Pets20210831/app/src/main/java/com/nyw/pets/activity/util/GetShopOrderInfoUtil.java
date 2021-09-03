package com.nyw.pets.activity.util;

import com.nyw.pets.activity.shop.util.MyShopOrderInfoUtil;

import java.io.Serializable;
import java.util.List;

public class GetShopOrderInfoUtil implements Serializable {

    String orderId;
    String  totalPrice;
    String server;
    List<MyShopOrderInfoUtil> shopOrderList;
    String shopInfo;
    String is_pay;
    String is_send;
    String is_put;
    String is_comment;
    String is_cancel;
    String is_salelate;
    String shop_total;
    String id;
    String orderNumber;
    String logistics_id;
    String put_type;

    public String getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(String logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getPut_type() {
        return put_type;
    }

    public void setPut_type(String put_type) {
        this.put_type = put_type;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getIs_send() {
        return is_send;
    }

    public void setIs_send(String is_send) {
        this.is_send = is_send;
    }

    public String getIs_put() {
        return is_put;
    }

    public void setIs_put(String is_put) {
        this.is_put = is_put;
    }

    public String getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(String is_comment) {
        this.is_comment = is_comment;
    }

    public String getIs_cancel() {
        return is_cancel;
    }

    public void setIs_cancel(String is_cancel) {
        this.is_cancel = is_cancel;
    }

    public String getIs_salelate() {
        return is_salelate;
    }

    public void setIs_salelate(String is_salelate) {
        this.is_salelate = is_salelate;
    }

    public String getShop_total() {
        return shop_total;
    }

    public void setShop_total(String shop_total) {
        this.shop_total = shop_total;
    }

    public String getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(String shopInfo) {
        this.shopInfo = shopInfo;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }


    public List<MyShopOrderInfoUtil> getShopOrderList() {
        return shopOrderList;
    }

    public void setShopOrderList(List<MyShopOrderInfoUtil> shopOrderList) {
        this.shopOrderList = shopOrderList;
    }
}
