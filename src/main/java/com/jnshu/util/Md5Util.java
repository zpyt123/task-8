package com.jnshu.util;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    /*
    *,Md5加密
     */
    public final static String stringToMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = s.getBytes();
        MessageDigest mdInst = null;
        try {
            //MD5获取 MessageDigest对象
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //使用指定的字节更新摘要
        mdInst.update(btInput);
        //获取密文
        byte[] bt = mdInst.digest();
        //将秘闻转换成16进制的字符串类型
        int j = bt.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = bt[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
    public final static String getMultiparFileMd5(MultipartFile multipartFile){
        try {
            byte [] uploadBytes = multipartFile.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] disgest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1,disgest).toString();
            }catch (Exception e){
            System.out.println("MD5获取失败");
        }
        return  null;

    }
}
