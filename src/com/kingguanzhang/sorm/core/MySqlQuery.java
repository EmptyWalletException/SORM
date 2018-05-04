package com.kingguanzhang.sorm.core;

import com.kingguanzhang.po.Emp;

import java.util.List;

public class MySqlQuery extends Query{
    public static void main(String[] args) {
       /* Emp e = new Emp();
        e.setId(2);
        e.setName("test");
        e.setAge(13);
        new MySqlQuery().update(e,new String[]{"name","age"});
        */

      /* List<Emp> list = new MySqlQuery().queryRows("select id,name,age from emp where age>? and id<?",Emp.class,new Object[]{13,5});

        for(Emp e:list){
            System.out.println(e.getId()+"\t"+e.getName()+"\t"+e.getAge());
        }*/

    }


    @Override
    public Object queryPagenate(int pageNum, int size) {
        return null;
    }
}
