package com.jnshu.util;

import java.util.Random;

public class RandNum {
    /**
     * 随机生成验证码
     */
    public  static String getNumLength(int intLength){
        //数字加字母
        final int maxNum = 36;
        int i ; //随机数
        int count = 0;
        char[] str ={'0','1','2','3','4','5','6','7','8','9'};
        StringBuffer pwd = new StringBuffer("");
        Random random = new Random();
        while (count<intLength){
            //生成随机数，防止负数，取绝对值
            i = Math.abs(random.nextInt(maxNum));
            //0-9取值
            if (i>=0 && i<str.length){
                //长度根据需求生成
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

}
