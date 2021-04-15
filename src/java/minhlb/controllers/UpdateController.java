/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
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
public class UpdateController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String SUCCESS = "SearchQuestionController";
    private static final Logger LOGGER = Logger.getLogger(UpdateController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            if (account != null) {
                if (account.getRoleId().equals("1")) {
                    String questionId = request.getParameter("QuestionId");
                    String newContent = request.getParameter("Content");
                    String[] newAnswers = request.getParameterValues(questionId + "_Answer");
                    String[] answerIds = request.getParameterValues("AnswerId");
                    String newCorrect = request.getParameter(questionId + "_Correct");
                    boolean isCorrect = false;
                    String newSubject = request.getParameter("Subject");
                    QuestionDAO questionDAO = new QuestionDAO();
                    Question question = new Question(questionId, newSubject, newContent, true);
                    boolean checkUpdate = questionDAO.updateQuestion(question);
                    
                    AnswerDAO answerDAO = new AnswerDAO();
                    for(int i = 0; i < newAnswers.length; i++){
                        if(newAnswers[i].equals(newCorrect)){
                            isCorrect = true;
                        }
                        Answer answer = new Answer(answerIds[i], questionId, newAnswers[i], isCorrect);
                        checkUpdate = answerDAO.updateAnswer(answer);
                    }
                    if (checkUpdate) {
                        request.setAttribute("page", request.getParameter("page"));
                        request.setAttribute("txtQuestion", request.getParameter("txtQuestion"));
                        request.setAttribute("status", request.getParameter("status"));
                        request.setAttribute("subject", request.getParameter("subject"));
                        url = SUCCESS;
                    }
                } else if (account.getRoleId().equals("2")) {
                    request.setAttribute("ERROR_MSG", "Only Admin role has permission to do this function.");
                }
            } else {
                url = LOGIN_PAGE;
            }

        } catch (Exception e) {
            LOGGER.error("ERROR at UpdateController: " + e.getMessage());
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
