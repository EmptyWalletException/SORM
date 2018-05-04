package com.kingguanzhang.sorm.bean;
/*
* Packaging one field in table
* */
public class ColumnInfo {

    private String name;

    private String dataType;

    //(0 : common key ; 1 : major key ; 2 : out key ;)
    private int keyType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getKeyType() {
        return keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }

    public ColumnInfo(String name, String dataType, int keyType) {
        this.name = name;
        this.dataType = dataType;
        this.keyType = keyType;
    }

    public ColumnInfo() {
    }
}
