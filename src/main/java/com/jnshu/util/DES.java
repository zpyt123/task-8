package com.jnshu.util;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class DES{
    /*
    *加密
     */
    public DES() {
    }

    public static byte[] encrypt(byte[] data, String sKey) {
        try{
            byte[] key = sKey.getBytes();
            //初始向量
            IvParameterSpec iv = new IvParameterSpec(key);
            DESKeySpec desKey = new DESKeySpec(key);
            //创建一个密匙工厂。将DESKeySpec转换成secrekey
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //将key转给secretKey
            SecretKey secretKey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            //用密匙初始化Cipher
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            //现在获取数据并加密
            //正式操作加密
            return cipher.doFinal(data);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    *解密
     */
    public static byte[] decrypt(byte[] src, String sKey) throws Exception {
        byte[] key = sKey.getBytes();
        //初始向量
        IvParameterSpec iv = new IvParameterSpec(key);
        //创建一个DESKeySpect对象
        DESKeySpec deskey = new DESKeySpec(key);
        //创建密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        //将DESKeySpec对象转换成SecretKey对象
        SecretKey secretKey = keyFactory.generateSecret(deskey);
        //Cipher对象完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        //用密匙初始化对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        //真正开始解密
        return cipher.doFinal(src);
    }
    /*
    *将二进制转换成16进制
     */
    public static String parseHexStr2Byte(byte buf[]){
        StringBuffer sf =new StringBuffer();
        for(int i = 0;i < buf.length;i++){
            String hes = Integer.toHexString(buf[i] & 0xff);
            if (hes.length() ==1){
                hes = '0' + hes;
            }
            sf.append(hes.toUpperCase());
        }
        return sf.toString();
    }

    /*
     * 将16进制转换为二进制
     */

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}