/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import minhlb.db.MyConnection;
import minhlb.dtos.Account;

/**
 *
 * @author Minh
 */
public class AccountDAO implements Serializable {

    private static Connection cn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    private static void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (cn != null) {
            cn.close();
        }
    }

    public Account checkLogin(String email, String password) throws Exception {
        Account result = null;
        String name, roleId;
        boolean status;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT Name, RoleId, [Status] FROM Account WHERE Email = ? AND [Password] = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getNString("Name");
                roleId = rs.getString("RoleId");
                status = rs.getBoolean("Status");
                result = new Account(email, name, roleId, status);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertAccount(Account dto) throws Exception {
        boolean result = false;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO Account(Email, [Password], Name, RoleId, [Status]) "
                    + "VALUES (?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, dto.getEmail());
            ps.setString(2, dto.getPassword());
            ps.setNString(3, dto.getName());
            ps.setString(4, dto.getRoleId());
            ps.setBoolean(5, dto.isStatus());
            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }
}
