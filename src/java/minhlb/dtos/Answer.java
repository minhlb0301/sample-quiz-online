/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Minh
 */
public class Answer implements Serializable{
    private String answerId, questionId, answer;
    private boolean isCorrect;
    private Date createDate;

    public Answer(String answerId, String questionId, String answer, boolean isCorrect) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
