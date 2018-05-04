package com.kingguanzhang.sorm.core;

import com.kingguanzhang.sorm.bean.ColumnInfo;
import com.kingguanzhang.sorm.bean.TableInfo;
import com.kingguanzhang.sorm.utils.JDBCUtils;
import com.kingguanzhang.sorm.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
*
* @author kingguanzhang
* */
public abstract class Query implements Cloneable{
    /*
    *Direct execution a DML statements,
    * @param sql ,it is mean sql statements
    *@param params ,it is mean Parameter
    * @teturn ,affected line number;
    * */
    public int executeDML(String sql , Object[] params){
        Connection conn = DBManager.getConn();
        int count = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            JDBCUtils.handelParams(ps,params);
            count = ps.executeUpdate();

            System.out.println("*** Sql statement: '"+ps+"' execute succeed ***");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.close(ps,conn);
        }
        return count;
    }

    public void insert(Object obj){
        //insert into tableName (id,uname,pwd) values (?,?,?)
        Class c = obj.getClass();
        List<Object> params = new ArrayList<Object>();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        StringBuilder sql = new StringBuilder("insert into "+tableInfo.getTname()+" (");
        int countNotNullField = 0;
        Field[] fs = c.getDeclaredFields();
        for(Field f: fs){
            String fieldName = f.getName();
            Object fieldValue = ReflectUtils.invokeGet(fieldName,obj);
            if (null != fieldValue){
                sql.append(fieldName+",");
                countNotNullField++;
                params.add(fieldValue);
            }
        }
        sql.setCharAt(sql.length()-1, ')');
        sql.append(" values (");
        for(int i=0 ; i<countNotNullField ; i++){
            sql.append("?,");
        }
        sql.setCharAt(sql.length()-1,')');
        executeDML(sql.toString(),params.toArray());
    }

    /*
    * //delete from User where id = ?;
    * Delete clazz Said class corresponding table's record;
    * @param clazz  ,The Object of the table corresponding class
    * @param id ,Major key price;
    * @return Use "int" return 1 or -1 ,or usr void return nothing;
    * */
    public void delete(Class clazz , Object id){

        //Emp.class,2 --> delete from emp where id=2
        TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        String sql = "delete from "+tableInfo.getTname()+" where "+onlyPriKey.getName()+"=? ";
        executeDML(sql,new Object[]{id});


    }

    /*
     * Delete class corresponding table's record in database(Object class corresponding to table,object major key corresponding to record);
     * @param clazz  ,The Object of the table corresponding class
     * @param id ,Major key price;
     * @return Use "int" return 1 or -1 ,or usr void return nothing;
     * */
    public void delete(Object obj){
        Class c = obj.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo onlyPrikey = tableInfo.getOnlyPriKey();

        Object priKeyValue = ReflectUtils.invokeGet(onlyPrikey.getName(),obj);
        delete(c,priKeyValue);
    }

    /*
     *update user set uname = ?,pwd=?
     * Update object corresponding record,only appoented field price;
     * @param obj ,The object which need update;
     * @param fieldNames ,Updated field list;
     * @return ,affected line number;
     * */
    public int update(Object obj,String[] fieldNames){
        Class c = obj.getClass();
        List<Object> params = new ArrayList<Object>();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo prikey = tableInfo.getOnlyPriKey();
        StringBuilder sql = new StringBuilder("update "+tableInfo.getTname()+" set ");

        for(String fname:fieldNames){
            Object fvalue = ReflectUtils.invokeGet(fname,obj);
            params.add(fvalue);
            sql.append(fname+"=?,");
        }
        sql.setCharAt(sql.length()-1,' ');
        sql.append(" where ");
        sql.append(prikey.getName()+"=? ");
        params.add(ReflectUtils.invokeGet(prikey.getName(),obj));
        return executeDML(sql.toString(),params.toArray());
    }

    public Object executeQueryTemplate(String sql,Object[] params,Class clazz,CallBack back){
        Connection conn = DBManager.getConn();
        List list = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            JDBCUtils.handelParams(ps,params);
            rs = ps.executeQuery();

           return back.doExecute(conn,ps,rs);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            DBManager.close(ps,conn);
        }

    }

    /*
     * Query multi-line record, and let the returned price packaging into the  clazz appoented class's object;
     * @param sql ,Query sql statement;
     * @param clazz ,The javabean class which is use to packaging data ;
     * @param params ,sql parameter;
     * @return ,The results of the query;
     * */
    public List queryRows(final String sql, final Class clazz, final Object[] params){
        Connection conn = DBManager.getConn();
        // Use template and callback ;
       return (List)executeQueryTemplate(sql, params, clazz, new CallBack() {
            @Override
            public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) {
                List list = null;
                try {
                    ResultSetMetaData metaData = null;
                    metaData = rs.getMetaData();
                    while (rs.next()) {   //handling line;
                        if (null == list){
                            list = new ArrayList();
                        }
                            Object rowObj = clazz.newInstance();
                        for (int i = 0; i < metaData.getColumnCount(); i++) {  //handling row;
                            String columnName = metaData.getColumnLabel(i + 1);
                            Object columnValue = rs.getObject(i + 1);
                            ReflectUtils.invokeSet(rowObj, columnName, columnValue);
                        }
                        list.add(rowObj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return list;
            }
        });
    }

    /*
     * Query a-line record, and let the returned price packaging into the  clazz appoented class's object;
     * @param sql ,Query sql statement;
     * @param clazz ,The javabean class which is use to packaging data ;
     * @param params ,sql parameter;
     * @return ,The results of the query;
     * */
    public Object queryUniqueRows(String sql, Class clazz ,Object[] params){
        List list = queryRows(sql,clazz,params);
        return (list != null || list.size()>0)?list.get(0):null;
    }

    /*
     * Query and return a value;
     * @param sql ,Query sql statement;
     * @param params ,sql parameter;
     * @return ,The results of the query;
     * */
    public Object queryValue(String sql ,Object[] params){
        // Use template and callback ;
        return executeQueryTemplate(sql, params, null, new CallBack() {
            @Override
            public Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs) {
                Object value = null;
                    try {
                        while(rs.next()){   //handling line;
                            value = rs.getObject(1);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                return value;
            }
        });
    }

    /*
     * Query and return a number;
     * @param sql ,Query sql statement;
     * @param params ,sql parameter;
     * @return ,The results of the query;
     * */
    public Number queryNumber(String sql ,Object[] params){
        return (Number) queryValue(sql,params);
    }

    /*
    * In different database, this method need write different code;
    * @param pageNum ;The number of pages to query;
    * @param size ;How many records are displayed per page;
    * @return Object
    * */
    public abstract Object queryPagenate(int pageNum,int size);

    @Override
    protected Object clone() throws CloneNotSupportedException{

        return  super.clone();
    }



}

