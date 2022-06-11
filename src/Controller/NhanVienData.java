/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.QLNhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class NhanVienData {
    
    public static PreparedStatement ps;
    public static ResultSet rs;
    public static Statement st;

   
    public boolean dangNhap(String taiKhoan, String pass) throws SQLException {
        boolean kt = false;
        try {
            ps = connectDatabase.TaoKetNoi().prepareStatement("SELECT * FROM QLNhan_Vien where MaNhanVien = ? and Password=?");
            ps.setString(1, taiKhoan);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                kt = true;
                ps.close();
            }
        } catch (Exception e) {
            return kt = false;
        }
        return kt;
        //st=Connect.getConnect().Statement("");

    }

    public static ResultSet showTextfield(String sql) {
        try {
            ps = connectDatabase.TaoKetNoi().prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            return null;
        }
    }

    public static void InsertNhanVien(QLNhanVien nv) {
        String sql = "insert into QLNhan_Vien values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = connectDatabase.TaoKetNoi().prepareStatement(sql);
            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getTenNhanVien());
            ps.setString(3, nv.getPassword());
            ps.setString(4, nv.getPhone());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getCMND());
            ps.setDate(7, new java.sql.Date(nv.getNgayLamViec().getYear(), nv.getNgayLamViec().getMonth(), nv.getNgayLamViec().getDate()));
            ps.setString(8, nv.getCaLamViec());
            ps.setString(9, nv.getLuongCoBan());
            ps.setString(10, nv.getHeSoLuong());
            ps.setString(11, nv.getTienLuong());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Ðã thêm khách hàng thành công!", "Thông báo", 1);
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Khách hàng không du?c thêm", "Thông báo", 1);
        }

    }

    public static boolean UpdateNhanVien(QLNhanVien nv) {
        try {
//           FileInputStream in = new FileInputStream(view.JAdminUpdate.fileNameAvatar);
//            byte[] image = new byte[(int) view.JAdminUpdate.fileNameAvatar.length()];
//            in.read(image);
            ps = connectDatabase.TaoKetNoi().prepareStatement("UPDATE QLNhan_Vien SET TenNhanVien = ?,"
                    + "Phone = ?,Email=?,CMND=?,NgayLamViec=?,CaLamViec=?,LuongCoBan=? where MaNhanVien = ?");
            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getTenNhanVien());
            ps.setString(3, nv.getPassword());
            ps.setString(4, nv.getPhone());
            ps.setString(5, nv.getEmail());
            ps.setString(6, nv.getCMND());
            ps.setDate(7, new java.sql.Date(nv.getNgayLamViec().getYear(), nv.getNgayLamViec().getMonth(), nv.getNgayLamViec().getDate()));
            ps.setString(8, nv.getCaLamViec());
            ps.setString(9, nv.getLuongCoBan());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean DeleteKhachHang(String MaNhanVien) {
        try {
            ps = connectDatabase.TaoKetNoi().prepareStatement("DELETE FROM QlNhan_Vien WHERE MaNhanVien = ?");
            ps.setString(1, MaNhanVien);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String taomaNhanVien() throws SQLException, ClassNotFoundException {
        Connection conn;
        Statement stmt;
        conn = connectDatabase.TaoKetNoi();
        String sql = "SELECT MaNhanVien FROM QLNhan_Vien order by MaNhanVien Desc";
        stmt = conn.createStatement();
        String manv;
        rs = stmt.executeQuery(sql);
        rs.next();
        manv = rs.getString("MaNhanVien").trim();
        stmt.close();
        conn.close();

        if ((Integer.valueOf(manv.substring(3)) + 1) < 10) {
            manv = "NV00" + (Integer.valueOf(manv.substring(3)) + 1);
        } else if (((Integer.valueOf(manv.substring(3)) + 1) >= 10) && ((Integer.valueOf(manv.substring(3)) + 1) < 100)) {
            manv = "NV0" + (Integer.valueOf(manv.substring(3)) + 1);
        } else {
            manv = "NV" + (Integer.valueOf(manv.substring(3)) + 1);
        }
        return manv;
    }

    public static boolean checkcmnd(String cmnd) throws ClassNotFoundException, SQLException {
        Connection con = null;
        boolean check = false;
        con = connectDatabase.TaoKetNoi();

        try {
            String SQL = "SELECT CMND FROM QLNhan_Vien Where CMND = ? ";
            PreparedStatement pre = con.prepareStatement(SQL);
            pre.setString(1, cmnd);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    rs.close();
                    pre.close();
                    con.close();
                    check = true;
                } else {
                    rs.close();
                    pre.close();
                    con.close();
                    check = false;

                }
            }
        } catch (Exception e) {
            System.out.println("Error Trace: " + e.getMessage());
            e.printStackTrace();
        }

        return check;

    }

    public static boolean checkmail1(String mail) throws ClassNotFoundException, SQLException {
        Connection con = null;

        con = connectDatabase.TaoKetNoi();
        boolean check = false;
        try {
            String SQL = "SELECT Email FROM QLNhan_Vien Where Email = ? ";

            PreparedStatement pre = con.prepareStatement(SQL);
            pre.setString(1, mail);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    rs.close();
                    pre.close();
                    con.close();
                    check = true;
                } else {
                    rs.close();
                    pre.close();
                    con.close();
                    check = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error Trace: " + e.getMessage());
            e.printStackTrace();
        }

        return check;
    }
    
}
