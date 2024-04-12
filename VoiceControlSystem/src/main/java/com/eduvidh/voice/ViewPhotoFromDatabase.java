package com.eduvidh.voice;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;

public class ViewPhotoFromDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306?user=root&password=12345";
        

        try {
            Connection connection = DriverManager.getConnection(url);

            String sql = "SELECT photo FROM voice.pic WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, 3); // Assuming you want to retrieve the photo with id = 1
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Retrieve the photo data as a byte array
                byte[] photoData = resultSet.getBytes("photo");

                // Convert byte array to BufferedImage
                ByteArrayInputStream bis = new ByteArrayInputStream(photoData);
                BufferedImage image = ImageIO.read(bis);

                // Display the image using Swing
                JFrame frame = new JFrame();
                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(new JLabel(new ImageIcon(image)));
                frame.pack();
                frame.setVisible(true);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}


