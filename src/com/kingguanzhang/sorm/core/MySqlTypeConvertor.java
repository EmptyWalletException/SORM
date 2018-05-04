package com.kingguanzhang.sorm.core;

public class MySqlTypeConvertor implements TypeConvertor {
    @Override
    public String databaseType2JavaType(String columntype) {
        if("varchar".equalsIgnoreCase(columntype) || "char".equalsIgnoreCase(columntype)){
            return "String";
        }else if("int".equalsIgnoreCase(columntype) || "tinyint".equalsIgnoreCase(columntype)
                || "smallint".equalsIgnoreCase(columntype) || "integer".equalsIgnoreCase(columntype)){
            return "Integer";
        }else if("bigint".equalsIgnoreCase(columntype)){
            return "Long";
        }else if("double".equalsIgnoreCase(columntype)) {
            return "Double";
        }else if("float".equalsIgnoreCase(columntype)) {
            return "Float";
        }else if("clob".equalsIgnoreCase(columntype)) {
            return "java.sql.Clob";
        }else if("blob".equalsIgnoreCase(columntype)) {
            return "java.sql.Blob";
        }else if("date".equalsIgnoreCase(columntype)) {
            return "java.sql.Date";
        }else if("time".equalsIgnoreCase(columntype)) {
            return "java.sql.Time";
        }else if("timestamp".equalsIgnoreCase(columntype)) {
            return "java.sql.timestamp";
        }
        return null;
    }

    @Override
    public String javaType2DatabaseType(String javaDataType) {
        return null;
    }
}
