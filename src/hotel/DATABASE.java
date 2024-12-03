/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel;

import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
 
public class DATABASE {
     public static Connection Connect() {
         Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/hotel", "root", "");
            System.out.println("Connected");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
      public static void main(String args[]) {
      Connect();
       
      
    }
}
