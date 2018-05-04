package com.kingguanzhang.sorm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUtils {

    /*
    * Set sql's parameter;
    * @param ps ,precompile sql object;
    * @param params ,parameter;
    *
    * */
    public static void handelParams(PreparedStatement ps,Object[] params){

        if(null != params){
            for(int i=0 ; i<params.length; i++){
                try {
                    ps.setObject(1+i,params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
