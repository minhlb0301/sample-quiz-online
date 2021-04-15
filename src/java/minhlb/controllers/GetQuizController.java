/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhlb.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
public class GetQuizController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GetQuizController.class);
    private static final String ERROR = "error.jsp";
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String DO_QUIZ = "do_quiz.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            if (account != null) {
                if (account.getRoleId().equals("1")) {
                    request.setAttribute("ERROR_MSG", "This function is used for student role only.");
                } else if (account.getRoleId().equals("2")) {
                    int index;
                    if (request.getParameter("index") == null) {
                        index = 0;
                    } else {
                        index = Integer.parseInt(request.getParameter("index"));
                    }
                    String quizTimeStr;
                    String action = request.getParameter("action");
                    if (action != null && action.equalsIgnoreCase("Next")) {
                        index++;
                        String endTimeStr = (String) session.getAttribute("EndQuiz");
                        Date now = new Date();
                        long nowMilliSec = now.getTime();
                        long endTime = Long.parseLong(endTimeStr);
                        long remain = endTime - nowMilliSec;
                        quizTimeStr = String.valueOf(remain);
                        session.setAttribute("QuizTime", quizTimeStr);
                        String subjectName = request.getParameter("subjectName");
                        session.setAttribute("subjectName", subjectName);
                        String questionId = request.getParameter("questionId");
                        List<String> questionIds = (List<String>) session.getAttribute("questionIds");
                        if(questionIds == null){
                            questionIds = new ArrayList<>();
                        }
                        questionIds.add(questionId);
                        session.setAttribute("questionIds", questionIds);
                        String answer = request.getParameter("answer");
                        answer = answer.replaceAll("%", " ");
                        List<String> answers = (List<String>) session.getAttribute("answers");
                        if(answers == null){
                            answers = new ArrayList<>();
                        }
                        answers.add(answer);
                        session.setAttribute("answers", answers);
                    }
                    int numberOfQuestion = -1;
                    String subjectId = request.getParameter("subjectId");
                    List<Answer> listAnswer = null;
                    List<Question> questions = null;
                    QuestionDAO questionDAO = new QuestionDAO();
                    AnswerDAO answerDAO = new AnswerDAO();
                    String numOfQuestionStr = (String) session.getAttribute("NumOfQuestionStr");
                    if (numOfQuestionStr == null) {
                        numberOfQuestion = questionDAO.getNumberOfQuestion(subjectId);
                        numOfQuestionStr = String.valueOf(numberOfQuestion);
                    } else {
                        numberOfQuestion = Integer.parseInt(numOfQuestionStr);
                    }
                    questions = (List<Question>) session.getAttribute("Questions");
                    if (questions == null) {
                        questions = questionDAO.getQuiz(subjectId, numberOfQuestion);
                    }
                    listAnswer = (List<Answer>) session.getAttribute("ListAnswer");
                    if (listAnswer == null) {
                        listAnswer = new ArrayList<>();
                        for (Question question : questions) {
                            List<Answer> answers = answerDAO.getAnswerByQuestionId(question.getQuestionId());
                            for (Answer answer : answers) {
                                listAnswer.add(answer);
                            }
                        }
                    }
                    long endQuizMilli;
                    long quizTime = numberOfQuestion * 60 * 1000;
                    String endQuizStr = (String) session.getAttribute("EndQuiz");
                    if (endQuizStr == null) {
                        Date startQuiz = new Date();
                        endQuizMilli = startQuiz.getTime() + quizTime;
                        endQuizStr = String.valueOf(endQuizMilli);
                    }
                    quizTimeStr = (String) session.getAttribute("QuizTime");
                    if (quizTimeStr == null) {
                        quizTimeStr = String.valueOf(quizTime);
                    }

                    if (questions != null && !questions.isEmpty() && listAnswer != null && !listAnswer.isEmpty()) {
                        session.setAttribute("Questions", questions);
                        session.setAttribute("ListAnswer", listAnswer);
                        session.setAttribute("NumOfQuestionStr", numOfQuestionStr);
                        session.setAttribute("NumberOfQuestion", numberOfQuestion);
                        request.setAttribute("SubjectId", subjectId);
                        request.setAttribute("SubjectName", request.getParameter("subjectName"));
                        request.setAttribute("TotalQuestion", questions.size());
                        request.setAttribute("index", index);
                        session.setAttribute("QuizTime", quizTimeStr);
                        session.setAttribute("EndQuiz", endQuizStr);
                        url = DO_QUIZ;
                    } else {
                        request.setAttribute("GetQuizError", "This subject has no questions! Let try other subjects!");
                        url = DO_QUIZ;
                    }
                }
            } else {
                url = LOGIN_PAGE;
            }

        } catch (Exception e) {
            LOGGER.error("ERROR at GetQuizController: " + e.getMessage());
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
