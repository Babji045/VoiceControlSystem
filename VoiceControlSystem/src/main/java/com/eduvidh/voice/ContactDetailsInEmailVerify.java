package com.eduvidh.voice;
import java.io.IOException;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/ContactDetailsInEmailVerify")
public class ContactDetailsInEmailVerify extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        String message = request.getParameter("message");

        // Set up the email properties for admin
        Properties adminProperties = new Properties();
        adminProperties.put("mail.smtp.auth", "true");
        adminProperties.put("mail.smtp.starttls.enable", "true");
        adminProperties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP host
        adminProperties.put("mail.smtp.port", "587"); // Replace with your SMTP port

        // Set up the session for admin
        Session adminSession = Session.getInstance(adminProperties, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(email, "zmpz pzsl fjbu gaou"); // Replace with user email credentials//while html data giving user so user login details here currently user login mail
            }
        });

        try {
            // Create a default MimeMessage object for admin
            MimeMessage adminMessage = new MimeMessage(adminSession);
            adminMessage.setFrom(new InternetAddress(email));
            adminMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("temperraja123@gmail.com")); // Replace with admin email
            adminMessage.setSubject("New Contact Form Submission");
            adminMessage.setText("Name: " + name + "\nEmail: " + email + "\nCity: " + city + "\nMessage: " + message);

            
         // Print the email message details to console
            System.out.println("Sending Email to Admin:");
            System.out.println("From: " + adminMessage.getFrom()[0]);
            System.out.println("To: " + adminMessage.getAllRecipients()[0]);
            System.out.println("Subject: " + adminMessage.getSubject());
            System.out.println("Message: " + adminMessage.getContent());
            
            // Send message to admin
            Transport.send(adminMessage);

            // Send automatic response to user
            sendAutomaticResponse(email, name);

            // Redirect to a success page
            RequestDispatcher dis = request.getRequestDispatcher("Sent.html");
            dis.include(request, response);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAutomaticResponse(String email, String name) throws MessagingException, IOException {
        // Set up the email properties for user response
        Properties userResponseProperties = new Properties();
        userResponseProperties.put("mail.smtp.auth", "true");
        userResponseProperties.put("mail.smtp.starttls.enable", "true");
        userResponseProperties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP host
        userResponseProperties.put("mail.smtp.port", "587"); // Replace with your SMTP port

        // Set up the session for user response
        Session userResponseSession = Session.getInstance(userResponseProperties, new jakarta.mail.Authenticator() {
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication("temperraja123@gmail.com", "bpuf aumt dsds rsgx"); // Replace with admin email credentials // why means admin giving reply
            }
        });

        // Create a default MimeMessage object for user response
        MimeMessage userResponseMessage = new MimeMessage(userResponseSession);
        userResponseMessage.setFrom(new InternetAddress("temperraja123@gmail.com"));
        userResponseMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        userResponseMessage.setSubject("Thank you for contacting us");
        userResponseMessage.setText("Dear " + name + ",\n\nThank you for contacting us. We have received your message and will get back to you as soon as possible.\n\nBest regards,\nThe Admin Team");
        
        // Print the email message details to console
        System.out.println("Sending Automatic Response to User:");
        System.out.println("From: " + userResponseMessage.getFrom()[0]);
        System.out.println("To: " + userResponseMessage.getAllRecipients()[0]);
        System.out.println("Subject: " + userResponseMessage.getSubject());
        System.out.println("Message: " + userResponseMessage.getContent());

        // Send message to user
        Transport.send(userResponseMessage);
    }
}





