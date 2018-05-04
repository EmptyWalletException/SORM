package com.kingguanzhang.sorm.utils;

import java.lang.reflect.Method;

public class ReflectUtils {

    /* Use reflect call Object obj corresponding property fieleName's get method;
    *@param fieldName
    * @param obj
    * @return Object
    */
    public static Object invokeGet(String fieldName ,Object obj){
        try {
            Class c = obj.getClass();
            Method m = c.getDeclaredMethod("get"+ StringUtils.firstChar2UpperCase(fieldName),null);
            return  m.invoke(obj,null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void invokeSet(Object obj,String columnName,Object columnValue){

        Method m = null;
        try {
            if(null == columnValue){
                m = obj.getClass().getDeclaredMethod("set"+ StringUtils.firstChar2UpperCase(columnName),columnValue.getClass());
                m.invoke(obj,columnValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
