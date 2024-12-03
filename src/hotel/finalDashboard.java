/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotel;

import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author ziamdriel03
 */
public class finalDashboard extends javax.swing.JFrame {

    /**
     * Creates new form finalDashboard
     */
    public finalDashboard() {
        initComponents();
        
        autoID();
        Load_room();
        Load_client();
        Load_reservation();
        updateReservationCount();
        updateClientCount();
        roomCount();
         jPanel6.removeAll();        // Clear any previous content
    jPanel6.add(jPanel10);      // Add the desired panel (e.g., jPanel10 as Home)
    jPanel6.repaint();          // Repaint to refresh the UI
    jPanel6.revalidate();  
    }


Connection con;
    PreparedStatement pat;
    DefaultTableModel d;

    
public void autoID() {
    try {
        con = DATABASE.Connect();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT MAX(RoomID) FROM room");
        
        if (rs.next()) { // Ensure there is a result
            String maxID = rs.getString(1); // Fetch the max RoomID
            
            if (maxID == null || maxID.isEmpty()) {
                jLabel9.setText("R0001");
            } else {
                try {
                    long id = Long.parseLong(maxID.substring(1)); // Extract numeric part, skipping 'R'
                    id++; // Increment the numeric part
                    jLabel9.setText("R" + String.format("%04d", id)); // Format with leading zeros
                } catch (NumberFormatException e) {
                    Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Failed to parse RoomID: " + maxID, e);
                    jLabel9.setText("R0001"); // Fallback if parsing fails
                }
            }
        } else {
            jLabel9.setText("R0001"); // Handle cases with no rows
        }
    } catch (SQLException ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Database error: " + ex.getMessage(), ex);
        jLabel9.setText("R0001"); // Fallback for database errors
    }
}



public void updateReservationCount() {
    try {
        
        // Step 1: Ensure 'con' is a valid and open connection
        if (con == null || con.isClosed()) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Database connection is not valid!");
            jLabel16.setText("Database connection error!");
            return;
        }

        // Step 2: Execute SQL query
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM reservation");

        // Step 3: Process result
        if (rs.next()) {
            int count = rs.getInt(1); // Fetch the count of reservations
            jLabel16.setText(" " + count); // Update JLabel
            Logger.getLogger(finalDashboard.class.getName()).log(Level.INFO, "Reservation count updated: " + count);
        } else {
            jLabel16.setText("Total Reservations: 0"); // Default text if no rows found
            Logger.getLogger(finalDashboard.class.getName()).log(Level.INFO, "No reservations found.");
        }
    } catch (SQLException ex) {
        // Step 4: Log and display database error
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Database error: " + ex.getMessage(), ex);
        jLabel16.setText("Error fetching data!");
    } catch (Exception ex) {
        // Step 5: Catch unexpected exceptions
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Unexpected error: " + ex.getMessage(), ex);
        jLabel16.setText("Unexpected error occurred!");
    }
}
public void updateClientCount() {
    try {
        // Step 1: Ensure 'con' is a valid and open connection
        if (con == null || con.isClosed()) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Database connection is not valid!");
            jLabel18.setText("Database connection error!");
            return;
        }

        // Step 2: Execute SQL query to count clients
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM user");

        // Step 3: Process result
        if (rs.next()) {
            int count = rs.getInt(1); // Fetch the count of clients
            jLabel18.setText(" " + count); // Update JLabel
            Logger.getLogger(finalDashboard.class.getName()).log(Level.INFO, "Client count updated: " + count);
        } else {
            jLabel18.setText("Total Clients: 0"); // Default text if no rows found
            Logger.getLogger(finalDashboard.class.getName()).log(Level.INFO, "No clients found.");
        }
    } catch (SQLException ex) {
        // Step 4: Log and display database error
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Database error: " + ex.getMessage(), ex);
        jLabel18.setText("Error fetching data!");
    } catch (Exception ex) {
        // Step 5: Catch unexpected exceptions
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Unexpected error: " + ex.getMessage(), ex);
        jLabel18.setText("Unexpected error occurred!");
    }
}
public void addNewRoom(String roomDetails) {
    try {
        // Step 1: Insert the new room into the database
        String query = "INSERT INTO room (room_details) VALUES (?)"; // Modify this according to your actual schema
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, roomDetails);
        pst.executeUpdate(); // Insert the new room

        // Step 2: After adding the new room, refresh the room count
        roomCount();

        // Optionally, log success
        Logger.getLogger(finalDashboard.class.getName()).log(Level.INFO, "New room added successfully.");

    } catch (SQLException ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Failed to add new room: " + ex.getMessage(), ex);
    } catch (Exception ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Unexpected error: " + ex.getMessage(), ex);
    }
}
public void roomCount() {
    try {
        // Step 1: Ensure 'con' is a valid and open connection
        if (con == null || con.isClosed()) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Database connection is not valid!");
            jLabel20.setText("Database connection error!");
            return;
        }

        // Step 2: Execute SQL query to count clients
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM room");

        // Step 3: Process result
        if (rs.next()) {
            int count = rs.getInt(1); // Fetch the count of clients
            jLabel20.setText(" " + count); // Update JLabel
            Logger.getLogger(finalDashboard.class.getName()).log(Level.INFO, "Client count updated: " + count);
        } else {
            jLabel20.setText("Total Clients: 0"); // Default text if no rows found
            Logger.getLogger(finalDashboard.class.getName()).log(Level.INFO, "No clients found.");
        }
    } catch (SQLException ex) {
        // Step 4: Log and display database error
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Database error: " + ex.getMessage(), ex);
        jLabel20.setText("Error fetching data!");
    } catch (Exception ex) {
        // Step 5: Catch unexpected exceptions
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, "Unexpected error: " + ex.getMessage(), ex);
        jLabel20.setText("Unexpected error occurred!");
    }
}
    public void Load_room() {
        try {
        pat = con.prepareStatement("SELECT * FROM room");
        ResultSet rs = pat.executeQuery();
        ResultSetMetaData rsd = rs.getMetaData();
        int c = rsd.getColumnCount(); // Retrieve the number of columns
        
        d = (DefaultTableModel) jTable1.getModel();
        d.setRowCount(0); // Clear the table model

        while (rs.next()) {
            Vector<String> v2 = new Vector<>();

            // Add each column value directly to the Vector
            v2.add(rs.getString("RoomId"));
            v2.add(rs.getString("RoomType"));
            v2.add(rs.getString("BedType"));
            v2.add(rs.getString("Amount"));

            d.addRow(v2); // Add the row to the table model
        }

    } catch (SQLException ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
    }
            }

