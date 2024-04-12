
//import java.io.*;
//import javax.servlet.*;
//import javax.servlet.http.*;

package com.eduvidh.voice;
import java.sql.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginByVoice")
public class LoginByVoice extends HttpServlet {
//    static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
//    static final String JDBC_USER = "root";
//    static final String JDBC_PASSWORD = "12345";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

    	
    	 String voiceInput = request.getParameter("voiceInput");
    	    System.out.println("Voice Input: " + voiceInput);
    	    
    	    try {
    	        // Split the input based on keywords "username" and "password"
    	        String[] parts = voiceInput.split("(?i)username|password");
    	        
    	        if (parts.length == 3) {
    	            // Extract username and password from the spoken input
    	            String spokenUsername = parts[1].trim();
    	            String spokenPassword = parts[2].trim();
    	            
    	            // Map spoken values to expected values for database authentication
    	            String expectedUsername = mapUsername(spokenUsername);
    	            String expectedPassword = mapPassword(spokenPassword);
    	            
    	            System.out.println("Spoken Username: " + spokenUsername);
    	            System.out.println("Spoken Password: " + spokenPassword);
    	            System.out.println("Expected Username: " + expectedUsername);
    	            System.out.println("Expected Password: " + expectedPassword);
    	            
    	            // Authenticate against the database
    	            if (authenticate(expectedUsername, expectedPassword)) {
    	                // Successful login
    	                RequestDispatcher dis = request.getRequestDispatcher("Welcome.html");
    	                dis.include(request, response);
    	                return;
    	            } else {
    	                // Failed login
    	                RequestDispatcher dis = request.getRequestDispatcher("Loginerror.html");
    	                dis.include(request, response);
    	                return;
    	            }
    	        }
    	        
    	        // Invalid input format or no input provided
    	        RequestDispatcher dis = request.getRequestDispatcher("Login.html");
    	        dis.include(request, response);
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        throw new ServletException("Error during login", e);
    	    }
    	}

    	private String mapUsername(String spokenUsername) {
    	    // Map spoken username "Pawan 1" to expected username "Pavan1"
    	    return spokenUsername.replace("Pawan", "Pavan").replace(" 1", "1");
    	}

    	private String mapPassword(String spokenPassword) {
    	    // Map spoken password "Pawan at the rate 1" to expected password "Pavan@1"
    	    return spokenPassword.replace("Pawan", "Pavan").replace(" at the rate 1", "@1");
    	}

    	
    	
    	
    	
    	
    	
//    	 String voiceInput = request.getParameter("voiceInput");
//    	    System.out.println("Voice Input: " + voiceInput);
//    	    try {
//    	        // Split the input based on keywords "username" and "password"
//    	        String[] parts = voiceInput.split("(?i)username|password");
//    	        if (parts.length == 3) 
//    	        {
//    	            String username = parts[1].trim();
//    	            String password = parts[2].trim().replaceAll("at the rate 1", "@1"); // Replace "at the rate 1" with "@1"
//    	            System.out.println("Spoken Username: " + username);
//    	            System.out.println("Spoken Password: " + password);
//    	            
//    	            if (authenticate(username, password)) 
//    	            {
//    	                // Successful login
//    	                RequestDispatcher dis = request.getRequestDispatcher("Welcome.html");
//    	                dis.include(request, response);
//    	                return;
//    	            } 
//    	            else 
//    	            {
//    	                // Failed login
//    	                RequestDispatcher dis = request.getRequestDispatcher("RegSucess.html");
//    	                dis.include(request, response);
//    	                return;
//    	            }
//    	        }
//    	        // Invalid input format or no input provided
//    	        RequestDispatcher dis = request.getRequestDispatcher("Login.html");
//    	        dis.include(request, response);
//    	    } 
//    	    catch (Exception e) 
//    	    {
//    	        e.printStackTrace();
//    	        throw new ServletException("Error during login", e);
//    	    }
//    	
    	
    	
    	
    	
    	
    	
    	
    	//        String voiceInput = request.getParameter("voiceInput");
//        System.out.println("Voice Input: " + voiceInput);
//        try {
//        	 // Split the input based on keywords "username" and "password"
//            String[] parts = voiceInput.split("(?i)username|password");
//            if (parts.length == 3) 
//            {
//                String username = parts[1].trim();
//                String password = parts[2].trim();
//                System.out.println("Spoken Username: " + username);
//                System.out.println("Spoken Password: " + password);
//                
//                if (authenticate(username, password)) 
//                {
//                    // Successful login
//                	RequestDispatcher dis = request.getRequestDispatcher("Welcome.html");
//					  dis.include(request, response);
//                   // response.sendRedirect("Welcome.html");
//                    return;
//                } 
//                else 
//                {
//                    // Failed login
//                	RequestDispatcher dis = request.getRequestDispatcher("RegSucess.html");
//					  dis.include(request, response);
//                   // response.sendRedirect("Login.html");
//                    return;
//                }
//            }
//            // Invalid input format or no input provided
//            RequestDispatcher dis = request.getRequestDispatcher("Login.html");
//			  dis.include(request, response);
//           // response.sendRedirect("Login.html");
//        } 
//        catch (Exception e) 
//        {
//        	e.printStackTrace();
//            throw new ServletException("Error during login", e);
//        }
    //}
    
    private static boolean authenticate(String username, String password) throws ServletException, IOException
    {
    	 String url="jdbc:mysql://localhost:3306?user=root&password=12345";
	     String query="select * from voice.register where userName=? and password=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
           // String query = "SELECT * FROM voice.register WHERE userName = ? AND password = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            return rs.next(); // If result set contains a row, authentication is successful

        } 
        catch (Exception e) 
        {
            e.printStackTrace(); // Log or handle the exception appropriately
        } 
        finally
        {
            // Close resources in a finally block to ensure they are always closed
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Log or handle the exception appropriately
            }
        }
        return false;
    }
}
    

//    private static boolean authenticate(String username, String password) throws SQLException 
//    {
//       
//            
//            try 
//            {
//              Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD); 
//                String query = "SELECT * FROM voice.register WHERE userName = ? AND password = ?";
//                System.out.println("Query: " + query);
//                PreparedStatement stmt = conn.prepareStatement(query);
//                    stmt.setString(1, username);
//                    stmt.setString(2, password);
//                     ResultSet rs = stmt.executeQuery();
//                     if(rs.next())
//                    {
//                        return rs.next(); // If result set contains a row, authentication is successful
//                    }
//                     else
//                     {
//                    	 System.out.println("Authenction not sucess");
//                     }
//            
//        } 
//            catch (Exception e) 
//            {
//            e.printStackTrace(); // Print stack trace for debugging
//           }
//        return false;
//        
//    }
//}
