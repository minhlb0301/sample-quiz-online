/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.util.List;
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
public class GetQuestionByIdController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetQuestionByIdController.class);
    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String UPDATE_PAGE = "update_question.jsp";

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
                    QuestionDAO questionDAO = new QuestionDAO();
                    AnswerDAO answerDAO = new AnswerDAO();
                    Question question = questionDAO.getQuestionById(questionId);
                    List<Answer> answers = answerDAO.getAnswerByQuestionId(questionId);
                    if (question != null) {
                        request.setAttribute("Searched_Question", question);
                        request.setAttribute("Answers", answers);
                        request.setAttribute("page", request.getParameter("page"));
                        request.setAttribute("txtQuestion", request.getParameter("txtQuestion"));
                        request.setAttribute("status", request.getParameter("status"));
                        request.setAttribute("subject", request.getParameter("subject"));
                        request.setAttribute("QuestionId", questionId);
                        url = UPDATE_PAGE;
                    } else {
                        request.setAttribute("Search_Error", "Question Not Found!");
                        url = UPDATE_PAGE;
                    }
                } else if (account.getRoleId().equals("2")) {
                    request.setAttribute("ERROR_MSG", "Only Admin role has permission to do this function.");
                }
            }else{
                url = LOGIN_PAGE;
            }

        } catch (Exception e) {
            LOGGER.error("ERROR at GetQuestionByIdController: " + e.getMessage());
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
