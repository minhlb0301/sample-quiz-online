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
import minhlb.daos.SubjectDAO;
import minhlb.dtos.Account;
import minhlb.dtos.Subject;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
public class GetAllCourseController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetAllCourseController.class);
    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String SUCCESS = "course.jsp";

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
                SubjectDAO subjectDAO = new SubjectDAO();
                List<Subject> subjects = subjectDAO.getAllSubject();
                session.setAttribute("Subjects", subjects);
                url = SUCCESS;
            }else {
                url = LOGIN_PAGE;
            }

        } catch (Exception e) {
            LOGGER.error("ERROR at GetAllCourseController: " + e.getMessage());
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
