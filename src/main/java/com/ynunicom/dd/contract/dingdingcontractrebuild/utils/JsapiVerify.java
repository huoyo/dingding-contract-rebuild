package com.ynunicom.dd.contract.dingdingcontractrebuild.utils;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.util.Formatter;

/**
 * @author: jinye.Bai
 * @date: 2020/6/23 8:55
 */
public class JsapiVerify {

    @SneakyThrows
    public static String sign(String ticket, String nonceStr, long timeStamp, String url) {
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
                + "&url=" + url;
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.reset();
        sha1.update(plain.getBytes("UTF-8"));
        return byteToHex(sha1.digest());
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
