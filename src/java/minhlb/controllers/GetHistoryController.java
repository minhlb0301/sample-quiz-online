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
import minhlb.daos.ResultDAO;
import minhlb.dtos.Account;
import minhlb.dtos.Result;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
public class GetHistoryController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetHistoryController.class);
    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String QUIZ_RESULT = "quiz_result.jsp";
    private static final int PAGE_SIZE = 10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("Questions");
            session.removeAttribute("ListAnswer");
            session.removeAttribute("NumOfQuestionStr");
            session.removeAttribute("NumberOfQuestion");
            session.removeAttribute("QuizTime");
            session.removeAttribute("EndQuiz");
            session.removeAttribute("subjectName");
            session.removeAttribute("questionIds");
            session.removeAttribute("answers");
            Account account = (Account) session.getAttribute("USER");
            if (account != null) {
                if (account.getRoleId().equals("1")) {
                    request.setAttribute("ERROR_MSG", "This function is used for student role only.");
                } else if (account.getRoleId().equals("2")) {
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
                    ResultDAO resultDAO = new ResultDAO();
                    List<Result> results = null;
                    String subjectName = request.getParameter("subjectName");
                    if (subjectName == null || subjectName.equals("")) {
                        results = resultDAO.getHistory(account.getEmail(), from, to);
                    } else {
                        results = resultDAO.getHistoryBySubjectName(subjectName, account.getEmail(), from, to);
                    }
                    int historyCount = resultDAO.getHistoryCount(subjectName);
                    int limitPage = 0;
                    if(historyCount / PAGE_SIZE < 1){
                        limitPage = 1;
                    }else {
                        limitPage = Math.round(historyCount / PAGE_SIZE);
                    }
                    if (results != null) {
                        request.setAttribute("action", "viewHistory");
                        request.setAttribute("page", page);
                        request.setAttribute("subjectName", subjectName);
                        request.setAttribute("results", results);
                        request.setAttribute("limitPage", limitPage);
                        url = QUIZ_RESULT;
                    } else {
                        request.setAttribute("History_MSG", "No record found! You might take a quiz first.");
                        url = QUIZ_RESULT;
                    }
                }
            } else {
                url = LOGIN_PAGE;
            }

        } catch (Exception e) {
            LOGGER.error("ERROR at GetHistoryController: " + e.getMessage());
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
