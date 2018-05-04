package com.kingguanzhang.sorm.utils;
/*
* Common StringUtils
* */
public class StringUtils {
    /*
    *Be Used for convert the first char to capital in word,for example: name --> Name;
    *@param str , Target str;
    * @return ,str;
    */

    public static String firstChar2UpperCase(String str){
        return str.toUpperCase().substring(0,1)+str.substring(1);
    }
}