public void Load_reservation() {
      try {
        // Use a parameterized query to prevent SQL injection
        pat = con.prepareStatement("SELECT * FROM reservation");
        ResultSet rs = pat.executeQuery();

        // Get table model and clear existing rows
        d = (DefaultTableModel) jTable3.getModel();
        d.setRowCount(0); // Clear previous data

        while (rs.next()) {
            // Create a new row with values for the current reservation
            Vector<String> v2 = new Vector<>();
            v2.add(rs.getString("userID"));
            v2.add(rs.getString("ReserveID"));
            v2.add(rs.getString("Name"));
            v2.add(rs.getString("Address"));
            v2.add(rs.getString("MobileNo"));
            v2.add(rs.getString("CheckIn"));
            v2.add(rs.getString("CheckOut"));
            v2.add(rs.getString("roomType"));
            v2.add(rs.getString("roomNo"));
            v2.add(rs.getString("bedType"));
            v2.add(rs.getString("amount"));
            v2.add(rs.getString("status"));  // Ensure the status is displayed correctly

            // Add the row to the table model
            d.addRow(v2);
        }
    } catch (SQLException ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error loading reservations: " + ex.getMessage());
    }
}

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tab1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tab2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tab3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tab4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtamount = new javax.swing.JTextField();
        txtroom = new javax.swing.JComboBox<>();
        txtbed = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 0, 255));

        tab1.setBackground(new java.awt.Color(204, 0, 255));
        tab1.setPreferredSize(new java.awt.Dimension(150, 70));
        tab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1MouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("HOME");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-home-50.png"))); // NOI18N

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(14, 14, 14))
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab2.setBackground(new java.awt.Color(204, 0, 255));
        tab2.setPreferredSize(new java.awt.Dimension(150, 70));
        tab2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2MouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MANAGE ROOM");

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-room-52.png"))); // NOI18N

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(tab2Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        tab3.setBackground(new java.awt.Color(204, 0, 255));
        tab3.setPreferredSize(new java.awt.Dimension(150, 70));
        tab3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab3MouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MANAGE RESERVATION");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-reservation-50.png"))); // NOI18N

        javax.swing.GroupLayout tab3Layout = new javax.swing.GroupLayout(tab3);
        tab3.setLayout(tab3Layout);
        tab3Layout.setHorizontalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(49, 49, 49))
        );
        tab3Layout.setVerticalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(17, 17, 17))))
        );

        tab4.setBackground(new java.awt.Color(204, 0, 255));
        tab4.setPreferredSize(new java.awt.Dimension(150, 70));
        tab4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab4MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("MANAGE CLIENT");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-client-50.png"))); // NOI18N

        javax.swing.GroupLayout tab4Layout = new javax.swing.GroupLayout(tab4);
        tab4.setLayout(tab4Layout);
        tab4Layout.setHorizontalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab4Layout.setVerticalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
            .addComponent(tab2, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
            .addComponent(tab3, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
            .addComponent(tab4, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tab1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(tab2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(tab3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(tab4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
        );

        jPanel6.setLayout(new java.awt.CardLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel5.setText("ROOM");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Room No");

        jLabel7.setText("Room Type");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("BedType");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("jLabel9");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Amount");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "RoomID", "RoomType", "BedType", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtroom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A/C", "NON A/C" }));
        txtroom.setPreferredSize(new java.awt.Dimension(84, 25));

        txtbed.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtbed.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Single", "Double", " " }));
        txtbed.setMinimumSize(new java.awt.Dimension(84, 25));

        jButton1.setBackground(new java.awt.Color(204, 0, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 0, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("EDIT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 0, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("DELETE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 0, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("CLEAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtbed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtamount, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel5)))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(24, 24, 24)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtamount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton4))
                        .addGap(112, 112, 112))))
        );

        jPanel6.add(jPanel7, "card2");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "UserID", "ReserveNo", "Name", "Address", "MobileNo", "CheckIn", "CheckOut", "RoomType", "RoomNo", "BedType", "Amount", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jButton6.setBackground(new java.awt.Color(204, 0, 255));
        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("DELETE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(204, 0, 255));
        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("APPROVE");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel11.setText("Search");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel8, "card3");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "UserID", "Username", "Firstname", "Lastname", "Email", "Gender", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jButton5.setBackground(new java.awt.Color(204, 0, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Delete");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel22.setText("Search");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel9, "card4");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(204, 0, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(220, 220));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("?");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Reservations");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel16))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 0, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(220, 220));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("?");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Client");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel19))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel18)))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 0, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(220, 220));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 60)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("?");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Rooms");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jButton8.setBackground(new java.awt.Color(204, 0, 255));
        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("LOGOUT");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 0, 255));
        jLabel23.setText("HELLO ADMIN!!!");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8)
                    .addComponent(jLabel23))
                .addGap(123, 123, 123)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)))
                .addContainerGap(163, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel10, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseClicked
        // TODO add your handling code here:
      jPanel6.removeAll();
      jPanel6.add(jPanel7);
       jPanel6.repaint();
        jPanel6.revalidate();
    }//GEN-LAST:event_tab2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String roomID = jLabel9.getText();
        String roomType = txtroom.getSelectedItem().toString();
        String bedType = txtbed.getSelectedItem().toString();
        String amount = txtamount.getText();

        try {
            pat = con.prepareStatement("insert into room (RoomID,RoomType,BedType,Amount) values (?,?,?,?)");
            pat.setString(1, roomID);
            pat.setString(2, roomType);
            pat.setString(3, bedType);
            pat.setString(4, amount);
            pat.executeUpdate();
            JOptionPane.showMessageDialog(this, "ROOM ADDED");
            txtroom.setSelectedIndex(-1);
            txtbed.setSelectedIndex(-1);
            txtamount.setText("");
            autoID();
            Load_room();
            roomCount();

        } catch (SQLException ex) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         String roomID = jLabel9.getText();
        String roomType = txtroom.getSelectedItem().toString();
        String bedType =txtbed.getSelectedItem().toString();
        String amount = txtamount.getText();

        try {
            pat = con.prepareStatement("update room set RoomType = ?, BedType = ?, Amount = ? where RoomID = ? ");
            pat.setString(1, roomType);
            pat.setString(2, bedType);
            pat.setString(3, amount);
            pat.setString(4, roomID);
            pat.executeUpdate();
            JOptionPane.showMessageDialog(this, "ROOM EDITED");
           txtroom.setSelectedIndex(-1);
            txtbed.setSelectedIndex(-1);
            txtamount.setText("");
            autoID();
            Load_room();
            jButton1.setEnabled(true);

        } catch (SQLException ex) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
         String roomID = jLabel9.getText();

        try {
            pat = con.prepareStatement("DELETE FROM room where RoomID = ? ");

            pat.setString(1, roomID);
            pat.executeUpdate();
            JOptionPane.showMessageDialog(this, "ROOM DELETED");
            txtroom.setSelectedIndex(-1);
            txtbed.setSelectedIndex(-1);
            txtamount.setText("");
            autoID();
            Load_room();
            roomCount();
            jButton1.setEnabled(true);

        } catch (SQLException ex) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
            // TODO add your handling code here:
        txtroom.setSelectedIndex(-1);
        txtbed.setSelectedIndex(-1);
        txtamount.setText("");
        autoID();
        Load_room();
        jButton1.setEnabled(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
          d = (DefaultTableModel) jTable1.getModel();
        int selectIndex = jTable1.getSelectedRow();
        jLabel9.setText(d.getValueAt(selectIndex, 0).toString());
        txtroom.setSelectedItem(d.getValueAt(selectIndex, 1).toString());
        txtbed.setSelectedItem(d.getValueAt(selectIndex, 2).toString());
        txtamount.setText(d.getValueAt(selectIndex, 3).toString());

        jButton1.setEnabled(false);
    }//GEN-LAST:event_jTable1MouseClicked

    private void tab4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseClicked
        // TODO add your handling code here:
          jPanel6.removeAll();
      jPanel6.add(jPanel9);
       jPanel6.repaint();
        jPanel6.revalidate();
        
    }//GEN-LAST:event_tab4MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
             int selectedRow = jTable2.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "No row selected.");
        return;
    }
    String UserID = jTable2.getValueAt(selectedRow, 0).toString(); 
    String Username = jTable2.getValueAt(selectedRow, 1).toString();  
    String Firstname = jTable2.getValueAt(selectedRow, 2).toString(); 
    String Lastname = jTable2.getValueAt(selectedRow, 3).toString(); 
  String Email = jTable2.getValueAt(selectedRow, 4).toString(); 
  String Gender = jTable2.getValueAt(selectedRow, 5).toString(); 
  String Password = jTable2.getValueAt(selectedRow, 6).toString(); 
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
         int selectedRow = jTable2.getSelectedRow();

    // Check if a row is selected
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "No row selected.");
        return;
    }

    // Get the UserID from the selected row (assuming it's in the first column)
    String UserID = jTable2.getValueAt(selectedRow, 0).toString();

    // Confirm deletion from the user
    int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this user?", 
            "Delete Confirmation", JOptionPane.YES_NO_OPTION);

    // If the user confirms, proceed to delete
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            // Prepare the DELETE statement with the UserID as the parameter
            pat = con.prepareStatement("DELETE FROM user WHERE UserID = ?");

            // Set the UserID parameter
            pat.setString(1, UserID);

            // Execute the DELETE query
            int rowsAffected = pat.executeUpdate();

            if (rowsAffected > 0) {
                // Notify the user that the record has been deleted
                JOptionPane.showMessageDialog(this, "User deleted successfully.");

                // Reload the table data (or update the table as needed)
                Load_client();

                // Optionally, re-enable buttons or reset UI components
                jButton1.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error: User could not be deleted.");
            }

        } catch (SQLException ex) {
            // Handle SQL errors
            Logger.getLogger(room.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tab3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseClicked
        // TODO add your handling code here:
          jPanel6.removeAll();
      jPanel6.add(jPanel8);
       jPanel6.repaint();
        jPanel6.revalidate();
    }//GEN-LAST:event_tab3MouseClicked
private String selectedReservationID;
    private int previouslySelectedRow = -1;
    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
          int selectedRow = jTable3.getSelectedRow();

    // Ensure a row is selected
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "No row selected.");
        return;
    }

    // Get the ReserveID from the selected row
    String reserveID = (String) jTable3.getValueAt(selectedRow, 1); // Assuming ReserveID is in column 1
    if (reserveID == null || reserveID.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Selected row does not have a valid ReserveID.");
        return;
    }

    // Store the selected ReserveID for further use
    selectedReservationID = reserveID;

    // Debugging: Print the selected reservation details for confirmation
    System.out.println("Selected Reservation ID: " + reserveID);
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
          if (selectedReservationID == null || selectedReservationID.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Please select a reservation to delete.");
    return;
}

