package View;

import Controller.connectDatabase;
import Model.HoaDonQL;
import Model.orderDV;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.Buffer;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class vi_HoaDon extends javax.swing.JFrame {

    public static ArrayList<HoaDonQL> arrayListHoaDon;
    ArrayList<HoaDonQL> listShift = new ArrayList<>();
    static Vector rowTableNL = new Vector();
    static Vector dataTableNL = new Vector();
    public static ArrayList<orderDV> arrayListOrderDV;
    ArrayList<orderDV> listShift2 = new ArrayList<>();
    static Vector rowTableOD = new Vector();
    static Vector dataTableOD = new Vector();

    HoaDonQL QLNL = new HoaDonQL();
    public static String sqlHoaDon = "SELECT * FROM HoaDon order by MaHD asc";

    orderDV QLOR = new orderDV();
    public static String sqlOrder = "SELECT * FROM OrderDV order by MaHD asc";

    String curentID = "";
    public static Statement st;
    public static PreparedStatement ps;

    public vi_HoaDon() throws SQLException, ClassNotFoundException {
        initComponents();
        setLocation(60, 40);
        setTitle("Quản Lý Hóa Đơn");
        comboboxMaNV();
        comboboxMaNL();
        comboboxNuocNgot();
        comboboxTrangMieng();
        comboboxTraSua();
        comboboxCafe();
        comboboxCMaHDHD();
        comboboxCMamon();
        comboboxNguoiTT();
        cbMaNL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String sql = "SELECT * FROM NguyenLieu WHERE MaNL = ?";
                    PreparedStatement statement = connectDatabase.TaoKetNoi().prepareStatement(sql);
                    statement.setString(1, (String) cbMaNL.getSelectedItem());
                    ResultSet set = statement.executeQuery();
                    while (set.next()) {
                        txtDonGia.setText(set.getString("DonGia"));
                    }
                } catch (Exception ex) {

                }
            }
        });
        cbCF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String sql = "SELECT * FROM DichVu WHERE TenMon = ?";
                    PreparedStatement statement = connectDatabase.TaoKetNoi().prepareStatement(sql);
                    statement.setString(1, (String) cbCF.getSelectedItem());
                    ResultSet set = statement.executeQuery();
                    while (set.next()) {
                        txtDonGia2.setText(set.getString("DonGia"));
                    }
                } catch (Exception ex) {

                }
            }
        });
        cbTS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String sql = "SELECT * FROM DichVu WHERE TenMon = ?";
                    PreparedStatement statement = connectDatabase.TaoKetNoi().prepareStatement(sql);
                    statement.setString(1, (String) cbTS.getSelectedItem());
                    ResultSet set = statement.executeQuery();
                    while (set.next()) {
                        txtDonGia2.setText(set.getString("DonGia"));
                    }
                } catch (Exception ex) {

                }
            }
        });
        cbTM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String sql = "SELECT * FROM DichVu WHERE TenMon = ?";
                    PreparedStatement statement = connectDatabase.TaoKetNoi().prepareStatement(sql);
                    statement.setString(1, (String) cbTM.getSelectedItem());
                    ResultSet set = statement.executeQuery();
                    while (set.next()) {
                        txtDonGia2.setText(set.getString("DonGia"));
                    }
                } catch (Exception ex) {

                }
            }
        });
        cbNN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String sql = "SELECT * FROM DichVu WHERE TenMon = ?";
                    PreparedStatement statement = connectDatabase.TaoKetNoi().prepareStatement(sql);
                    statement.setString(1, (String) cbNN.getSelectedItem());
                    ResultSet set = statement.executeQuery();
                    while (set.next()) {
                        txtDonGia2.setText(set.getString("DonGia"));
                    }
                } catch (Exception ex) {

                }
            }
        });
        btnThanhToan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql = "SELECT SUM(CAST(SoLuong as int)*CAST(DonGia as int)) as ThanhTien FROM OrderDV WHERE MaHD = MaHD ORDER BY ThanhTien";
                try {
                    st = connectDatabase.TaoKetNoi().createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        txtTTOD.setText(rs.getString("ThanhTien"));
                    }
                    st.close();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        creatArraylistHoaDon(sqlHoaDon);
        loadTableDataHoaDon();
        creatArraylistOrder(sqlOrder);
        loadTableDataOrder();
    }

    static void creatArraylistHoaDon(String sql) throws SQLException, ClassNotFoundException {
        Connection conn;
        Statement stmt;
        arrayListHoaDon = new ArrayList<>();
        conn = connectDatabase.TaoKetNoi();
        stmt = conn.createStatement();
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { //Duyêt từng dòng trong sql
                arrayListHoaDon.add(new HoaDonQL( //goi phương thức khởi tạo nằm trong model nhân viên
                        rs.getString("MaHD"),
                        rs.getString("MaNL").trim(),
                        rs.getString("MaNhanVien"),
                        rs.getString("SoLuong"),
                        rs.getString("DonGia"),
                        rs.getString("ThanhTien")
                ));
            }
        }
        stmt.close();
        conn.close();
    }

    public void loadTableDataHoaDon() {
        dataTableNL = new Vector();
        for (int i = 0; i < arrayListHoaDon.size(); i++) {
            Vector temp = new Vector();
            temp.add(arrayListHoaDon.get(i).getMaHD());
            temp.add(arrayListHoaDon.get(i).getMaNL());
            temp.add(arrayListHoaDon.get(i).getMaNhanVien());
            temp.add(arrayListHoaDon.get(i).getSoLuong());
            temp.add(arrayListHoaDon.get(i).getDonGia());
            temp.add(arrayListHoaDon.get(i).getThanhTien());
            dataTableNL.add(temp);
        }
        rowTableNL = new Vector();
        rowTableNL.add("Mã Hóa Đơn");
        rowTableNL.add("Mã Nguyên Liệu");
        rowTableNL.add("Mã Nhân Viên");
        rowTableNL.add("Số Lượng");
        rowTableNL.add("Đơn Giá");
        rowTableNL.add("Thành tiền");
        DefaultTableModel model = new DefaultTableModel(dataTableNL, rowTableNL);
        tbHoaDon.setModel(model);
    }

    static void creatArraylistOrder(String sql2) throws SQLException, ClassNotFoundException {
        Connection conn;
        Statement stmt;
        arrayListOrderDV = new ArrayList<>();
        conn = connectDatabase.TaoKetNoi();
        stmt = conn.createStatement();
        try (ResultSet rs2 = stmt.executeQuery(sql2)) {
            while (rs2.next()) { 
                arrayListOrderDV.add(new orderDV(
                        rs2.getString("MaHD"),
                        rs2.getString("MaMon"),
                        rs2.getString("MaNhanVien"),
                        rs2.getString("TenMon"),
                        rs2.getString("SoLuong"),
                        rs2.getString("DonGia")
                ));
            }
        }
        stmt.close();
        conn.close();
    }

    public void loadTableDataOrder() {
        dataTableOD = new Vector();
        for (int i = 0; i < arrayListOrderDV.size(); i++) {
            Vector temp = new Vector();
            temp.add(arrayListOrderDV.get(i).getMaHD());
            temp.add(arrayListOrderDV.get(i).getMaMon());
            temp.add(arrayListOrderDV.get(i).getMaNhanVien());
            temp.add(arrayListOrderDV.get(i).getTenMon());
            temp.add(arrayListOrderDV.get(i).getSoLuong());
            temp.add(arrayListOrderDV.get(i).getDonGia());
            dataTableOD.add(temp);
        }
        rowTableOD = new Vector();
        rowTableOD.add("Mã hóa đơn");
        rowTableOD.add("Mã món");
        rowTableOD.add("Nhân Viên TT");
        rowTableOD.add("Tên món");
        rowTableOD.add("Số Lượng");
        rowTableOD.add("Đơn Giá");
        DefaultTableModel model = new DefaultTableModel(dataTableOD, rowTableOD);
        tbOrder.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        cbMaNL = new javax.swing.JComboBox();
        cbMaNV = new javax.swing.JComboBox();
        txtDonGia = new javax.swing.JTextField();
        txtThanhTien = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbHoaDon = new javax.swing.JTable();
        JsnSL = new javax.swing.JSpinner();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        btnB01 = new javax.swing.JButton();
        btnB02 = new javax.swing.JButton();
        btnB03 = new javax.swing.JButton();
        btnB04 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cbCF = new javax.swing.JComboBox<>();
        cbTS = new javax.swing.JComboBox<>();
        cbTM = new javax.swing.JComboBox<>();
        cbNN = new javax.swing.JComboBox<>();
        cbSL1 = new javax.swing.JComboBox<>();
        cbSL2 = new javax.swing.JComboBox<>();
        cbSL3 = new javax.swing.JComboBox<>();
        cbSL4 = new javax.swing.JComboBox<>();
        btnThemDV = new javax.swing.JButton();
        btnThem3 = new javax.swing.JButton();
        btnThem2 = new javax.swing.JButton();
        btnThem4 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbOrder = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtBanSo = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        txtTTOD = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cbMaHDHD = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cbNguoiTT = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtDonGia2 = new javax.swing.JTextField();
        cbMaMonOD = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenu11 = new javax.swing.JMenu();
        jMenu12 = new javax.swing.JMenu();
        jMenu13 = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("jMenu5");

        jMenuItem1.setText("jMenuItem1");

        jMenu7.setText("File");
        jMenuBar3.add(jMenu7);

        jMenu8.setText("Edit");
        jMenuBar3.add(jMenu8);

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.lightGray, null, null));

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 204, 255)));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("HÓA ĐƠN THANH TOÁN");

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton18.setText("ĐĂNG XUẤT");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(470, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(135, 135, 135)
                .addComponent(jButton18)
                .addGap(63, 63, 63)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton18))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel2.setText("MÃ HÓA ĐƠN : ");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel3.setText("MÃ NGUYÊN LIỆU : ");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel4.setText("MÃ NHÂN VIÊN : ");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel6.setText("SỐ LƯỢNG : ");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel7.setText("ĐƠN GIÁ : ");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel8.setText("THÀNH TIỀN : ");

        txtMaHD.setEnabled(false);

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        btnLamMoi.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconlammoi.png"))); // NOI18N
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconthem.png"))); // NOI18N
        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconsua.png"))); // NOI18N
        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icondelete.png"))); // NOI18N
        btnXoa.setText("XÓA");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTimKiem.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconsearch.png"))); // NOI18N
        btnTimKiem.setText("TÌM KIẾM");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addComponent(btnTimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbHoaDon);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 304, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 17, Short.MAX_VALUE))))
        );

        JsnSL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JsnSLStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(27, 27, 27)
                                .addComponent(cbMaNV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbMaNL, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(145, 145, 145)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDonGia)
                            .addComponent(txtThanhTien)
                            .addComponent(JsnSL, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(570, 570, 570))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JsnSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(cbMaNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(cbMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("View_Hóa Đơn", jPanel6);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 255), new java.awt.Color(102, 102, 255)));

        btnB01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        btnB01.setText("B01");
        btnB01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnB01ActionPerformed(evt);
            }
        });

        btnB02.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        btnB02.setText("B02");
        btnB02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnB02ActionPerformed(evt);
            }
        });

        btnB03.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        btnB03.setText("B03");
        btnB03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnB03ActionPerformed(evt);
            }
        });

        btnB04.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        btnB04.setText("B04");
        btnB04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnB04ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton5.setText("B05");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton7.setText("B06");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton10.setText("B07");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton11.setText("B08");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton12.setText("B09");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton13.setText("B10");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton14.setText("B11");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton15.setText("B12");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton16.setText("B13");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconchonban.png"))); // NOI18N
        jButton17.setText("B14");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnB03, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnB01, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnB02, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(btnB04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnB02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnB01, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnB03, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnB04, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton7))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton13))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton14)
                    .addComponent(jButton15))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16)
                    .addComponent(jButton17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Chọn bàn", jPanel10);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText("Chọn dịch vụ");

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel10.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        jLabel10.setText("Cafe");

        jLabel11.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        jLabel11.setText("Trà sữa");

        jLabel12.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        jLabel12.setText("Tráng miệng");

        jLabel13.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        jLabel13.setText("Nước ngọt");

        jLabel14.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        jLabel14.setText("Số lượng");

        jLabel15.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        jLabel15.setText("Số lượng");

        jLabel16.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        jLabel16.setText("Số lượng");

        jLabel17.setFont(new java.awt.Font("Cambria Math", 0, 14)); // NOI18N
        jLabel17.setText("Số lượng");

        cbCF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCFActionPerformed(evt);
            }
        });

        cbSL1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cbSL1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSL1ActionPerformed(evt);
            }
        });

        cbSL2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        cbSL3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cbSL3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSL3ActionPerformed(evt);
            }
        });

        cbSL4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        btnThemDV.setText("THÊM");
        btnThemDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDVActionPerformed(evt);
            }
        });

        btnThem3.setText("THÊM");
        btnThem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem3ActionPerformed(evt);
            }
        });

        btnThem2.setText("THÊM");
        btnThem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem2ActionPerformed(evt);
            }
        });

        btnThem4.setText("THÊM");
        btnThem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbTS, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCF, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbTM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbNN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(cbSL4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(cbSL3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(cbSL2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(cbSL1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(btnThem2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemDV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel14)
                        .addComponent(cbCF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbSL1)
                    .addComponent(btnThemDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel15)
                        .addComponent(cbTS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbSL2)
                    .addComponent(btnThem2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbSL3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel17)
                            .addComponent(cbSL4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThem4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cbNN))
                .addContainerGap())
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bảng hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 13))); // NOI18N

        tbOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbOrder);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tính tiền", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 13))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel18.setText("Bàn số : ");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel19.setText("Tổng tiền thanh toán");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel20.setText("Khuyến mãi");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel21.setText("Thuế (VAT)");

        txtBanSo.setEnabled(false);

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconin.png"))); // NOI18N
        jButton2.setText("IN HÓA ĐƠN");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconHoaDon2.png"))); // NOI18N
        jButton4.setText("LƯU HÓA ĐƠN");

        btnThanhToan.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconthanhtoan.png"))); // NOI18N
        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/iconxoa.png"))); // NOI18N
        jButton6.setText("XÓA HÓA ĐƠN");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        txtTTOD.setEnabled(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBanSo)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4)
                            .addComponent(txtTTOD)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtBanSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTTOD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnThanhToan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        jLabel22.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel22.setText("Mã Hóa Đơn");

        cbMaHDHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMaHDHDActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel24.setText("Mã món : ");

        jLabel23.setText("Người thanh toán : ");

        jLabel5.setText("Đơn giá : ");

        txtDonGia2.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbMaHDHD, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMaMonOD, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbNguoiTT, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDonGia2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel22)
                    .addComponent(cbMaHDHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(cbNguoiTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtDonGia2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMaMonOD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Order_Hóa Đơn", jPanel7);

        jMenu1.setText("File");

        jMenu9.setText("File");
        jMenu1.add(jMenu9);

        jMenu6.setText("Open");
        jMenu1.add(jMenu6);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu10.setText("View");
        jMenuBar1.add(jMenu10);

        jMenu11.setText("Insert");
        jMenuBar1.add(jMenu11);

        jMenu12.setText("Run");
        jMenuBar1.add(jMenu12);

        jMenu13.setText("Help");
        jMenuBar1.add(jMenu13);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbMaHDHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMaHDHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbMaHDHDActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        txtTTOD.setText("");
        txtBanSo.setText("");
        Controller.orderData.DeleteHoaDon();
        try {
            creatArraylistOrder(sqlOrder);
        } catch (SQLException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataOrder();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        HoaDonQL nl = new HoaDonQL();
        nl.setMaHD(this.txtMaHD.getText());
        nl.setMaNL(this.cbMaNL.getSelectedItem().toString());
        nl.setMaNhanVien(this.cbMaNV.getSelectedItem().toString());
        nl.setSoLuong(this.JsnSL.getValue().toString());
        nl.setDonGia(this.txtDonGia.getText());
        nl.setThanhTien(this.txtTTOD.getText());
        Controller.orderData.UpdateHoaDon(nl);
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnThem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem4ActionPerformed
        // TODO add your handling code here:
        if (txtBanSo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời chọn bàn phục vụ");
        }
        orderDV dv = new orderDV();
        dv.setMaHD(this.cbMaHDHD.getSelectedItem().toString());
        dv.setMaMon(this.cbMaMonOD.getSelectedItem().toString());
        dv.setMaNhanVien(this.cbNguoiTT.getSelectedItem().toString());
        dv.setTenMon(this.cbNN.getSelectedItem().toString());
        dv.setSoLuong(this.cbSL4.getSelectedItem().toString());
        dv.setDonGia(this.txtDonGia2.getText());
        Controller.orderData.InsertOrderDV(dv);
        try {
            creatArraylistOrder(sqlOrder);
        } catch (SQLException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataOrder();
    }//GEN-LAST:event_btnThem4ActionPerformed

    private void btnThem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem2ActionPerformed
        // TODO add your handling code here:
        if (txtBanSo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời chọn bàn phục vụ");
        }
        orderDV dv = new orderDV();
        dv.setMaHD(this.cbMaHDHD.getSelectedItem().toString());
        dv.setMaMon(this.cbMaMonOD.getSelectedItem().toString());
        dv.setMaNhanVien(this.cbNguoiTT.getSelectedItem().toString());
        dv.setTenMon(this.cbTS.getSelectedItem().toString());
        dv.setSoLuong(this.cbSL2.getSelectedItem().toString());
        dv.setDonGia(this.txtDonGia2.getText());
        Controller.orderData.InsertOrderDV(dv);
        try {
            creatArraylistOrder(sqlOrder);
        } catch (SQLException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataOrder();
    }//GEN-LAST:event_btnThem2ActionPerformed

    private void btnThem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem3ActionPerformed
        // TODO add your handling code here:
        if (txtBanSo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời chọn bàn phục vụ");
        }
        orderDV dv = new orderDV();
        dv.setMaHD(this.cbMaHDHD.getSelectedItem().toString());
        dv.setMaMon(this.cbMaMonOD.getSelectedItem().toString());
        dv.setMaNhanVien(this.cbNguoiTT.getSelectedItem().toString());
        dv.setTenMon(this.cbTM.getSelectedItem().toString());
        dv.setSoLuong(this.cbSL3.getSelectedItem().toString());
        dv.setDonGia(this.txtDonGia2.getText());
        Controller.orderData.InsertOrderDV(dv);
        try {
            creatArraylistOrder(sqlOrder);
        } catch (SQLException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataOrder();
    }//GEN-LAST:event_btnThem3ActionPerformed

    private void btnThemDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDVActionPerformed
        // TODO add your handling code here:
        if (txtBanSo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời chọn bàn phục vụ");
        }
        orderDV dv = new orderDV();
        dv.setMaHD(this.cbMaHDHD.getSelectedItem().toString());
        dv.setMaMon(this.cbMaMonOD.getSelectedItem().toString());
        dv.setMaNhanVien(this.cbNguoiTT.getSelectedItem().toString());
        dv.setTenMon(this.cbCF.getSelectedItem().toString());
        dv.setSoLuong(this.cbSL1.getSelectedItem().toString());
        dv.setDonGia(this.txtDonGia2.getText());
        Controller.orderData.InsertOrderDV(dv);
        //total1();
        try {
            creatArraylistOrder(sqlOrder);
        } catch (SQLException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataOrder();
    }//GEN-LAST:event_btnThemDVActionPerformed

    private void cbSL3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSL3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSL3ActionPerformed

    private void btnB04ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB04ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B04");
    }//GEN-LAST:event_btnB04ActionPerformed

    private void btnB03ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB03ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B03");
    }//GEN-LAST:event_btnB03ActionPerformed

    private void btnB02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB02ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B02");
    }//GEN-LAST:event_btnB02ActionPerformed

    private void btnB01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnB01ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B01");
    }//GEN-LAST:event_btnB01ActionPerformed

    private void JsnSLStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_JsnSLStateChanged
        try {
            // TODO add your handling code here:
            JsnSL.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        int soluong = (Integer) JsnSL.getValue();
        String DonGia;
        DonGia = txtDonGia.getText();
        int g = Integer.parseInt(DonGia);
        txtThanhTien.setText(String.valueOf(soluong * g));
    }//GEN-LAST:event_JsnSLStateChanged

    private void tbHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbHoaDonMouseClicked
        // TODO add your handling code here:
        curentID = (tbHoaDon.getValueAt(tbHoaDon.getSelectedRow(), 0).toString());
        txtMaHD.setText(tbHoaDon.getValueAt(tbHoaDon.getSelectedRow(), 0).toString());
        cbMaNL.setSelectedItem(tbHoaDon.getValueAt(tbHoaDon.getSelectedRow(), 1).toString());
        cbMaNV.setSelectedItem(tbHoaDon.getValueAt(tbHoaDon.getSelectedRow(), 2).toString());
        //JsnSL.setValue(tbHoaDon.getValueAt(tbHoaDon.getSelectedRow(),3).toString());
        txtDonGia.setText(tbHoaDon.getValueAt(tbHoaDon.getSelectedRow(), 4).toString());
        txtThanhTien.setText(tbHoaDon.getValueAt(tbHoaDon.getSelectedRow(), 5).toString());
    }//GEN-LAST:event_tbHoaDonMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        if (this.txtSearch.getText().length() == 0) {
            String sql1 = "SELECT * from HoaDon ";
            try {
                creatArraylistHoaDon(sql1);
            } catch (SQLException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableDataHoaDon();
        } else {
            String sql1 = "SELECT * from HoaDon WHERE MaHD like N'%" + this.txtSearch.getText() + "%' "
            + "or MaNL like N'%" + this.txtSearch.getText() + "%'"
            + "or MaNhanVien like N'%" + this.txtSearch.getText() + "%'" + "or TenNL like N'%" + this.txtSearch.getText() + "%'"
            + "or SoLuong like N'%" + this.txtSearch.getText() + "%'" + "or DonGia like N'%" + this.txtSearch.getText() + "%'";
            try {
                creatArraylistHoaDon(sql1);
            } catch (SQLException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableDataHoaDon();
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        String delete = null;
        delete = txtMaHD.getText();
        Controller.HoaDonData.DeleteHoaDon(delete);
        try {
            creatArraylistHoaDon(sqlHoaDon);
        } catch (SQLException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataHoaDon();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (curentID.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Ban chua chon dong can sua");
            return;
        }
//        if (txtTenNL.getText().trim().equals("")) {
//            JOptionPane.showMessageDialog(null, "Mời nhập tên nguyên liệu!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
//            txtTenNL.requestFocus();
//            return;
//        }

        //        if (txtSoLuong.getText().trim().equals("")) {
            //            JOptionPane.showMessageDialog(null, "Mời nhập số lượng!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            //            txtSoLuong.requestFocus();
            //            return;
            //        }
        if (txtDonGia.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời nhập đơn giá", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            txtDonGia.requestFocus();
            return;
        }
        HoaDonQL nl = new HoaDonQL();
        nl.setMaHD(this.txtMaHD.getText());
        nl.setMaNL(this.cbMaNL.getSelectedItem().toString());
        nl.setMaNhanVien(this.cbMaNV.getSelectedItem().toString());
        nl.setSoLuong(this.JsnSL.getValue().toString());
        nl.setDonGia(this.txtDonGia.getText());
        nl.setThanhTien(this.txtThanhTien.getText());
        if (Controller.HoaDonData.UpdateHoaDon(nl)) {
            JOptionPane.showMessageDialog(null, "Bạn đã sửa thành công", "Thông báo", 1);
        }
        try {
            creatArraylistHoaDon(sqlHoaDon);
        } catch (SQLException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataHoaDon();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        try {
            txtMaHD.setText(Controller.HoaDonData.taomaHoaDon());
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
//        if (txtTenNL.getText().trim().equals("")) {
//            JOptionPane.showMessageDialog(null, "Mời nhập tên nguyên liệu !", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
//            txtTenNL.requestFocus();
//            return;
//        }

        //        if (txtSoLuong.getText().trim().equals("")) {
            //            JOptionPane.showMessageDialog(null, "Mời nhập Số lượng!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            //            txtSoLuong.requestFocus();
            //            return;
            //        }
        HoaDonQL nl = new HoaDonQL();
        nl.setMaHD(this.txtMaHD.getText());
        nl.setMaNL(this.cbMaNL.getSelectedItem().toString());
        nl.setMaNhanVien(this.cbMaNV.getSelectedItem().toString());
        nl.setSoLuong(this.JsnSL.getValue().toString());
        nl.setDonGia(this.txtDonGia.getText());
        nl.setThanhTien(this.txtThanhTien.getText());
        Controller.HoaDonData.InsertHoaDon(nl);
        try {
            creatArraylistHoaDon(sqlHoaDon);
        } catch (SQLException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataHoaDon();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            txtMaHD.setText(Controller.HoaDonData.taomaHoaDon());
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        cbMaNL.setSelectedItem(null);
        cbMaNV.setSelectedItem(null);
        //JsnSL.setValue("".);
        txtDonGia.setText("");
        txtThanhTien.setText("0");
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        LOGIN lg = new LOGIN();
        lg.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        XuLyDN xl = new XuLyDN();
        xl.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B05");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B06");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B07");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B08");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B09");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B10");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B11");
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B12");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B13");
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        txtBanSo.setText("B14");
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Sẽ được phát triển trong tương lai!");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbSL1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSL1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSL1ActionPerformed

    private void cbCFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCFActionPerformed

    private void comboboxMaNL() {
        String sql = "SELECT * FROM NguyenLieu";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbMaNL.addItem(rs.getString("MaNL"));
            }
        } catch (Exception e) {

        }
    }

    private void comboboxMaNV() {
        String sql = "SELECT * FROM QLNhan_Vien";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbMaNV.addItem(rs.getString("MaNhanVien"));
            }
        } catch (Exception e) {

        }
    }

    private void comboboxNguoiTT() {
        String sql = "SELECT * FROM QLNhan_Vien";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbNguoiTT.addItem(rs.getString("MaNhanVien"));
            }
        } catch (Exception e) {

        }
    }

    private void comboboxNuocNgot() {
        String sql = "SELECT * FROM DichVu WHERE TenMon like 'Sting%'";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbNN.addItem(rs.getString("TenMon"));
            }
        } catch (Exception e) {

        }
    }

    private void comboboxTrangMieng() {
        String sql = "SELECT * FROM DichVu WHERE TenMon like N'Hạt%'";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbTM.addItem(rs.getString("TenMon"));
            }
        } catch (Exception e) {

        }
    }

    private void comboboxTraSua() {
        String sql = "SELECT * FROM DichVu WHERE TenMon like N'Trà%'";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbTS.addItem(rs.getString("TenMon"));
            }
        } catch (Exception e) {

        }
    }

    private void comboboxCafe() {
        String sql = "SELECT * FROM DichVu WHERE TenMon like N'Cafe%'";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbCF.addItem(rs.getString("TenMon"));
            }
        } catch (Exception e) {

        }
    }

    private void comboboxCMaHDHD() {
        String sql = "SELECT * FROM HoaDon";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbMaHDHD.addItem(rs.getString("MaHD"));
            }
        } catch (Exception e) {

        }
    }

    private void comboboxCMamon() {
        String sql = "SELECT * FROM DichVu";
        try {
            st = connectDatabase.TaoKetNoi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cbMaMonOD.addItem(rs.getString("MaMon"));
            }
        } catch (Exception e) {

        }
    }

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
            java.util.logging.Logger.getLogger(vi_HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vi_HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vi_HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vi_HoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new vi_HoaDon().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(vi_HoaDon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner JsnSL;
    private javax.swing.JButton btnB01;
    private javax.swing.JButton btnB02;
    private javax.swing.JButton btnB03;
    private javax.swing.JButton btnB04;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThem2;
    private javax.swing.JButton btnThem3;
    private javax.swing.JButton btnThem4;
    private javax.swing.JButton btnThemDV;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cbCF;
    private javax.swing.JComboBox<String> cbMaHDHD;
    private javax.swing.JComboBox<String> cbMaMonOD;
    private javax.swing.JComboBox cbMaNL;
    private javax.swing.JComboBox cbMaNV;
    private javax.swing.JComboBox<String> cbNN;
    private javax.swing.JComboBox<String> cbNguoiTT;
    private javax.swing.JComboBox<String> cbSL1;
    private javax.swing.JComboBox<String> cbSL2;
    private javax.swing.JComboBox<String> cbSL3;
    private javax.swing.JComboBox<String> cbSL4;
    private javax.swing.JComboBox<String> cbTM;
    private javax.swing.JComboBox<String> cbTS;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTable tbHoaDon;
    private javax.swing.JTable tbOrder;
    private javax.swing.JTextField txtBanSo;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtDonGia2;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTTOD;
    private javax.swing.JTextField txtThanhTien;
    // End of variables declaration//GEN-END:variables

}
