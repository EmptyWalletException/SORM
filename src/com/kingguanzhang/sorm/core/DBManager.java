package com.kingguanzhang.sorm.core;

import com.kingguanzhang.pool.DBConnPool;
import com.kingguanzhang.sorm.bean.Configuration;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/*
* Maintain the manage of connect object on the basis of configuration info;
*
* */
public class DBManager {

    private static Configuration conf;
    private static DBConnPool pool;
    static {
        Properties pros = new Properties();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        conf = new Configuration();
        conf.setDriver(pros.getProperty("driver"));
        conf.setPoPackage(pros.getProperty("poPackage"));
        conf.setPwd(pros.getProperty("pwd"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setURL(pros.getProperty("URL"));//Once "URL" was written into "url",result in SQLException: (The url cannot be null);
        conf.setUser(pros.getProperty("user"));
        conf.setUsingDB(pros.getProperty("usingDB"));
        conf.setQueryClass(pros.getProperty("queryClass"));
        conf.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));
        conf.setPoolMaxSize(Integer.parseInt(pros.getProperty("poolMaxSize")));
    }

    //Get method;

    public static Configuration getConf(){
        return conf;
    }

    /*
     * Get connect
     * */
    public static Connection getConn() {
      if(null == pool){
          pool = new DBConnPool();
      }
        return pool.getConnection();
    }

    /*
     * Create connect
     * */
    public static Connection createConn() {
        try {
            Class.forName(conf.getDriver());
            return DriverManager.getConnection(conf.getURL(), conf.getUser(), conf.getPwd());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /*
    * Close connect
    *@param rs
    *@param ps
    *@param conn
    *
    * */
    public static void close(ResultSet rs, Statement ps, Connection conn) {

        try {
            if (null != rs) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (null != ps) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(conn);
    }
    public static void close(Statement ps, Connection conn) {

        try {
            if (null != ps) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(conn);

    }

    public static void close(Connection conn) {

        pool.close(conn);
    }


}
