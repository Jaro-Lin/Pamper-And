package com.nyw.pets.activity.shop.util;

public class OrderShopData {

    /**
     * id : 2
     * orderState : 1
     * price : 45.00
     * server : http://qiniu.lovelovepets.com/
     * shopImg : 2020/04/eab42202004030926519580.png
     * shopNumber : 1
     * specifications : 1000g/包
     * title : 小鱼干
     */

    private String id;
    private String orderState;
    private String price;
    private String server;
    private String shopImg;
    private String shopNumber;
    private String specifications;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(String shopNumber) {
        this.shopNumber = shopNumber;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
