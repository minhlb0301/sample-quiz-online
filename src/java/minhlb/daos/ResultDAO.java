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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import minhlb.db.MyConnection;
import minhlb.dtos.Result;

/**
 *
 * @author Minh
 */
public class ResultDAO implements Serializable {

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

    public int getLastResultId() throws Exception {
        int result = 0;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT COUNT(ResultId) AS [Count] "
                    + "FROM Result";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(("Count"));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertResult(Result dto) throws Exception {
        boolean result = false;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO Result(ResultId, Email, SubjectId, CreateDate, NumberOfCorrect, Point) "
                    + "VALUES (?,?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, dto.getResultId());
            ps.setString(2, dto.getEmail());
            ps.setString(3, dto.getSubjectId());
            ps.setTimestamp(4, new Timestamp(dto.getCreateDate().getTime()));
            ps.setString(5, dto.getNumOfCorrect());
            ps.setFloat(6, dto.getPoint());
            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<Result> getHistory(String email, int from, int to) throws Exception {
        List<Result> result = null;
        Result dto = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT ResultId, SubjectId, CreateDate, NumberOfCorrect, Point, SubjectName \n"
                    + "FROM	(	SELECT ResultId, Result.SubjectId, CreateDate, NumberOfCorrect, Point, SubjectName, ROW_NUMBER() OVER (ORDER BY CreateDate ASC) AS RowNum\n"
                    + "			FROM Result join [Subject] on Result.SubjectId = [Subject].SubjectId\n"
                    + "			WHERE Result.Email = ?\n"
                    + "		) AS R\n"
                    + "WHERE  R.RowNum BETWEEN ? AND ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setInt(2, from);
            ps.setInt(3, to);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                dto = new Result(rs.getString("ResultId"), email, rs.getString("SubjectId"), rs.getString("NumberOfCorrect"), rs.getDate("CreateDate"), rs.getFloat("Point"));
                dto.setSubjectName(rs.getString("SubjectName"));
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<Result> getHistoryBySubjectName(String subjectName, String email, int from, int to) throws Exception {
        List<Result> result = null;
        Result dto = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT ResultId, SubjectId, CreateDate, NumberOfCorrect, Point, SubjectName \n"
                    + "FROM	(	SELECT ResultId, Result.SubjectId, CreateDate, NumberOfCorrect, Point, SubjectName, ROW_NUMBER() OVER (ORDER BY CreateDate ASC) AS RowNum\n"
                    + "			FROM Result join [Subject] on Result.SubjectId = [Subject].SubjectId\n"
                    + "			WHERE Result.Email = ? AND SubjectName LIKE ?\n"
                    + "		) AS R\n"
                    + "WHERE  R.RowNum BETWEEN ? AND ? ";
            ps = cn.prepareStatement(sql);
            ps.setString(2, "%" + subjectName + "%");
            ps.setString(1, email);
            ps.setInt(3, from);
            ps.setInt(4, to);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                dto = new Result(rs.getString("ResultId"), email, rs.getString("SubjectId"), rs.getString("NumberOfCorrect"), rs.getDate("CreateDate"), rs.getFloat("Point"));
                dto.setSubjectName(rs.getString("SubjectName"));
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public int getHistoryCount(String subjectName) throws Exception{
        int result = 0;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "SELECT COUNT(ResultId) AS [Count] FROM Result join [Subject] on Result.SubjectId = [Subject].SubjectId WHERE SubjectName LIKE ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%"+subjectName+"%");
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("Count");
            }
        }finally{
            closeConnection();
        }
        return result;
    }
}
