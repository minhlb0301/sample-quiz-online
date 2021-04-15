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
import minhlb.dtos.Answer;

/**
 *
 * @author Minh
 */
public class AnswerDAO implements Serializable {

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
    
    public int getLastAnswerId() throws Exception {
        int result = 0;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "SELECT COUNT(AnswerId) AS [Count] "
                    + "FROM Answer ";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("Count");
            }
        }finally {
            closeConnection();
        }
        return result;
    }

    public List<Answer> getAnswerByQuestionId(String questionId) throws Exception {
        List<Answer> result = null;
        Answer dto = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT AnswerId, Answer, IsCorrect FROM Answer WHERE QuestionId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, questionId);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while(rs.next()){
                dto = new Answer(rs.getString("AnswerId"), questionId, rs.getString("Answer"), rs.getBoolean("IsCorrect"));
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean insertAnswer(Answer dto) throws Exception {
        boolean result = false;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO Answer(AnswerId, QuestionId, Answer, IsCorrect, CreateDate) "
                    + "VALUES(?, ?, ?, ?, ?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, dto.getAnswerId());
            ps.setString(2, dto.getQuestionId());
            ps.setString(3, dto.getAnswer());
            ps.setBoolean(4, dto.isIsCorrect());
            ps.setTimestamp(5, new Timestamp(dto.getCreateDate().getTime()));
            result = ps.executeUpdate() > 0;
        }finally{
            closeConnection();
        }
        return result;
    }
    
    public boolean updateAnswer(Answer dto) throws Exception {
        boolean result = false;
        try{
            cn = MyConnection.getMyConnection();
            String sql = "UPDATE Answer "
                    + "SET Answer = ?, IsCorrect = ? "
                    + "WHERE AnswerId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, dto.getAnswer());
            ps.setBoolean(2, dto.isIsCorrect());
            ps.setString(3, dto.getAnswerId());
            result = ps.executeUpdate() > 0;
        }finally{
            closeConnection();
        }
        return result;
    }
}
