package com.jnshu.util;

import java.util.Random;

public class CharacterUtils {
    //方法1：length 为产生的长度
    public  static String getRandomString(int length){
        //定义一个字符串（A-Z, a-z，0-9）即62位
        String str = "zxcvbnmlkjhgfdsaqwertyuiop1234567890";//QWERTYUIOPLKJHGFDSAZXCVBNM
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sf =new StringBuffer();
        //长度为几循环几次
        for (int i=0;i < length ;++i){
        //产生0-61位数
        int number =random.nextInt(62);
        //产生的数字通过length次承载到sf中
        sf.append(str.charAt(number));
    }
    //将承载的字符转换化成字符串
    return sf.toString();

}
/*
*第二种方法
 */
public static String getRandomString2(int length){
//产生随机数
    Random random = new Random();
    StringBuffer sf = new StringBuffer();
    //循环次数
    for (int i=0 ;i < length ;i++){
        //产生0-2个随机数，即a-z,A-Z,0-9三种可能
        int number =random.nextInt(3);
        long result = 0;
        switch (number){
            //如果number产生的数字是0
            case 0:
                //产生A-ZASCII编码
                result =Math.round(Math.random()*25+97);//Math.radom()*25+97 产生ASCII编码
                sf.append(String.valueOf((char)result));
                break;
            case 1:
                //产生a-z编码
                result = Math.round(Math.random()*25+97);
                sf.append(String.valueOf((char)result));
                break;
            case 2:
                //产生0-9编码

                sf.append(String.valueOf(new Random().nextInt(10)));
                break;
        }

    }
    return sf.toString();
}
public  static void  main(String[] args){
    System.out.println(CharacterUtils.getRandomString2(8));
}
}