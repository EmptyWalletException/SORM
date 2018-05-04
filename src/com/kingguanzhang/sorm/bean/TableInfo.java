package com.kingguanzhang.sorm.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*Table info
*
* */
public class TableInfo {

    private  String tname;

    private Map<String,ColumnInfo> columns;

    //The major key (onlyPrikey it is mean Only Private Key)
    private ColumnInfo onlyPriKey;



    private List<ColumnInfo> prikey;//Unite Private Key;


    public List<ColumnInfo> getPrikey() {
        return prikey;
    }

    public void setPrikey(List<ColumnInfo> prikey) {
        this.prikey = prikey;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Map<String, ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, ColumnInfo> columns) {
        this.columns = columns;
    }

    public ColumnInfo getOnlyPriKey() {
        return onlyPriKey;
    }

    public void setOnlyPriKey(ColumnInfo onlyPriKey) {
        this.onlyPriKey = onlyPriKey;
    }

    public TableInfo(String tname, Map<String, ColumnInfo> columns, ColumnInfo onlyPriKey) {
        this.tname = tname;
        this.columns = columns;
        this.onlyPriKey = onlyPriKey;
    }

    public TableInfo(String tname, List<ColumnInfo> prikey,Map<String, ColumnInfo> columns) {
        this.tname = tname;
        this.columns = columns;
        this.prikey = prikey;
    }


    public TableInfo() {
    }
}
