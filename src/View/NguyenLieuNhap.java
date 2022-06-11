/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.connectDatabase;
import Model.QLNguyenLieu;
import Model.QLThongKe;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Administrator
 */
public class NguyenLieuNhap extends javax.swing.JFrame {

    /**
     * Creates new form NguyenLieuNhap
     */
    public static ArrayList<QLNguyenLieu> arrayListNguyenLieu;
    ArrayList<QLNguyenLieu> listShift = new ArrayList<>();
    static Vector rowTableNL = new Vector();
    static Vector dataTableNL = new Vector();

    QLNguyenLieu QLNL = new QLNguyenLieu();
    public static String sqlNguyenLieu = "SELECT * FROM NguyenLieu order by MaNL asc";
    String curentID = "";

    public NguyenLieuNhap() throws SQLException, ClassNotFoundException {
        initComponents();
        setTitle("Nhập Nguyên Liệu");
        setLocation(250, 100);
        creatArraylistNguyenLieu(sqlNguyenLieu);
        loadTableDataNguyenLieu();
    }

    static void creatArraylistNguyenLieu(String sql) throws SQLException, ClassNotFoundException {
        Connection conn;
        Statement stmt;
        arrayListNguyenLieu = new ArrayList<>();
        conn = connectDatabase.TaoKetNoi();
        stmt = conn.createStatement();
        try (ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { //Duyêt từng dòng trong sql
                arrayListNguyenLieu.add(new QLNguyenLieu( //goi phương thức khởi tạo nằm trong model nhân viên
                        rs.getString("MaNL").trim(),
                        rs.getString("TenNL"),
                        rs.getDate("NgayNhap"),
                        rs.getString("SoLuong"),
                        rs.getString("DvTinh"),
                        rs.getString("DonGia")
                ));
            }
        }
        stmt.close();
        conn.close();
    }

