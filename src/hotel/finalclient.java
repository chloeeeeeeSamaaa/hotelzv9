/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hotel;
import java.awt.Image;
import java.net.URL;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.ZoneId;
/**
 *
 * @author USER
 */
public class finalclient extends javax.swing.JFrame {
 private String us;
    /**
     * Creates new form finalclient
     */
    public finalclient() {
        initComponents();
       
        Connect();
        autoID(Login.UID);
        RoomNo();
        Load_reservation();
         Load_client_INFO();
            jPanel2.removeAll();
      jPanel2.add(jPanel3);
       jPanel2.repaint();
        jPanel2.revalidate();
         
    }
Connection con;
    PreparedStatement pat;
    DefaultTableModel d;
    int sw_image = 1;
   


    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/hotel", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(room.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(room.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
private String autoID(int UID) {
    String newReserveID = "R001";  // Default ID if no reservations are found for the user
    try {
        // Fetch the last used ReserveID for the logged-in user
        pat = con.prepareStatement("SELECT ReserveID FROM reservation WHERE userID = ? ORDER BY ReserveID DESC LIMIT 1");
        pat.setInt(1, Login.UID);  // Use the logged-in user's ID
        ResultSet rs = pat.executeQuery();
        
        // Check if a record is returned
        if (rs.next()) {
            String lastID = rs.getString("ReserveID");
            // Assuming the ID format is like "R001", "R002", etc.
            int lastIDNum = Integer.parseInt(lastID.substring(1)); // Get numeric part of ID
            newReserveID = "R" + String.format("%03d", lastIDNum + 1);  // Increment ID and format it
        }
    } catch (SQLException ex) {
        Logger.getLogger(room.class.getName()).log(Level.SEVERE, null, ex);
    }
    return newReserveID;
}
  public void RoomNo() {
        try {
        pat = con.prepareStatement("SELECT DISTINCT RoomID FROM room");
        ResultSet rs = pat.executeQuery();
        txtro.removeAllItems();
        while (rs.next()) {
            txtro.addItem(rs.getString("RoomID"));
        }
    } catch (SQLException ex) {
        Logger.getLogger(room.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
  public void Load_reservation() {
    try {
        // Use a JOIN to fetch the user's name from the users table
        String query = """
                SELECT r.ReserveID, u.Firstname, r.Address, r.MobileNo, r.CheckIn, r.CheckOut, 
                       r.roomType, r.roomNo, r.bedType, r.amount, r.status
                FROM reservation r
                JOIN user u ON r.userID = u.userID
                WHERE r.userID = ?
                """;

        pat = con.prepareStatement(query);
        pat.setInt(1, Login.UID); // Fetch reservations for the current user
        ResultSet rs = pat.executeQuery();

        // Get table model and clear existing rows
        d = (DefaultTableModel) jTable1.getModel();
        d.setRowCount(0);

        while (rs.next()) {
            // Create a new row with values for the current reservation
            Vector<String> v2 = new Vector<>();
            v2.add(rs.getString("ReserveID"));
            v2.add(rs.getString("Firstname")); // Fetch the name from the users table
            v2.add(rs.getString("Address"));
            v2.add(rs.getString("MobileNo"));
            v2.add(rs.getString("CheckIn"));
            v2.add(rs.getString("CheckOut"));
            v2.add(rs.getString("roomType"));
            v2.add(rs.getString("roomNo"));
            v2.add(rs.getString("bedType"));
            v2.add(rs.getString("amount"));
            v2.add(rs.getString("status"));

            // Add the row to the table model
            d.addRow(v2);
        }
    } catch (SQLException ex) {
        Logger.getLogger(room.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Error loading reservations: " + ex.getMessage());
    }
}
  public void Load_client_INFO() { 
    try {
        // Use a parameterized query to prevent SQL injection
        String sql = "SELECT * FROM user WHERE UserID = ?";
        pat = con.prepareStatement(sql);
        pat.setInt(1, Login.UID); // Replace loggedInUserID with the actual variable holding the user ID
        
        ResultSet rs = pat.executeQuery();

        // Get table model and clear existing rows
        d = (DefaultTableModel) jTable2.getModel();
        d.setRowCount(0);

        while (rs.next()) {
            // Create a new row with values for the current user
            Vector<String> v2 = new Vector<>();
            
            v2.add(rs.getString("Username"));
          v2.add(rs.getString("Address"));
          v2.add(rs.getString("MobileNo"));
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
        JOptionPane.showMessageDialog(null, "Error loading client information: " + ex.getMessage());
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

        genderGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        tab1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tab2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tab3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tab4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        left = new javax.swing.JLabel();
        right = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        lblHomeImage = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        txtuname = new javax.swing.JTextField();
        txtfname = new javax.swing.JTextField();
        txtlname = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        rbtnFemale = new javax.swing.JRadioButton();
        rbtnMale = new javax.swing.JRadioButton();
        psword = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtm = new javax.swing.JTextField();
        txta = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtcheckin = new com.toedter.calendar.JDateChooser();
        txtcheckout = new com.toedter.calendar.JDateChooser();
        txtro = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1255, 660));

        jPanel1.setBackground(new java.awt.Color(204, 0, 255));
        jPanel1.setForeground(new java.awt.Color(204, 0, 255));
        jPanel1.setToolTipText("");

        tab1.setBackground(new java.awt.Color(204, 0, 255));
        tab1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("HOME");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home (2).png"))); // NOI18N
        jLabel7.setText("jLabel7");

        javax.swing.GroupLayout tab1Layout = new javax.swing.GroupLayout(tab1);
        tab1.setLayout(tab1Layout);
        tab1Layout.setHorizontalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab1Layout.setVerticalGroup(
            tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addGap(14, 14, 14))
        );

        tab2.setBackground(new java.awt.Color(204, 0, 255));
        tab2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab2MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MY RESERVATIONS");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/booking (2).png"))); // NOI18N
        jLabel9.setText("jLabel9");

        javax.swing.GroupLayout tab2Layout = new javax.swing.GroupLayout(tab2);
        tab2.setLayout(tab2Layout);
        tab2Layout.setHorizontalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab2Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        tab2Layout.setVerticalGroup(
            tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tab2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18))
        );

        tab3.setBackground(new java.awt.Color(204, 0, 255));
        tab3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab3MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PROFILE");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user.png"))); // NOI18N
        jLabel18.setText("jLabel18");

        javax.swing.GroupLayout tab3Layout = new javax.swing.GroupLayout(tab3);
        tab3.setLayout(tab3Layout);
        tab3Layout.setHorizontalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab3Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab3Layout.setVerticalGroup(
            tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(tab3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab4.setBackground(new java.awt.Color(204, 0, 255));
        tab4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab4MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ADD RESERVATIONS");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reserve.png"))); // NOI18N
        jLabel19.setText("jLabel19");

        javax.swing.GroupLayout tab4Layout = new javax.swing.GroupLayout(tab4);
        tab4.setLayout(tab4Layout);
        tab4Layout.setHorizontalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(50, 50, 50))
        );
        tab4Layout.setVerticalGroup(
            tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab4Layout.createSequentialGroup()
                .addGroup(tab4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19))
                    .addGroup(tab4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tab3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(tab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(tab2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(tab4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tab3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(861, 660));
        jPanel2.setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_CbtnLeft.png"))); // NOI18N
        left.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leftMouseClicked(evt);
            }
        });

        right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ic_CbtnRight.png"))); // NOI18N
        right.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rightMouseClicked(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(204, 0, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(null);

        lblHomeImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bd1d4d093c0ca703156989b49efea140 (3).jpg"))); // NOI18N
        jPanel8.add(lblHomeImage);
        lblHomeImage.setBounds(20, 20, 420, 280);

        jButton2.setBackground(new java.awt.Color(204, 0, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("LOGOUT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel20.setText("Welcome To Tempest Inn");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel21.setText("Your perfect stay starts right here. Tempest Inn is designed to offer a seamless and enjoyable booking experience,");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel22.setText("making it easier than ever to reserve your ideal room.");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setText("Explore Our Rooms:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel24.setText("Browse through the images above to view the room at Tempest Inn. Choose the room");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel25.setText("that suits your needs and style.");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(right)
                .addGap(41, 41, 41))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addContainerGap(210, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jLabel20))
                .addGap(12, 12, 12)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(left))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(right)))
                .addGap(18, 18, 18)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, "card2");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(850, 600));

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable1.setForeground(new java.awt.Color(204, 0, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ReserveID", "Name", "Address", "MobileNo", "CheckIn", "CheckOut", "Bedtype", "RoomNo", "RoomType", "Amount", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setRowHeight(40);
        jScrollPane1.setViewportView(jTable1);

        jButton4.setBackground(new java.awt.Color(204, 0, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, "card3");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(204, 0, 255));
        jPanel5.setToolTipText("");
        jPanel5.setPreferredSize(new java.awt.Dimension(850, 600));
        jPanel5.setLayout(null);

        jTable2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jTable2.setForeground(new java.awt.Color(204, 0, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Username", "Address", "MobileNo", "Firstname", "Lasrname", "Email", "Gender", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.setRowHeight(40);
        jTable2.setRowSelectionAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel5.add(jScrollPane2);
        jScrollPane2.setBounds(6, 6, 1016, 68);

        txtuname.setForeground(new java.awt.Color(204, 0, 255));
        txtuname.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanel5.add(txtuname);
        txtuname.setBounds(6, 106, 230, 29);

        txtfname.setForeground(new java.awt.Color(204, 0, 255));
        txtfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfnameActionPerformed(evt);
            }
        });
        jPanel5.add(txtfname);
        txtfname.setBounds(6, 301, 230, 30);

        txtlname.setForeground(new java.awt.Color(204, 0, 255));
        jPanel5.add(txtlname);
        txtlname.setBounds(6, 369, 230, 30);

        txtemail.setForeground(new java.awt.Color(204, 0, 255));
        jPanel5.add(txtemail);
        txtemail.setBounds(6, 431, 230, 30);

        genderGroup.add(rbtnFemale);
        rbtnFemale.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbtnFemale.setForeground(new java.awt.Color(204, 0, 255));
        rbtnFemale.setText("Female");
        jPanel5.add(rbtnFemale);
        rbtnFemale.setBounds(138, 473, 98, 25);

        rbtnMale.setBackground(new java.awt.Color(255, 255, 255));
        genderGroup.add(rbtnMale);
        rbtnMale.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbtnMale.setForeground(new java.awt.Color(204, 0, 255));
        rbtnMale.setText("Male");
        jPanel5.add(rbtnMale);
        rbtnMale.setBounds(6, 473, 98, 25);

        psword.setForeground(new java.awt.Color(204, 0, 255));
        psword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pswordActionPerformed(evt);
            }
        });
        jPanel5.add(psword);
        psword.setBounds(6, 530, 230, 30);

        jButton3.setBackground(new java.awt.Color(204, 0, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("EDIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3);
        jButton3.setBounds(6, 578, 72, 27);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 255));
        jLabel5.setText("Username");
        jLabel5.setToolTipText("");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(6, 80, 70, 20);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 255));
        jLabel8.setText("Firstname");
        jLabel8.setToolTipText("");
        jPanel5.add(jLabel8);
        jLabel8.setBounds(6, 275, 72, 20);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 0, 255));
        jLabel10.setText("Lastname");
        jLabel10.setToolTipText("");
        jPanel5.add(jLabel10);
        jLabel10.setBounds(6, 343, 70, 20);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 255));
        jLabel11.setText("Email");
        jLabel11.setToolTipText("");
        jPanel5.add(jLabel11);
        jLabel11.setBounds(6, 405, 43, 20);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 0, 255));
        jLabel12.setText("Password");
        jLabel12.setToolTipText("");
        jPanel5.add(jLabel12);
        jLabel12.setBounds(6, 504, 62, 20);

        txtm.setForeground(new java.awt.Color(204, 0, 255));
        jPanel5.add(txtm);
        txtm.setBounds(6, 233, 230, 30);

        txta.setForeground(new java.awt.Color(204, 0, 255));
        txta.setMinimumSize(new java.awt.Dimension(64, 30));
        jPanel5.add(txta);
        txta.setBounds(6, 167, 230, 30);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 0, 255));
        jLabel13.setText("Address");
        jLabel13.setToolTipText("");
        jPanel5.add(jLabel13);
        jLabel13.setBounds(6, 141, 70, 20);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 0, 255));
        jLabel14.setText("MobileNo");
        jLabel14.setToolTipText("");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(6, 207, 70, 20);

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dcz916l-14684120-a141-478b-b3d1-e0f9ee481909.gif"))); // NOI18N
        jLabel26.setText("jLabel26");
        jPanel5.add(jLabel26);
        jLabel26.setBounds(280, 80, 500, 490);

        jPanel2.add(jPanel5, "card4");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setForeground(new java.awt.Color(204, 0, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(850, 600));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 255));
        jLabel6.setText("RESERVATION");

        txtro.setBackground(new java.awt.Color(204, 0, 255));
        txtro.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(204, 0, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Reserve Now");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 0, 255));
        jLabel15.setText("CheckIn");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 0, 255));
        jLabel16.setText("CheckOut");

        jLabel17.setBackground(new java.awt.Color(204, 0, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 0, 255));
        jLabel17.setText("Choose Room");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtcheckout, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                        .addComponent(txtcheckin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtro, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17))
                .addGap(260, 260, 260))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(jButton1))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(281, 281, 281)
                        .addComponent(jLabel6)))
                .addContainerGap(445, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcheckin, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtcheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(8, 8, 8)
                .addComponent(txtro, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel6, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tab1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab1MouseClicked
        // TODO add your handling code here:
        jPanel2.removeAll();
      jPanel2.add(jPanel3);
       jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_tab1MouseClicked

    private void tab2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab2MouseClicked
        // TODO add your handling code here:
         jPanel2.removeAll();
      jPanel2.add(jPanel4);
       jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_tab2MouseClicked

    private void tab3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab3MouseClicked
        // TODO add your handling code here:
        jPanel2.removeAll();
      jPanel2.add(jPanel5);
       jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_tab3MouseClicked

    private void tab4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab4MouseClicked
        // TODO add your handling code here:
        jPanel2.removeAll();
      jPanel2.add(jPanel6);
       jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_tab4MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   String reserveID = autoID(Login.UID);

    // Validate inputs
    if (txtcheckin.getDate() == null || txtcheckout.getDate() == null || 
        txtro.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields", "Missing Information", JOptionPane.WARNING_MESSAGE);
        return;
    }

    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
    String StartDate = df1.format(txtcheckin.getDate());
    String EndDate = df1.format(txtcheckout.getDate());

    String roomNo = txtro.getSelectedItem().toString(); // Room number selected by the user

    // Validate that Check-In date is today or later
    LocalDate today = LocalDate.now();
    LocalDate checkInDate = txtcheckin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate checkOutDate = txtcheckout.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    if (checkInDate.isBefore(today)) {
        JOptionPane.showMessageDialog(this, "Check-in date cannot be before today's date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Check if checkout date is before or the same as check-in date
    if (!checkOutDate.isAfter(checkInDate)) {
        JOptionPane.showMessageDialog(this, "Check-out date must be after the check-in date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        // Fetch the logged-in user's details (name, address, and mobile)
        String name = null, address = null, mobile = null;
        pat = con.prepareStatement("SELECT Firstname, Address, MobileNo FROM user WHERE userID = ?");
        pat.setInt(1, Login.UID);
        ResultSet rs = pat.executeQuery();
        if (rs.next()) {
            name = rs.getString("Firstname");
            address = rs.getString("Address");
            mobile = rs.getString("MobileNo");
        }

        // Ensure user's details are found
        if (name == null || address == null || mobile == null) {
            JOptionPane.showMessageDialog(this, "User details not found. Please ensure your profile is complete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fetch room details (room type, bed type, and amount)
        String roomType = null, bedType = null;
        double roomAmountPerNight = 0;
        pat = con.prepareStatement("SELECT roomType, bedType, Amount FROM room WHERE roomID = ?");
        pat.setString(1, roomNo);
        rs = pat.executeQuery();
        if (rs.next()) {
            roomType = rs.getString("roomType");
            bedType = rs.getString("bedType");
            roomAmountPerNight = rs.getDouble("Amount");
        }
        if (roomType == null || bedType == null) {
            JOptionPane.showMessageDialog(this, "Room details not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate total amount based on the number of nights
        long diffInMillis = txtcheckout.getDate().getTime() - txtcheckin.getDate().getTime();
        long numberOfNights = diffInMillis / (1000 * 60 * 60 * 24); // Convert milliseconds to days
        double totalAmount = numberOfNights * roomAmountPerNight;

        // Validate room availability
        pat = con.prepareStatement(
            "SELECT * FROM reservation WHERE roomNo = ? AND ( " +
            "(? BETWEEN CheckIn AND CheckOut) OR " +   // Check-In overlaps
            "(? BETWEEN CheckIn AND CheckOut) OR " +   // Check-Out overlaps
            "(CheckIn BETWEEN ? AND ?) OR " +         // Existing reservation within new reservation
            "(CheckOut BETWEEN ? AND ?) " +          // Existing reservation overlaps new reservation
            ")"
        );

        pat.setString(1, roomNo);
        pat.setString(2, StartDate);
        pat.setString(3, EndDate);
        pat.setString(4, StartDate);
        pat.setString(5, EndDate);
        pat.setString(6, StartDate);
        pat.setString(7, EndDate);

        rs = pat.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Room is unavailable for the selected dates. Please choose a different date or room.", "Room Unavailable", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert the reservation
        pat = con.prepareStatement(
            "INSERT INTO reservation (ReserveID, userID, Name, Address, MobileNo, CheckIn, CheckOut, bedType, roomNo, roomType, amount, status) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
        );

        pat.setString(1, reserveID);
        pat.setInt(2, Login.UID);
        pat.setString(3, name);
        pat.setString(4, address);
        pat.setString(5, mobile);
        pat.setString(6, StartDate);
        pat.setString(7, EndDate);
        pat.setString(8, bedType);
        pat.setString(9, roomNo);
        pat.setString(10, roomType);
        pat.setDouble(11, totalAmount);
        pat.setString(12, "Approved");

        pat.executeUpdate();

        JOptionPane.showMessageDialog(this, "Reservation added successfully.");
        Load_reservation();

        // Open payment frame
        payment paymentFrame = new payment();
        paymentFrame.setTotalAmount(totalAmount);
        paymentFrame.setVisible(true);
        dispose();

    } catch (SQLException ex) {
        Logger.getLogger(room.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void leftMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leftMouseClicked
      // TODO add your handling code here:
     switchImage();
    }//GEN-LAST:event_leftMouseClicked

    private void rightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightMouseClicked
        // TODO add your handling code here:
    switchImageBack();
    }//GEN-LAST:event_rightMouseClicked
private String[] imagePaths = {
    "/icons/bd1d4d093c0ca703156989b49efea140 (3).jpg",
    "/icons/ej-anime-hotel-uzaki-room-photo (1).jpg", 
    "/icons/c3ccf707970df8e9966da203dd3bfbeb (1).jpg",
    "/icons/cc0d2bc3db0050dc990d7b96aa17e387.jpg",
    "/icons/s-main-44 (1).jpg",
    "/icons/anime-theme-hotels-4.jpg"

};
private int currentImageIndex = 0;
    private void switchImage() {
    // Get the current image path
    String currentPath = imagePaths[currentImageIndex];
    
    // Load the image using getResource
    URL resource = getClass().getResource(currentPath);

    if (resource != null) {
        // Create ImageIcon and scale the image
        ImageIcon ii = new ImageIcon(resource);
        Image image = ii.getImage().getScaledInstance(lblHomeImage.getWidth(), lblHomeImage.getHeight(), Image.SCALE_SMOOTH);
        lblHomeImage.setIcon(new ImageIcon(image));
        
        // Cycle to the next image
        currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
    } else {
        System.err.println("Resource not found: " + currentPath);
    }
}
    private void switchImageBack() {
    // Get the current image path
    String currentPath = imagePaths[currentImageIndex];
    
    // Load the image using getResource
    URL resource = getClass().getResource(currentPath);

    if (resource != null) {
        // Create ImageIcon and scale the image
        ImageIcon ii = new ImageIcon(resource);
        Image image = ii.getImage().getScaledInstance(lblHomeImage.getWidth(), lblHomeImage.getHeight(), Image.SCALE_SMOOTH);
        lblHomeImage.setIcon(new ImageIcon(image));
        
        // Cycle to the previous image
        currentImageIndex = (currentImageIndex - 1 + imagePaths.length) % imagePaths.length;
    } else {
        System.err.println("Resource not found: " + currentPath);
    }
}
    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
 int selectedRow = jTable2.getSelectedRow();
    
    if (selectedRow != -1) { // Ensure a row is actually selected
        try {
            // Retrieve the table model
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            
            // Fetch data from the selected row
            String username = model.getValueAt(selectedRow, 0).toString();
            String address = model.getValueAt(selectedRow, 1).toString();
            String mobileNo = model.getValueAt(selectedRow, 2).toString();
            String firstname = model.getValueAt(selectedRow, 3).toString();
            String lastname = model.getValueAt(selectedRow, 4).toString();
            String email = model.getValueAt(selectedRow, 5).toString();
            String gender = model.getValueAt(selectedRow, 6).toString();
            String password = model.getValueAt(selectedRow, 7).toString();

            // Set the values in your components
            txtuname.setText(username);
            txta.setText(address);
            txtm.setText(mobileNo);
            txtfname.setText(firstname);
            txtlname.setText(lastname);
            txtemail.setText(email);

            // Set the gender radio button
            if ("Male".equalsIgnoreCase(gender)) {
                rbtnMale.setSelected(true);
                rbtnFemale.setSelected(false);
            } else if ("Female".equalsIgnoreCase(gender)) {
                rbtnFemale.setSelected(true);
                rbtnMale.setSelected(false);
            } else {
                rbtnMale.setSelected(false);
                rbtnFemale.setSelected(false);
            }

            psword.setText(password);

        } catch (Exception e) {
            // Handle any unexpected exceptions
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "No row selected!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jTable2MouseClicked

    private void txtfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfnameActionPerformed

    private void pswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pswordActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       int selectedRow = jTable2.getSelectedRow();

    if (selectedRow != -1) { // Ensure a row is selected
        try {
            // Retrieve data from text fields or other components
            String userID = jTable2.getValueAt(selectedRow, 0).toString(); // UserID is usually not editable
            String username = txtuname.getText().trim();
            String address = txta.getText().trim();
            String mobile = txtm.getText().trim();
            String firstname = txtfname.getText().trim();
            String lastname = txtlname.getText().trim();
            String email = txtemail.getText().trim();
            String gender;
            
            if (rbtnMale.isSelected()) {
                gender = "Male";
            } else if (rbtnFemale.isSelected()) {
                gender = "Female";
            } else {
                JOptionPane.showMessageDialog(this, "Please select a gender.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop execution if no gender is selected
            }
            
            String password = psword.getText().trim();
            
            // Validate input data
            if (username.isEmpty() || address.isEmpty() || mobile.isEmpty() || firstname.isEmpty() || 
                lastname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Prepare and execute the SQL update query
            String sql = "UPDATE user SET Username = ?, Address = ?, MobileNo = ?, Firstname = ?, Lastname = ?, Email = ?, Gender = ?, Password = ? WHERE UserID = ?";
            pat = con.prepareStatement(sql);
            pat.setString(1, username);
            pat.setString(2, address);
            pat.setString(3, mobile);
            pat.setString(4, firstname);
            pat.setString(5, lastname);
            pat.setString(6, email);
            pat.setString(7, gender);
            pat.setString(8, password);
            pat.setInt(9, Login.UID); // Use the UserID as the condition

            // Execute the query and check the result
            int rowsUpdated = pat.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
                Load_client_INFO(); // Refresh the table to show updated data
            } else {
                JOptionPane.showMessageDialog(this, "No changes were made to the record.");
            }

            // Clear the text fields and reset the radio buttons
            clearFields();

        } catch (SQLException ex) {
            Logger.getLogger(finalDashboard.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error updating record: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
       int selectedRow = jTable1.getSelectedRow();

    if (selectedRow != -1) { // Ensure a row is selected
        try {
            // Retrieve the reserveID and userID from the correct table columns
            String reserveID = jTable1.getValueAt(selectedRow, 0).toString(); // Adjust column index for reserveID
            int userID = Login.UID; // Use the logged-in user's ID directly

            // Confirm the deletion with the user
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this reservation?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Prepare the DELETE SQL statement
                    pat = con.prepareStatement("DELETE FROM reservation WHERE reserveID = ? AND userID = ?");
                    pat.setString(1, reserveID);
                    pat.setInt(2, userID);

                    // Execute the DELETE operation
                    int rowsAffected = pat.executeUpdate();

                    if (rowsAffected > 0) {
                        // Show success message
                        JOptionPane.showMessageDialog(this, "Reservation deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Clear the form fields
                        jLabel12.setText("");
                        txtcheckin.setDate(null);
                        txtcheckout.setDate(null);
                        txtro.setSelectedIndex(-1);

                        // Refresh the data in the table
                        Load_reservation();

                        // Enable the add button
                        jButton1.setEnabled(true);
                    } else {
                        // Show error if no rows were affected
                        JOptionPane.showMessageDialog(this, "No reservation found with the provided ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(room.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Error deleting reservation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error processing data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // Show error if no row is selected
        JOptionPane.showMessageDialog(this, "Please select a reservation to delete.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Login obj = new Login();
        obj.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
private void clearFields() {
    txtuname.setText("");
    txtfname.setText("");
    txtlname.setText("");
    txtemail.setText("");
    txta.setText("");
    txtm.setText("");
    psword.setText("");
    rbtnMale.setSelected(false);
    rbtnFemale.setSelected(false);
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
            java.util.logging.Logger.getLogger(finalclient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(finalclient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(finalclient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(finalclient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup genderGroup;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblHomeImage;
    private javax.swing.JLabel left;
    private javax.swing.JTextField psword;
    private javax.swing.JRadioButton rbtnFemale;
    private javax.swing.JRadioButton rbtnMale;
    private javax.swing.JLabel right;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab3;
    private javax.swing.JPanel tab4;
    private javax.swing.JTextField txta;
    private com.toedter.calendar.JDateChooser txtcheckin;
    private com.toedter.calendar.JDateChooser txtcheckout;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtfname;
    private javax.swing.JTextField txtlname;
    private javax.swing.JTextField txtm;
    private javax.swing.JComboBox<String> txtro;
    private javax.swing.JTextField txtuname;
    // End of variables declaration//GEN-END:variables
}
