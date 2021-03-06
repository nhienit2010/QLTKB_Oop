package com.kma.qltkb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {

	public String fullName;
	public String id;
	public String pass;
	
	public Student() {}
	public Student(String fullName, String id, String pass) {
		this.fullName = fullName;
		this.id = id;
		this.pass = pass;
	}

	// Thêm một user vào trong database
	public void addIntoSQL() {
		try {
			Connect_SQL connect = new Connect_SQL();
			Connection conn = connect.getConnection("jdbc:mysql://localhost:3306/test", "root", ""); 
			Statement stmt = conn.createStatement();
			
            String query = "insert into oop_users values ('" + this.id +"', '" + this.pass + "');";
            stmt.executeUpdate(query);
            conn.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	// Check người dùng có id đã tồn tại trong database hay chưa?
	public static boolean checkExists(String id) {
		boolean flag = false;
		
		try {
			Connect_SQL connect = new Connect_SQL();
			Connection conn = connect.getConnection("jdbc:mysql://localhost:3306/test", "root", ""); 
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select username from oop_users");
            
            while (rs.next() && flag == false) {
            	System.out.println(rs.getString(1));
                if (rs.getString(1).equals(id) )
                	flag = true;
            }
            conn.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
		return flag;
		
	}
	
	// Kiểm tra id và pass vừa nhập vào có khớp với dữ liệu trong database hay không? Nếu có thì được phép login và không thì trả về lỗi
	public static boolean checkLogin(String id,String pass) {
		boolean flag = false;

		try {
			Connect_SQL connect = new Connect_SQL();
			Connection conn = connect.getConnection("jdbc:mysql://localhost:3306/test", "root", ""); 
			Statement stmt = conn.createStatement();		
			String query = "select password from oop_users where username= '"+ id + "'";
			ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                if (pass.equals(rs.getString(1)))
                	flag = true;
                else 
                	flag = false;
                break;
            }
            conn.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
		
		return flag;
	}
}
