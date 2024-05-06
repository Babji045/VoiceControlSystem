package com.eduvidh.voice;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/RegsiterVerify")
@MultipartConfig(maxFileSize = 16177216)//upto 16mb
public class RegsiterVerify  extends HttpServlet
{
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
     {
        //super.doPost(req, resp);
    	 //To Take the Data From the Html Page
    	String username= req.getParameter("un");
    	String nam= req.getParameter("name");
    	String em= req.getParameter("email");
    	String password= req.getParameter("pass");
    	String ConfirmPassword = req.getParameter("confirmPass");
    	String phone = req.getParameter("mb");
    	Part filePart = req.getPart("fileToUpload");
    	PrintWriter writer  = resp.getWriter();
    	
    	// Validate username
        if (!isValidUsername(username)) 
        {
            writer.println("<h1 style=color:red>Invalid Username. Please enter a username starting with a capital letter followed by characters and ending with a number.</h1>");
            RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
            dis.include(req, resp);
            return; // Exit the method
        }
        // Validate password
        if (!isValidPassword(password)) 
        {
            writer.println("<h1 style=color:red>Invalid Password. Please enter a password containing at least one capital letter and one special character (@ or $).and ending with a number</h1>");
            RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
            dis.include(req, resp);
            return; // Exit the method
        }
        
        // Validate email
        if (isEmailExists(em)) 
        {
            writer.println("<h1 style=color:red>Email already exists. Please use another email.</h1>");
            RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
            dis.include(req, resp);
            return; // Exit the method
        }
        // Unique UserName valiation
        if (isUserExists(username)) 
        {
            writer.println("<h1 style=color:red>UserName already exists. Please use another UserName.</h1>");
            RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
            dis.include(req, resp);
            return; // Exit the method
        }
        
    	
   	 if (username == null || username.trim().isEmpty()) {
        writer.println("<h1 style=color:red>Username is required. Please try again.</h1>");
        RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
        dis.include(req, resp);
        return; // Exit the method
    }
        
    	    if (nam == null || nam.trim().isEmpty()) {
    	        writer.println("<h1 style=color:red>Name is required. Please try again.</h1>");
    	        RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
    	        dis.include(req, resp);
    	        return; // Exit the method
    	    }
    	    
    	    if (em == null || em.trim().isEmpty()) {
    	        writer.println("<h1 style=color:red>Email is required. Please try again.</h1>");
    	        RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
    	        dis.include(req, resp);
    	        return; // Exit the method
    	    }
    	    
    	    if (password == null || password.trim().isEmpty()) {
    	        writer.println("<h1 style=color:red>Password is required. Please try again.</h1>");
    	        RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
    	        dis.include(req, resp);
    	        return; // Exit the method
    	    }

    	
    	    // Process file upload
         //   Part filePart = req.getPart("fileToUpload");
//            InputStream inputStream = null;
//            if (filePart != null) {
//                inputStream = filePart.getInputStream();
//            }  
    	    
    	    
    	
    	
    	//Establish the Jdbc Connection
    	String url="jdbc:mysql://localhost:3306?user=root&password=12345";
    	String query="insert into voice.register (userName, name, email, password, profilephoto,confirmPasswordl,mobileNo) values(?,?,?,?,?,?,?)";
    	 try 
    	 {
    		 Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url);
			PreparedStatement  ps = conn.prepareStatement(query);
			 InputStream is = filePart.getInputStream();
			   ps.setString(1, username);
			   ps.setString(2, nam);
			   ps.setString(3, em);
			   ps.setString(4, password);
			  // ps.setBlob(5, is);
			   ps.setString(6, ConfirmPassword);
			   ps.setString(7, phone);
			   
			   if (is != null) 
			   {
	              // If inputStream is not null, set the photo parameter as input stream
				   ps.setBlob(5, is);
	            } 
			   else 
	            {
	                // Set photo parameter as null if inputStream is null
				   ps.setNull(5, Types.BLOB);
	            }
			 int rec= ps.executeUpdate();
			 if (rec>0) 
			 {
				 writer.println("<h1 style=color:green><center>Registartion Sucess</center></h1>");
			RequestDispatcher dis = req.getRequestDispatcher("Welcomepage.html");
			  dis.include(req, resp);
				System.out.println("Data inserted");
			}
			 else
			 {
				 writer.println("<h1 style=color:red><center>Invalid Registartion Details Try Again</center></h1>");
				 RequestDispatcher dis = req.getRequestDispatcher("Signup.html");
				  dis.include(req, resp);
				 System.out.println("Data not inserted"); 
			 }
			 conn.close();
			   
		} 
    	 catch (Exception e) 
    	 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    } 
     
  // Method to validate username
     private boolean isValidUsername(String username) {
         // Regular expression to match username pattern
         String regex = "^[A-Z].*[0-9]$";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(username);
         return matcher.matches();
     }
  // Method to validate password
     private boolean isValidPassword(String password) {
         // Regular expression to match password containing at least one capital letter and one special character
    	 //one special character, and ending with a number
         String regex = "^(?=.*[A-Z])(?=.*[@$])[a-zA-Z0-9@$]*[0-9]$";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(password);
         return matcher.matches();
     }
  // Method to check if email exists in the database
     private boolean isEmailExists(String em)
     {
         String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
         String query = "SELECT * FROM voice.register WHERE email = ?";

         try
         {
        	 Class.forName("com.mysql.jdbc.Driver");
             Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);
             pstmt.setString(1, em);
             ResultSet rs = pstmt.executeQuery();
             return rs.next(); // If ResultSet has next row, means email already exists
         } 
         catch (Exception e) 
         {
             e.printStackTrace();
             return false; // Assume email doesn't exist in case of any error
         }
         
     }
     
     // Method to check if email exists in the database
     private boolean isUserExists(String username)
     {
         String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
         String query = "SELECT * FROM voice.register WHERE userName = ?";

         try
         {
        	 Class.forName("com.mysql.jdbc.Driver");
             Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);
             pstmt.setString(1, username);
             ResultSet rs = pstmt.executeQuery();
             return rs.next(); // If ResultSet has next row, means email already exists
         } 
         catch (Exception e) 
         {
             e.printStackTrace();
             return false; // Assume email doesn't exist in case of any error
         }
         
     }
}
