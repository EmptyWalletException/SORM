package com.kingguanzhang.po;
import java.sql.*;
import java.util.*;
public class Emp{
	private String name;
 	private Integer id;
 	private Integer age;
 
	public String getName(){
		return name;
	}
	public Integer getId(){
		return id;
	}
	public Integer getAge(){
		return age;
	}

	public String setName(String name){
		this.name=name;
		return name;
	}
	public Integer setId(Integer id){
		this.id=id;
		return id;
	}
	public Integer setAge(Integer age){
		this.age=age;
		return age;
	}

}
