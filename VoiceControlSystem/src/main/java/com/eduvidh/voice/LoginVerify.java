package com.eduvidh.voice;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/LoginVerify")
public class LoginVerify extends HttpServlet
{
   @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
   {
      //super.doPost(req, resp);
	    String username=req.getParameter("un");
	    String password=req.getParameter("pass");
	      PrintWriter writer  = resp.getWriter();
	      
	      //Establish Jdbc connection
	      
	      String url="jdbc:mysql://localhost:3306?user=root&password=12345";
	    	String query="select * from voice.register where userName=? and password=?";
	    	 try 
	    	 {
	    		 Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url);
				PreparedStatement  ps = conn.prepareStatement(query);
				// InputStream is = filePart.getInputStream();
				   ps.setString(1, username);
				  ps.setString(2, password);
				 ResultSet rs= ps.executeQuery();
				 if (rs.next()) 
				 {
				   writer.println("<h1 style=color:green><center>Login Sucess</center></h1>");
				   writer.println("<h1 style=color:green><center>Welcome "+rs.getString("name")+"</center></h1>");
				     RequestDispatcher dis = req.getRequestDispatcher("Welcome.html");
				    dis.include(req, resp);
					System.out.println("Login Sucess");
				}
				 else
				 {
					 writer.println("<h1 style=color:red><center>Invalid Login Details Try Again</center></h1>");
					 RequestDispatcher dis = req.getRequestDispatcher("Login.html");
					  dis.include(req, resp);
					 System.out.println("Invalid Login Details"); 
				 }
				 conn.close();
				   
			} 
	    	 catch (Exception e) 
	    	 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 
	    }
	      
	    
   }

