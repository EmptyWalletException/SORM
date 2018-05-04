package com.kingguanzhang.sorm.core;

/*
* Be responsible for conversion type of data in java and database
*
*/
public interface TypeConvertor {
/*
* Conversion database data to type of java;
*@param columnType ,Type of database data ;
* @return ,Type of java;
*/
    public String databaseType2JavaType(String columntype);

/*
* Conversion java data to type of database;
*@param javaDataType ,Type of java;
* @return ,Type of database data ;
*/
    public String javaType2DatabaseType(String javaDataType);


}
