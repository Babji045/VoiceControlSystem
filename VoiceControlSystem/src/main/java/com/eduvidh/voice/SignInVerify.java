package com.eduvidh.voice;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
//To Save the Photo in the Databse without servlet
public class SignInVerify {

	public static void main(String[] args)
	{
		String url="jdbc:mysql://localhost:3306?user=root&password=12345";
    	String query="insert into voice.pic values(?,?)";
    	 try 
    	 {
    		 Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement  ps = conn.prepareStatement(query);
			 File imageFile = new File("C:\\Users\\babji\\OneDrive\\Desktop\\car.png");
			    FileInputStream f=new FileInputStream(imageFile);
//			   ps.setString(1, "BB3");
//			   ps.setString(2, "BB");
//			   ps.setString(3, "bb3@gmail.com");
//			   ps.setString(4, "Bb@3");
//			   //ps.setBlob(5, f);
			    
			  ps.setBinaryStream(1, f,(int) imageFile.length());
			  ps.setInt(2, 3);
			//   ps.setBlob(1, f,(int) imageFile.length());
			 int rec= ps.executeUpdate();
			 if (rec>0) 
			 {
			
				System.out.println("Data inserted");
			}
			 else
			 {
				
				 System.out.println("Data not inserted"); 
			 }
			 f.close();
			 conn.close();
			   
		} 
    	 catch (Exception e) 
    	 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 

}
