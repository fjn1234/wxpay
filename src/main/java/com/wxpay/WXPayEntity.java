package com.wxpay;

public class WXPayEntity {

    private static WXPayEntity entity;

    public static void clearEntity() {
        entity = null;
    }

    public static WXPayEntity getEntity() {
        return entity;
    }

    public static void setEntity(WXPayEntity entity) {
        WXPayEntity.entity = entity;
    }

    private boolean paySuccess = false;

    public void setPaySuccess(boolean paySuccess) {
        this.paySuccess = paySuccess;
    }

    public boolean isPaySuccess() {
        return paySuccess;
    }

    //----------------------------------------
    public String appId = "";
    public String partnerId = "";
    public String prepayId = "";
    public String nonceStr = "";
    public String timeStamp = "";
    public String sign = "";
    public String packageName = "";
    public String appTyle = "";

    public String getAppTyle() {
        return appTyle;
    }

    public void setAppTyle(String appTyle) {
        this.appTyle = appTyle;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
