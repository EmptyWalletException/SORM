package com.kingguanzhang.sorm.core;

import com.kingguanzhang.sorm.bean.ColumnInfo;
import com.kingguanzhang.sorm.bean.TableInfo;
import com.kingguanzhang.sorm.utils.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.kingguanzhang.sorm.utils.JavaFileUtils.createJavaPOFile;

/*
*Be responsible for manage all the relationship between table and calss;
 */
public class TableContext {

    public static Map<String,TableInfo> tables = new HashMap<String, TableInfo>();

    public static Map<Class,TableInfo> poClassTableMap = new HashMap<Class, TableInfo>();

    private TableContext(){}

    static {
      try {
          Connection con = DBManager.getConn();

          DatabaseMetaData dbmd = con.getMetaData();
          ResultSet tableRet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
          while (tableRet.next()) {
              String tableName = (String) tableRet.getObject("TABLE_NAME");
              TableInfo ti = new TableInfo(tableName, new ArrayList<ColumnInfo>(), new HashMap<String, ColumnInfo>());
              tables.put(tableName, ti);
              ResultSet set = dbmd.getColumns(null, "%", tableName, "%");
              while (set.next()) {
                  ColumnInfo ci = new ColumnInfo(set.getString("COLUMN_NAME"),set.getString("TYPE_NAME"),0);
                  ti.getColumns().put(set.getString("COLUMN_NAME"),ci);
              }
              ResultSet set2 = dbmd.getPrimaryKeys(null, "%", tableName);
              while (set2.next()) {
                  ColumnInfo ci2 = (ColumnInfo) ti.getColumns().get(set2.getObject("COLUMN_NAME"));
                  ci2.setKeyType(1);
                  ti.getPrikey().add(ci2);
              }

              if(ti.getPrikey().size()>0){
                  ti.setOnlyPriKey(ti.getPrikey().get(0));
              }
          }
      }catch (SQLException e){
        e.printStackTrace();
      }

      updateJavaPOFile(); // When the frame start,this method will update java class;
      loadPOTables(); // When the frame start,load all class in the po package,to improve efficient;
    }

    public static  Map<String,TableInfo> getTableInfos(){
        return tables;
    }

    // This method be used to update java class in poPackage on the basis of table.
    public static void updateJavaPOFile(){
        Map<String,TableInfo> map = TableContext.tables;
        int i = 1;
        for(TableInfo t:map.values()){
            createJavaPOFile(t,new MySqlTypeConvertor());
            System.out.println("*** Table NO."+i+" create succeed ***");
        }
    }

    public static void loadPOTables(){
        for(TableInfo tableInfo:tables.values()){
            Class c = null;
            try {
                c = Class.forName(DBManager.getConf().getPoPackage()+"."+ StringUtils.firstChar2UpperCase(tableInfo.getTname()));
                poClassTableMap.put(c,tableInfo);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){

        Map<String,TableInfo> tables = TableContext.tables;
        System.out.print(tables);

    }
}
