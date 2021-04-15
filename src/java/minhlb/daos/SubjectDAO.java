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
import java.util.ArrayList;
import java.util.List;
import minhlb.db.MyConnection;
import minhlb.dtos.Subject;

/**
 *
 * @author Minh
 */
public class SubjectDAO implements Serializable {

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

    public List<Subject> getAllSubject() throws Exception {
        List<Subject> result = null;
        Subject dto = null;
        try {
            String subjectId, subjectName, name, email;
            cn = MyConnection.getMyConnection();
            String sql = "SELECT SubjectId, SubjectName, s.Email, Name FROM [Subject] s join Account a on s.Email = a.Email";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                subjectId = rs.getString("SubjectId");
                subjectName = rs.getString("SubjectName");
                email = rs.getString("Email");
                name = rs.getNString("Name");
                dto = new Subject(subjectId, subjectName, email);
                dto.setName(name);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<Subject> getSubjectByName(String searchValue) throws Exception {
        List<Subject> result = null;
        Subject dto = null;
        String subjectId, subjectName, name, email;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT SubjectId, SubjectName, s.Email, Name FROM [Subject] s join Account a on s.Email = a.Email "
                    + "WHERE SubjectName LIKE ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                subjectId = rs.getString("SubjectId");
                subjectName = rs.getString("SubjectName");
                email = rs.getString("Email");
                name = rs.getNString("Name");
                dto = new Subject(subjectId, subjectName, email);
                dto.setName(name);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
