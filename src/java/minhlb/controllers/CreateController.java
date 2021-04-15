/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhlb.daos.AnswerDAO;
import minhlb.daos.QuestionDAO;
import minhlb.dtos.Account;
import minhlb.dtos.Answer;
import minhlb.dtos.Question;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
public class CreateController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String CREATE_PAGE = "create_question.jsp";
    private static final Logger LOGGER = Logger.getLogger(CreateController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            if (account != null) {
                if (account.getRoleId().equals("1")) {
                    String content = request.getParameter("NewQuestion");
                    String[] answers = request.getParameterValues("Answer");
                    String correct = request.getParameter("Correct");
                    boolean isCorrect = false;
                    String subjectId = request.getParameter("Subject");
                    Date createDate = new Date();
                    QuestionDAO questionDAO = new QuestionDAO();
                    String lastQuestionId = questionDAO.getLastQuestionId(subjectId);
                    String questionId;
                    int id = 0;
                    if (lastQuestionId == null || lastQuestionId.equals("")) {
                        questionId = subjectId + "_1";
                    } else {
                        String subLastQuestionId = lastQuestionId.substring(lastQuestionId.lastIndexOf("_") + 1);
                        id = Integer.parseInt(subLastQuestionId);
                        id = id + 1;
                        questionId = subjectId + "_" + id;
                    }
                    Question question = new Question(questionId, subjectId, content, true);
                    question.setCreateDate(createDate);
                    question.setEmail(account.getEmail());
                    AnswerDAO answerDAO = new AnswerDAO();
                    boolean checkInsert = questionDAO.insertQuestion(question);
                    if (checkInsert) {
                        checkInsert = false;
                        int lastAnswerId = answerDAO.getLastAnswerId();
                        String answerId;
                        for (String answer : answers) {
                            lastAnswerId = lastAnswerId + 1;
                            if (correct.equals(answer)) {
                                isCorrect = true;
                            } else {
                                isCorrect = false;
                            }
                            createDate = new Date();
                            answerId = "Answer_" + lastAnswerId;
                            Answer answerDTO = new Answer(answerId, questionId, answer, isCorrect);
                            answerDTO.setCreateDate(createDate);
                            checkInsert = answerDAO.insertAnswer(answerDTO);
                           
                        }
                    }
                    if (checkInsert) {
                        request.setAttribute("Create_Result", "Create New Question Successful!");
                        url = CREATE_PAGE;
                    }
                } else if (account.getRoleId().equals("2")) {
                    request.setAttribute("ERROR_MSG", "Only Admin role has permission to do this function.");
                }
            } else {
                url = LOGIN_PAGE;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at CreateController: " + e.getMessage());
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
