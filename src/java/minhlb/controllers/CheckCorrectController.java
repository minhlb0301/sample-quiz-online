/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhlb.daos.ResultDAO;
import minhlb.dtos.Account;
import minhlb.dtos.Answer;
import minhlb.dtos.Result;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
public class CheckCorrectController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String QUIZ_RESULT = "quiz_result.jsp";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final Logger LOGGER = Logger.getLogger(CheckCorrectController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            if (account != null) {
                if (account.getRoleId().equals("1")) {
                    request.setAttribute("ERROR_MSG", "This function is used for student role only");
                } else if (account.getRoleId().equals("2")) {
                    List<Answer> listAnswer = (List<Answer>) session.getAttribute("ListAnswer");
                    int count = 0;
                    int totalQuestion = Integer.parseInt(request.getParameter("totalQuestion"));
                    String subjectName = request.getParameter("SubjectName");
                    if (subjectName == null) {
                        subjectName = (String) session.getAttribute("subjectName");

                    }
                    List<String> questionIds = (List<String>) session.getAttribute("questionIds");
                    if (questionIds != null) {
                        List<String> answers = (List<String>) session.getAttribute("answers");
                        if (answers != null) {
                            for (int i = 0; i < listAnswer.size(); i++) {
                                for (String answer : answers) {
                                    if (listAnswer.get(i).getAnswer().equalsIgnoreCase(answer)) {
                                        if (listAnswer.get(i).isIsCorrect()) {
                                            count = count + 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    float point = (float) count / totalQuestion * 10;
                    String numberOfCorrect = String.valueOf(count) + "/" + String.valueOf(totalQuestion);
                    ResultDAO resultDAO = new ResultDAO();
                    int lastResultId = resultDAO.getLastResultId();
                    String resultId = "Result_" + (lastResultId + 1);
                    Date createDate = new Date();
                    Result result = new Result(resultId, account.getEmail(), request.getParameter("subjectId"), numberOfCorrect, createDate, point);
                    boolean checkInsertResult = resultDAO.insertResult(result);
                    if (checkInsertResult) {
                        request.setAttribute("action", "viewResult");
                        request.setAttribute("SubjectId", request.getParameter("subjectId"));
                        request.setAttribute("SubjectName", request.getParameter("subjectName"));
                        request.setAttribute("point", point);
                        request.setAttribute("correct", numberOfCorrect);
                        url = QUIZ_RESULT;
                    }
                }
            } else {
                url = LOGIN_PAGE;
            }
            session.removeAttribute("Questions");
            session.removeAttribute("ListAnswer");
            session.removeAttribute("NumOfQuestionStr");
            session.removeAttribute("NumberOfQuestion");
            session.removeAttribute("QuizTime");
            session.removeAttribute("EndQuiz");
            session.removeAttribute("subjectName");
            session.removeAttribute("questionIds");
            session.removeAttribute("answers");
        } catch (Exception e) {
            LOGGER.error("ERROR at CheckCorrectController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
