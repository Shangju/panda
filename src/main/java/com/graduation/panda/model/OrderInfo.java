package com.graduation.panda.model;

import java.util.Date;

public class OrderInfo {
    private String OrderId;
    private String UserId;
    private String orderType;
    private double totalPrice;
    private boolean pay;
    private boolean receipt;
    private String shipTime;
    private Date createTime;
    private boolean status;
    private String userAddress;
    private boolean recieve;

    public boolean isRecieve() {
        return recieve;
    }

    public void setRecieve(boolean recieve) {
        this.recieve = recieve;
    }



    public String getUserAddress() {
        return userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public boolean isReceipt() {
        return receipt;
    }

    public void setReceipt(boolean receipt) {
        this.receipt = receipt;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
