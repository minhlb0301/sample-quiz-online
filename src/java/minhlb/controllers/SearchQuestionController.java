/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.util.ArrayList;
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
public class SearchQuestionController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchQuestionController.class);
    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String RESULT = "search_result.jsp";
    private static final int PAGE_SIZE = 5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            if (account != null) {
                if (account.getRoleId().equals("1")) {
                    String pageId = request.getParameter("page");
                    int page;
                    if (pageId == null) {
                        page = 1;
                    } else {
                        page = Integer.parseInt(pageId);
                    }
                    String action = request.getParameter("action");
                    if (action != null) {
                        switch (action) {
                            case "<":
                                page--;
                                break;
                            case ">":
                                page++;
                                break;
                            default:
                                break;
                        }
                    }
                    int from = page * PAGE_SIZE + 1 - PAGE_SIZE;
                    int to = page * PAGE_SIZE;
                    String searchQuestion = request.getParameter("txtQuestion");
                    if (searchQuestion == null) {
                        searchQuestion = "";
                    }
                    String status = request.getParameter("status");
                    String subjectId = request.getParameter("subject");
                    if (subjectId == null) {
                        subjectId = "";
                    }
                    QuestionDAO questionDAO = new QuestionDAO();
                    List<Question> questions = questionDAO.searchQuestion(searchQuestion, Boolean.parseBoolean(status), subjectId, from, to);
                    AnswerDAO answerDAO = new AnswerDAO();
                    List<Answer> listAnswer = new ArrayList<>();
                    if (questions != null && questions.size() > 0) {
                        for (Question dto : questions) {
                            List<Answer> answers = answerDAO.getAnswerByQuestionId(dto.getQuestionId());
                            for (Answer answer : answers) {
                                listAnswer.add(answer);
                            }
                        }
                    }
                    int questionCount = questionDAO.getQuestionCount(searchQuestion, Boolean.parseBoolean(status), subjectId);
                    int limitPage = 0;
                    if(questionCount / PAGE_SIZE < 1) {
                        limitPage = 1;
                    }else {
                        limitPage = Math.round(questionCount / PAGE_SIZE);
                    }
                    if (questions != null) {
                        request.setAttribute("SearchResult", questions);
                        request.setAttribute("ListAnswer", listAnswer);
                        request.setAttribute("txtQuestion", searchQuestion);
                        request.setAttribute("status", status);
                        request.setAttribute("subject", subjectId);
                        request.setAttribute("page", page);
                        request.setAttribute("limitPage", limitPage);
                        url = RESULT;
                    } else {
                        request.setAttribute("SEARCH_ERROR", "Not Found!");
                        url = RESULT;
                    }
                } else if (account.getRoleId().equals("2")) {
                    request.setAttribute("ERROR_MSG", "Only Admin role has permission to do this function.");
                }
            } else {
                url = LOGIN_PAGE;
            }

        } catch (Exception e) {
            LOGGER.error("ERROR at SearchQuestionController: " + e.getMessage());
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
