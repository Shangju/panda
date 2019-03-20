package com.graduation.panda.model;

import java.util.Date;

public class UserAddress {
    private long addressId;
    private String userId;
    private String userName;
    private String userPhone;
    private String cityName;
    private String areaName;
    private String userAddress;
    private boolean defaultStatus;
    private boolean dataFlag;
    private Date createTime;

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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
    public boolean isDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(boolean defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public boolean isDataFlag() {
        return dataFlag;
    }

    public void setDataFlag(boolean dataFlag) {
        this.dataFlag = dataFlag;
    }
}
