/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhlb.daos.AccountDAO;
import minhlb.dtos.Account;
import org.apache.log4j.Logger;

/**
 *
 * @author Minh
 */
public class LoginController extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private static final String SUCCESS = "GetAllCourseController";
    private static final String FAIL = "index.jsp";
    private static final String ERROR = "error.jsp";
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String email = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
                        
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            String hexPassword = String.format("%064x", new BigInteger(1, digest));
            
            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.checkLogin(email, hexPassword);
            if (account != null) {
                if (account.isStatus()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", account);
                    url = SUCCESS;
                } else {
                    request.setAttribute("LOGIN_ERROR", "Account is not available!");
                    url = FAIL;
                }
            } else {
                request.setAttribute("LOGIN_ERROR", "Invalid Email/Password!");
                url = FAIL;
            }
            
        } catch (Exception e) {
            LOGGER.error("ERROR at LoginController: " + e.getMessage());
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
