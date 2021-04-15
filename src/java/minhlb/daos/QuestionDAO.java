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
import minhlb.dtos.Question;

/**
 *
 * @author Minh
 */
public class QuestionDAO implements Serializable {

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

    public List<Question> searchQuestion(String question, boolean status, String subjectId, int from, int to) throws Exception {
        List<Question> result = null;
        Question dto = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT QuestionId, SubjectId, Question_Content\n"
                    + "FROM(	SELECT QuestionId, SubjectId, Question_Content, ROW_NUMBER() OVER (ORDER BY CreateDate ASC) AS RowNum\n"
                    + "		FROM Question \n"
                    + "		WHERE Question_Content LIKE ? AND SubjectId LIKE ? AND [Status] = ? ) AS Q \n"
                    + "WHERE Q.RowNum BETWEEN ? AND ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + question + "%");
            ps.setString(2, "%" + subjectId + "%");
            ps.setBoolean(3, status);
            ps.setInt(4, from);
            ps.setInt(5, to);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                dto = new Question(rs.getString("QuestionId"), subjectId, rs.getString("Question_Content"), status);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public Question getQuestionById(String searchId) throws Exception {
        Question result = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT QuestionId, SubjectId, Question_Content, [Status] "
                    + "FROM Question WHERE QuestionId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, searchId);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = new Question(searchId, rs.getString("SubjectId"), rs.getString("Question_Content"), rs.getBoolean("Status"));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public String getLastQuestionId(String subjectId) throws Exception {
        String result = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT TOP 1 QuestionId\n"
                    + "FROM Question\n"
                    + "WHERE SubjectId = ?\n"
                    + "ORDER BY CreateDate DESC";
            ps = cn.prepareStatement(sql);
            ps.setString(1, subjectId);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getString("QuestionId");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insertQuestion(Question dto) throws Exception {
        boolean result = false;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "INSERT INTO Question(QuestionId, SubjectId, Question_Content, CreateDate, Status, Email) "
                    + "VALUES (?,?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            ps.setString(1, dto.getQuestionId());
            ps.setString(2, dto.getSubjectId());
            ps.setString(3, dto.getContent());
            ps.setTimestamp(4, new Timestamp(dto.getCreateDate().getTime()));
            ps.setBoolean(5, dto.isStatus());
            ps.setString(6, dto.getEmail());
            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean deleteQuestion(String questionId) throws Exception {
        boolean result = false;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "UPDATE Question "
                    + "SET [Status] = 'false' "
                    + "WHERE QuestionId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, questionId);
            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateQuestion(Question dto) throws Exception {
        boolean result = false;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "UPDATE Question "
                    + "SET SubjectId = ?, Question_Content = ? "
                    + "WHERE QuestionId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, dto.getSubjectId());
            ps.setString(2, dto.getContent());
            ps.setString(3, dto.getQuestionId());
            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getNumberOfQuestion(String subjectId) throws Exception {
        int result = 0;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT NumberOfQuestion FROM [Subject] WHERE SubjectId = ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, subjectId);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("NumberOfQuestion");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<Question> getQuiz(String subjectId, int numberOfQuestion) throws Exception {
        List<Question> result = null;
        Question question = null;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT TOP " + numberOfQuestion + " QuestionId, SubjectId, Question_Content "
                    + "FROM Question "
                    + "WHERE SubjectId = ? AND [Status] = 'True' "
                    + "ORDER BY NEWID()";
            ps = cn.prepareStatement(sql);
            ps.setString(1, subjectId);
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                question = new Question(rs.getString("QuestionId"), subjectId, rs.getString("Question_Content"), true);
                result.add(question);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getQuestionCount(String content, boolean status, String subjectId) throws Exception {
        int result = 0;
        try {
            cn = MyConnection.getMyConnection();
            String sql = "SELECT COUNT(QuestionId) AS [Count] FROM Question WHERE Question_Content LIKE ? AND [Status] = ? AND SubjectId LIKE ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + content + "%");
            ps.setBoolean(2, status);
            ps.setString(3, "%" + subjectId + "%");
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("Count");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
