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
public class Result implements Serializable{
    private String resultId, email, subjectId, numOfCorrect, subjectName;
    private Date createDate;
    private float point;

    public Result(String resultId, String email, String subjectId, String numOfCorrect, Date createDate, float point) {
        this.resultId = resultId;
        this.email = email;
        this.subjectId = subjectId;
        this.numOfCorrect = numOfCorrect;
        this.createDate = createDate;
        this.point = point;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getNumOfCorrect() {
        return numOfCorrect;
    }

    public void setNumOfCorrect(String numOfCorrect) {
        this.numOfCorrect = numOfCorrect;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    
}