// Confirm deletion
int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this reservation?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
if (confirm != JOptionPane.YES_OPTION) {
    return; // User canceled
}

try {
    // Retrieve userID from the selected row (adjust the column index if needed)
    int selectedRow = jTable3.getSelectedRow();
    String selectedUserID = jTable3.getValueAt(selectedRow, 0).toString(); // Assuming userID is in column 0

    // Prepare the DELETE query with both ReserveID and userID
    pat = con.prepareStatement("DELETE FROM reservation WHERE ReserveID = ? AND userID = ?");
    pat.setString(1, selectedReservationID);
    pat.setString(2, selectedUserID); // Bind the userID to the query

    // Execute the delete query
    int rowsAffected = pat.executeUpdate();

    if (rowsAffected > 0) {
        JOptionPane.showMessageDialog(this, "Reservation deleted successfully.");
        Load_reservation(); // Refresh the table
        updateReservationCount();
    } else {
        JOptionPane.showMessageDialog(this, "Reservation could not be deleted.");
    }
} catch (SQLException ex) {
    JOptionPane.showMessageDialog(this, "Error deleting reservation: " + ex.getMessage());
}
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
      try {
        // Get the selected row index
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reservation to approve.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the ReserveID, RoomNo, and Check-in/Check-out dates from the selected row
        String selectedReserveID = jTable3.getValueAt(selectedRow, 1).toString(); // Assuming ReserveID is in column 1
        String roomNo = jTable3.getValueAt(selectedRow, 8).toString();  // Assuming Room No is in column 8
        String checkIn = jTable3.getValueAt(selectedRow, 5).toString(); // Assuming Check-In is in column 5
        String checkOut = jTable3.getValueAt(selectedRow, 6).toString(); // Assuming Check-Out is in column 6
        String selectedUserID = jTable3.getValueAt(selectedRow, 0).toString(); // Assuming userID is in column 0
        String currentStatus = jTable3.getValueAt(selectedRow, 11).toString(); // Assuming Status is in column 7

        // Check if the reservation is already approved
        if ("Approved".equals(currentStatus)) {
            JOptionPane.showMessageDialog(this, "This reservation is already approved.", "Already Approved", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convert dates to the proper format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date checkInDate = sdf.parse(checkIn);
        java.util.Date checkOutDate = sdf.parse(checkOut);

        // Prepare SQL query to check if the room is already booked for the selected dates (excluding the current reservation)
        pat = con.prepareStatement(
            "SELECT * FROM reservation WHERE roomNo = ? AND status = 'Pending' AND ( " +
            "(CheckIn BETWEEN ? AND ?) OR " + 
            "(CheckOut BETWEEN ? AND ?) OR " + 
            "(CheckIn <= ? AND CheckOut >= ?) ) AND ReserveID != ?" // Exclude the current reservation (ReserveID != ?)
        );

        // Set parameters for roomNo and the date ranges
        pat.setString(1, roomNo);
        pat.setDate(2, new java.sql.Date(checkInDate.getTime()));
        pat.setDate(3, new java.sql.Date(checkOutDate.getTime()));
        pat.setDate(4, new java.sql.Date(checkInDate.getTime()));
        pat.setDate(5, new java.sql.Date(checkOutDate.getTime()));
        pat.setDate(6, new java.sql.Date(checkInDate.getTime()));
        pat.setDate(7, new java.sql.Date(checkOutDate.getTime()));
        pat.setString(8, selectedReserveID); // Exclude the current reservation by ReserveID

        ResultSet rs = pat.executeQuery();

        // If a conflicting pending reservation exists, show an error message and return
        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Room is already booked for the selected dates.", "Room Conflict", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // If no conflict, update the reservation status
        pat = con.prepareStatement("UPDATE reservation SET status = ? WHERE ReserveID = ? AND userID = ?");
        pat.setString(1, "Approved");  // Change Pending to "Approved"
        pat.setString(2, selectedReserveID);  // Set the selected ReserveID
        pat.setString(3, selectedUserID);  // Set the selected userID
        pat.executeUpdate();

        // Show success message
        JOptionPane.showMessageDialog(this, "Reservation approved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Refresh the table or reload reservations
        Load_reservation();

    } catch (SQLException | ParseException ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error while updating reservation status.", "Error", JOptionPane.ERROR_MESSAGE);
    }  
    }//GEN-LAST:event_jButton7ActionPerformed
public void Search(){
      try {
        // Get the search query from the JTextField
        String searchQuery = jTextField1.getText().trim();

     
        // SQL query to search for the term in ReserveID, Name, or any other column
        String query = "SELECT * FROM reservation WHERE ReserveID LIKE ? OR Name LIKE ? OR userID LIKE ?";
        
        // Prepare the statement
        pat = con.prepareStatement(query);
        pat.setString(1, "%" + searchQuery + "%");  // Add wildcard for partial matches
        pat.setString(2, "%" + searchQuery + "%");
        pat.setString(3, "%" + searchQuery + "%");

        ResultSet rs = pat.executeQuery();

        // Get table model and clear existing rows
        d = (DefaultTableModel) jTable3.getModel();
        d.setRowCount(0);

        // Check if the ResultSet has any rows
        boolean hasResults = false;

        // Loop through the results and add to the table
        while (rs.next()) {
            hasResults = true;  // Mark as true if there are any results
            Vector<String> row = new Vector<>();
            row.add(rs.getString("userID"));
            row.add(rs.getString("ReserveID"));
            row.add(rs.getString("Name"));
            row.add(rs.getString("Address"));
            row.add(rs.getString("MobileNo"));
            row.add(rs.getString("CheckIn"));
            row.add(rs.getString("CheckOut"));
            row.add(rs.getString("roomType"));
            row.add(rs.getString("roomNo"));
            row.add(rs.getString("bedType"));
            row.add(rs.getString("amount"));
            row.add(rs.getString("status"));
            d.addRow(row);
        }

        // If no results were found, show a message
        if (!hasResults) {
            JOptionPane.showMessageDialog(this, "No reservations found for the given search query.", "No Results", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error while searching.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        Search();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void tab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseClicked
        // TODO add your handling code here:
        jPanel6.removeAll();
      jPanel6.add(jPanel10);
       jPanel6.repaint();
        jPanel6.revalidate();
    }//GEN-LAST:event_tab1MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        Login obj = new Login();
        obj.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton8ActionPerformed
public void Search2(){
      try {
        // Get the search query from the JTextField
        String searchQuery = jTextField2.getText().trim();

     
        // SQL query to search for the term in ReserveID, Name, or any other column
        String query = "SELECT * FROM user WHERE Username LIKE ? OR Firstname LIKE ? OR Lastname LIKE ? OR userID LIKE ?";
        
        // Prepare the statement
        pat = con.prepareStatement(query);
        pat.setString(1, "%" + searchQuery + "%");  // Add wildcard for partial matches
        pat.setString(2, "%" + searchQuery + "%");
        pat.setString(3, "%" + searchQuery + "%");
        pat.setString(4, "%" + searchQuery + "%");
        ResultSet rs = pat.executeQuery();

        // Get table model and clear existing rows
        d = (DefaultTableModel) jTable2.getModel();
        d.setRowCount(0);

        // Check if the ResultSet has any rows
        boolean hasResults = false;

        // Loop through the results and add to the table
        while (rs.next()) {
            hasResults = true;  // Mark as true if there are any results
            Vector<String> row = new Vector<>();
            row.add(rs.getString("userID"));
            row.add(rs.getString("Username"));
            row.add(rs.getString("Firstname"));
            row.add(rs.getString("Lastname"));
            row.add(rs.getString("Email"));
            row.add(rs.getString("Gender"));
            row.add(rs.getString("Password"));
            
            d.addRow(row);
        }

        // If no results were found, show a message
        if (!hasResults) {
            JOptionPane.showMessageDialog(this, "No users found for the given search query.", "No Results", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error while searching.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        Search2();
    }//GEN-LAST:event_jTextField2KeyReleased
public void Load_client() {
    try {
        // Use a parameterized query to prevent SQL injection
        pat = con.prepareStatement("SELECT * FROM user");
      
        ResultSet rs = pat.executeQuery();

        // Get table model and clear existing rows
        d = (DefaultTableModel) jTable2.getModel();
        d.setRowCount(0);

        while (rs.next()) {
            // Create a new row with values for the current reservation
            Vector<String> v2 = new Vector<>();
            v2.add(rs.getString("UserID"));
            v2.add(rs.getString("Username"));
            v2.add(rs.getString("Firstname"));
            v2.add(rs.getString("Lastname"));
            v2.add(rs.getString("Email"));
            v2.add(rs.getString("Gender"));
             v2.add(rs.getString("Password"));

            // Add the row to the table model
            d.addRow(v2);
        }
    } catch (SQLException ex) {
        Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error loading reservations: " + ex.getMessage());
    }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(finalDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(finalDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(finalDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(finalDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new finalDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab3;
    private javax.swing.JPanel tab4;
    private javax.swing.JTextField txtamount;
    private javax.swing.JComboBox<String> txtbed;
    private javax.swing.JComboBox<String> txtroom;
    // End of variables declaration//GEN-END:variables
}
