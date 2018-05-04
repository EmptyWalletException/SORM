package com.kingguanzhang.pool;
import com.kingguanzhang.sorm.core.DBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnPool {
    private  List pool;
    private static final int POOL_MAX_SIZE = DBManager.getConf().getPoolMaxSize();
    private static final int POOL_MIN_SIZE = DBManager.getConf().getPoolMinSize();

    public DBConnPool() {
        initPool();
    }

    // Initalize connect pool , insure pool size > MIN_SIZE ;
    public  void initPool(){
        if(null == pool) { pool = new ArrayList(); }
        while (pool.size()<DBConnPool.POOL_MIN_SIZE){
            pool.add(DBManager.createConn());
            System.out.println("*** Hava "+pool.size()+" connect now ***");
        }
    }

    // Get a connect from connect pool ;
    public  synchronized Connection getConnection(){
        int last_index = pool.size()-1;
        Connection conn = (Connection) pool.get(last_index);
        pool.remove(last_index);
        return conn;
    }

    public synchronized void close(Connection conn){
        if (pool.size()>POOL_MAX_SIZE) {
            try {
                if (null != conn) { conn.close(); }
            } catch (Exception e) { e.printStackTrace(); }
        }else{
            pool.add(conn);
        }
    }
}
