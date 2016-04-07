package com.wxpay;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.security.MessageDigest;
import java.util.HashMap;

public class WXPayHelper {

    private static IWXAPI iwxapi = null;
    private static WXPayEntity lastPayEntity = null;
    public static final String WXPACKAGENAME = "com.tencent.mm";

    public static boolean init(Context context) {
        try {
            iwxapi = WXAPIFactory.createWXAPI(context, null);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean pay(WXPayEntity wxPayEntity) {
        if (iwxapi == null) throw new RuntimeException("wx api isn't init");
        if (!iwxapi.registerApp(wxPayEntity.getAppId())) return false;
        PayReq request = new PayReq();
        request.appId = wxPayEntity.getAppId();
        request.partnerId = wxPayEntity.getPartnerId();
        request.prepayId = wxPayEntity.getPrepayId();
        request.packageValue = wxPayEntity.getPackageName();
        request.nonceStr = wxPayEntity.getNonceStr();
        request.timeStamp = wxPayEntity.getTimeStamp();
        request.sign = wxPayEntity.getSign();
        lastPayEntity = wxPayEntity;
        return iwxapi.sendReq(request);
    }

    public static void clearLastPayEntity() {
        lastPayEntity = null;
    }

    public static IWXAPI getIWXAPI() {
        return iwxapi;
    }

    public static WXPayEntity getLastPayEntity() {
        return lastPayEntity;
    }

    public static boolean isWXInstalled(Context context) {
//        if (iwxapi == null) throw new CException("wx api isn't init");
//        return iwxapi.openWXApp();
        return isApkInstalled(context,WXPACKAGENAME);
    }

    public static String genAppSign(WXPayEntity wxPayEntity) {
        HashMap<String, String> signParams = new HashMap<>();
        signParams.put("appid", wxPayEntity.getAppId());
        signParams.put("noncestr", wxPayEntity.getNonceStr());
        signParams.put("package", wxPayEntity.getPackageName());
        signParams.put("partnerid", wxPayEntity.getPartnerId());
        signParams.put("prepayid", wxPayEntity.getPrepayId());
        signParams.put("timestamp", wxPayEntity.getTimeStamp());
        StringBuilder sb = new StringBuilder();
        for (String k : signParams.keySet()) {
            sb.append(k);
            sb.append('=');
            sb.append(signParams.get(k));
            sb.append('&');
        }
        sb.append("key=");
        sb.append("S9QQDmk2lOg89LRXIAIv78Sagawoj4C2");
        String appSign =  getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    private  static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private static boolean isApkInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