    public void loadTableDataNguyenLieu() {
        dataTableNL = new Vector();
        for (int i = 0; i < arrayListNguyenLieu.size(); i++) {
            Vector temp = new Vector();
            temp.add(arrayListNguyenLieu.get(i).getMaNL());
            temp.add(arrayListNguyenLieu.get(i).getTenNL());
            String[] strdate = arrayListNguyenLieu.get(i).getNgayNhap().toString().split("/");
            if (strdate.length == 1) {
                strdate = arrayListNguyenLieu.get(i).getNgayNhap().toString().split("-");
            }
            temp.add(strdate[2] + "-" + strdate[1] + "-" + strdate[0]);
            temp.add(arrayListNguyenLieu.get(i).getSoLuong());
            temp.add(arrayListNguyenLieu.get(i).getDvTinh());
            temp.add(arrayListNguyenLieu.get(i).getDonGia());
            dataTableNL.add(temp);
        }
        rowTableNL = new Vector();
        rowTableNL.add("Mã Nguyên Liệu");
        rowTableNL.add("Tên Nguyên Liệu");
        rowTableNL.add("Ngày nhập");
        rowTableNL.add("Số lượng");
        rowTableNL.add("Đơn vị tính");
        rowTableNL.add("Đơn giá");
        DefaultTableModel model = new DefaultTableModel(dataTableNL, rowTableNL);
        tbNguyenLieu.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        btnTinhTien = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaNL = new javax.swing.JTextField();
        txtTenNL = new javax.swing.JTextField();
        cbDvTinh = new javax.swing.JComboBox();
        txtSoLuong = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        chonNgayNhap = new com.toedter.calendar.JDateChooser();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbNguyenLieu = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ NGUYÊN LIỆU");

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        btnTimKiem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTimKiem.setText("TÌM KIẾM");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnXoa.setText("XÓA");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnTinhTien.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnTinhTien.setText("TÍNH TIỀN");
        btnTinhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhTienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTinhTien)
                        .addGap(200, 200, 200))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(201, 201, 201)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem)
                    .addComponent(btnLamMoi)
                    .addComponent(btnSua))
                .addGap(12, 12, 12)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnTinhTien))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel2.setText("Mã Nguyên Liệu : ");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel3.setText("Tên Nguyên Liệu : ");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel4.setText("Ngày nhập : ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel5.setText("Số lượng : ");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel6.setText("Đơn vị tính : ");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel7.setText("Đơn giá : ");

        txtMaNL.setEnabled(false);
        txtMaNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNLActionPerformed(evt);
            }
        });

        cbDvTinh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Đồng", "Cái", "Hộp", "Bộ" }));
        cbDvTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbDvTinhMouseClicked(evt);
            }
        });
        cbDvTinh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDvTinhItemStateChanged(evt);
            }
        });

        chonNgayNhap.setDateFormatString("dd-MM-yyyy");
        chonNgayNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chonNgayNhapMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNL)
                    .addComponent(txtTenNL)
                    .addComponent(chonNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtSoLuong))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbDvTinh, 0, 264, Short.MAX_VALUE)
                            .addComponent(txtDonGia))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(txtMaNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(txtTenNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbDvTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel7)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chonNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbNguyenLieu.setModel(new javax.swing.table.DefaultTableModel(
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
        tbNguyenLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNguyenLieuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbNguyenLieu);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        if (this.txtSearch.getText().length() == 0) {
            String sql1 = "SELECT * from NguyenLieu ";
            try {
                creatArraylistNguyenLieu(sql1);
            } catch (SQLException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableDataNguyenLieu();
        } else {
            String sql1 = "SELECT * from NguyenLieu WHERE MaNL like N'%" + this.txtSearch.getText() + "%' "
                    + "or TenNL like N'%" + this.txtSearch.getText() + "%'"
                    + "or SoLuong like N'%" + this.txtSearch.getText() + "%'" + "or DonGia like N'%" + this.txtSearch.getText() + "%'";
            try {
                creatArraylistNguyenLieu(sql1);
            } catch (SQLException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableDataNguyenLieu();
        }
//        String shift = (String) cbDvTinh.getSelectedItem();
//        listShift = login.showShifts(shift);
//        Vector clums = new Vector();
//        clums.add("Mã nhân viên");
//        clums.add("Họ tên");
//        clums.add("Địa chỉ");
//        clums.add("Phone");
//        clums.add("Email");
//        clums.add("Quyền hạn");
//        clums.add("Ca làm việc");
//        Vector data = new Vector();
//        for (Shiftname sfn : listShift) {
//            Vector row = new Vector();
//            row.add(sfn.getCode());
//            row.add(sfn.getFullname());
//            row.add(sfn.getAddress());
//            row.add(sfn.getPhone());
//            row.add(sfn.getEmail());
//            row.add(sfn.getRoles());
//            row.add(sfn.getShiftname());
//
//            data.add(row);
//        }
//        tblEmp.setModel(new DefaultTableModel(data, clums));
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtMaNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNLActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        try {
            // TODO add your handling code here:
            txtMaNL.setText(Controller.NguyenLieuData.taomaNguyenLieu());
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtTenNL.setText("");
        chonNgayNhap.setDate(null);
        txtSoLuong.setText("");
        cbDvTinh.setEditor(null);
        txtDonGia.setText("");
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void tbNguyenLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNguyenLieuMouseClicked
        curentID = (tbNguyenLieu.getValueAt(tbNguyenLieu.getSelectedRow(), 0).toString());
        txtMaNL.setText(tbNguyenLieu.getValueAt(tbNguyenLieu.getSelectedRow(), 0).toString());
        txtTenNL.setText(tbNguyenLieu.getValueAt(tbNguyenLieu.getSelectedRow(), 1).toString());
        String[] strdate = tbNguyenLieu.getValueAt(tbNguyenLieu.getSelectedRow(), 2).toString().split("-");
        java.sql.Date date = java.sql.Date.valueOf(strdate[2] + "-" + strdate[1] + "-" + strdate[0]);
        chonNgayNhap.setDate(date);
        txtSoLuong.setText(tbNguyenLieu.getValueAt(tbNguyenLieu.getSelectedRow(), 3).toString());
        cbDvTinh.setSelectedItem(tbNguyenLieu.getValueAt(tbNguyenLieu.getSelectedRow(), 4).toString());
        txtDonGia.setText(tbNguyenLieu.getValueAt(tbNguyenLieu.getSelectedRow(), 5).toString());
        //txtTongTien.setText(tbNguyenLieu.getValueAt(tbNguyenLieu.getSelectedRow(), 6).toString());

    }//GEN-LAST:event_tbNguyenLieuMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        String delete = null;
        delete = txtMaNL.getText();
        Controller.NguyenLieuData.DeleteNguyenLieu(delete);
        try {
            creatArraylistNguyenLieu(sqlNguyenLieu);
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataNguyenLieu();
    }//GEN-LAST:event_btnXoaActionPerformed

    @SuppressWarnings("IncompatibleEquals")
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        try {
            txtMaNL.setText(Controller.NguyenLieuData.taomaNguyenLieu());
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (txtTenNL.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời nhập tên nguyên liệu !", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            txtTenNL.requestFocus();
            return;
        }
        if (chonNgayNhap.getDateFormatString().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời nhập số điện thoại!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            chonNgayNhap.requestFocus();
            return;
        }
        if (!chonNgayNhap.getDateFormatString().matches("dd-MM-yyyy")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!", "Mesage Dialog", JOptionPane.INFORMATION_MESSAGE);
            chonNgayNhap.requestFocus();
            return;
        }
        if (txtSoLuong.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời nhập Số lượng!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }
        QLNguyenLieu nl = new QLNguyenLieu();
        nl.setMaNL(this.txtMaNL.getText());
        nl.setTenNL(this.txtTenNL.getText());
        java.util.Date dNow = chonNgayNhap.getDate();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        String[] strdate = ft.format(dNow).toString().split("-");
        java.sql.Date date = java.sql.Date.valueOf(strdate[2] + "-" + strdate[1] + "-" + strdate[0]);
        nl.setNgayNhap(date);
        nl.setSoLuong(this.txtSoLuong.getText());
        nl.setDvTinh(this.cbDvTinh.getSelectedItem().toString());
        nl.setDonGia(this.txtDonGia.getText());
        Controller.NguyenLieuData.InsertNguyenLieu(nl);
        try {
            creatArraylistNguyenLieu(sqlNguyenLieu);
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataNguyenLieu();
    }//GEN-LAST:event_btnThemActionPerformed

    private void chonNgayNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chonNgayNhapMouseClicked

    }//GEN-LAST:event_chonNgayNhapMouseClicked

    private void btnTinhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhTienActionPerformed
        // TODO add your handling code here:
        double tinhtien = 0;
        for (int i = 0; i <= 0; i++) {
            if (txtSoLuong.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Mời nhập số lượng để tính tiền", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
                txtSoLuong.requestFocus();
                return;
            }
            if (txtDonGia.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Mời nhập đơn giá để tính tiền", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
                txtDonGia.requestFocus();
                return;
            }
            int soluong = Integer.valueOf(txtSoLuong.getText().toString());
            double dongia = Double.valueOf(txtDonGia.getText().toString());
            tinhtien = soluong * dongia;
        }
        JOptionPane.showMessageDialog(null, "Số tiền thanh toán nguyên liệu là " + tinhtien);
        try {
            creatArraylistNguyenLieu(sqlNguyenLieu);
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataNguyenLieu();
    }//GEN-LAST:event_btnTinhTienActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (curentID.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Ban chua chon dong can sua");
            return;
        }
        if (txtTenNL.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời nhập tên nguyên liệu!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            txtTenNL.requestFocus();
            return;
        }

        if (chonNgayNhap.getDate().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời nhập thời gian!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            chonNgayNhap.requestFocus();
            return;
        }
        if (txtSoLuong.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời nhập số lượng!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            txtSoLuong.requestFocus();
            return;
        }
//        if (!txtSoLuong.getText().trim().matches("0\\d\\d\\d\\d\\d\\d\\d\\d\\d")) {
//            JOptionPane.showMessageDialog(null, "S? di?n tho?i không h?p l?!", "Mesage Dialog", JOptionPane.INFORMATION_MESSAGE);
//            txtSoLuong.requestFocus();
//            return;
//        }
        if (txtDonGia.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Mời nhập đơn giá", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
            txtDonGia.requestFocus();
            return;
        }
//        if (!txtEmail.getText().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
//            JOptionPane.showMessageDialog(null, "Email không h?p l?!", "Mesage Dialog", JOptionPane.INFORMATION_MESSAGE);
//            txtEmail.requestFocus();
//            return;
//        }
//        try {
//            if (!(arrayListNhanVien.get(tbNhanVien.getSelectedRow()).getEmail().equalsIgnoreCase(txtEmail.getText())) && Controller.KhachHangData.checkmail1(txtEmail.getText())) {
//                JOptionPane.showMessageDialog(null, "Email trùng!", "Mesage Dialog", JOptionPane.INFORMATION_MESSAGE);
//                txtEmail.requestFocus();
//                return;
//            }
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(QLNhanViien.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(QLNhanViien.class.getName()).log(Level.SEVERE, null, ex);
//        }

        QLNguyenLieu nl = new QLNguyenLieu();
        nl.setMaNL(this.txtMaNL.getText());
        nl.setTenNL(this.txtTenNL.getText());
        //nl.setNgayNhap(this.chonNgayNhap.getDate());
        nl.setSoLuong(this.txtSoLuong.getText());
        nl.setDvTinh(this.cbDvTinh.getSelectedItem().toString());
        nl.setDonGia(this.txtDonGia.getText());
        if (Controller.NguyenLieuData.UpdateNguyenLieu(nl)) {
            JOptionPane.showMessageDialog(null, "Bạn đã sửa thành công", "Thông báo", 1);
        }
        try {
            creatArraylistNguyenLieu(sqlNguyenLieu);
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTableDataNguyenLieu();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void cbDvTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbDvTinhMouseClicked
        // TODO add your handling code here:
//        if (this.cbDvTinh.getSelectedItem() == "") {
//            String sql1 = "SELECT * from NguyenLieu";
//            try {
//                creatArraylistNguyenLieu(sql1);
//            } catch (SQLException ex) {
//                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            loadTableDataNguyenLieu();
//        } else {
//            String sql1 = "SELECT * from NguyenLieu WHERE DvTinh in ('Hộp', 'Cái')" + this.txtSearch.getText() + "%' ";
//            try {
//                creatArraylistNguyenLieu(sql1);
//            } catch (SQLException ex) {
//                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            loadTableDataNguyenLieu();
        // }
    }//GEN-LAST:event_cbDvTinhMouseClicked

    private void cbDvTinhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbDvTinhItemStateChanged
        // TODO add your handling code here:
        if (this.cbDvTinh.getSelectedItem() == "") {
            String sql1 = "SELECT * from NguyenLieu";
            try {
                creatArraylistNguyenLieu(sql1);
            } catch (SQLException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableDataNguyenLieu();
        } else {
            String sql1 = "SELECT * from NguyenLieu WHERE DvTinh like N'%" + this.txtSearch.getText() + "%' ";
            try {
                creatArraylistNguyenLieu(sql1);
            } catch (SQLException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableDataNguyenLieu();
        }
    }//GEN-LAST:event_cbDvTinhItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JMainInterface main = new JMainInterface();
        main.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(NguyenLieuNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguyenLieuNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguyenLieuNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguyenLieuNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new NguyenLieuNhap().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(NguyenLieuNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTinhTien;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox cbDvTinh;
    private com.toedter.calendar.JDateChooser chonNgayNhap;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tbNguyenLieu;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaNL;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenNL;
    // End of variables declaration//GEN-END:variables
}
