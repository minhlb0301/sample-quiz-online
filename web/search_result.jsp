<%-- 
    Document   : search_result
    Created on : Feb 9, 2021, 10:34:07 PM
    Author     : Minh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
        <style>
            .sidebar{
                margin: 0;
                padding: 0;
                width: 200px;
                background-color: #f1f1f1;
                position: fixed;
                height: 100%;
                overflow: auto;
            }
        </style>
        <title>Search Result | Quiz Online</title>
    </head>
    <body style="padding-left: 5px">
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <div class="sidebar" style="border: solid gray 1px; margin-bottom: 10px; padding: 4px 0 4px 4px; height: 10%; width: 500px; background: lightcyan">
            <form action="searchQuestion" method="post">
                Question: <input type="text" name="txtQuestion" /><br/>
                Status: <select name="status">
                    <option value="true">True</option>
                    <option value="false">False</option>
                </select>
                Subject: <select name="subject">
                    <option value=""></option>
                    <c:forEach items="${sessionScope.Subjects}" var="dto">
                        <option value="${dto.subjectId}">${dto.subjectName}</option>
                    </c:forEach>
                </select>
                <input type="submit" name="searchQuestion" value="Search"/> 
            </form>
            <a href="create_question.jsp" >Create New Question</a>
        </div>
        <div style="margin-left: 600px; padding: 1px 16px">
            <c:if test="${not empty requestScope.SearchResult}">
                <c:forEach items="${requestScope.SearchResult}" var="questionDTO">
                    <table border="1" style="margin-bottom: 10px">
                        <thead>
                            <tr class="table-active">
                                <th>${questionDTO.questionId}</th>
                                <th>${questionDTO.content}</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${not empty requestScope.ListAnswer}">
                                <c:forEach items="${requestScope.ListAnswer}" var="answerDTO">
                                    <c:if test="${answerDTO.questionId eq questionDTO.questionId}">
                                        <c:if test="${answerDTO.isCorrect eq 'true'}" var="isCorrect">
                                            <tr style="background: lightgreen">
                                                <td>Answer :</td>
                                                <td>${answerDTO.answer}</td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${!isCorrect}">
                                            <tr>
                                                <td>Answer :</td>
                                                <td>${answerDTO.answer}</td>
                                            </tr>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <tr>
                                <c:if test="${questionDTO.status eq 'true'}" var="isDelete">
                                    <td>
                                        <c:url value="Delete" var="DeleteURL">
                                            <c:param name="page" value="${requestScope.page}"/>
                                            <c:param name="txtQuestion" value="${requestScope.txtQuestion}"/>
                                            <c:param name="status" value="${requestScope.status}"/>
                                            <c:param name="subject" value="${requestScope.subject}"/>
                                            <c:param name="QuestionId" value="${questionDTO.questionId}"/>
                                        </c:url>
                                        <a href="${DeleteURL}" class="btn btn-danger">Delete</a>
                                    </td>
                                </c:if>
                                <c:if test="${!isDelete}">
                                    <td>
                                        <button type="button" class="btn btn-danger" disabled="">Delete</button>
                                    </td>
                                </c:if>
                                <td>
                                    <c:url value="GetQuestionById" var="UpdateURL">
                                        <c:param name="page" value="${requestScope.page}"/>
                                        <c:param name="txtQuestion" value="${requestScope.txtQuestion}"/>
                                        <c:param name="status" value="${requestScope.status}"/>
                                        <c:param name="subject" value="${requestScope.subject}"/>
                                        <c:param name="QuestionId" value="${questionDTO.questionId}"/>
                                    </c:url>
                                    <a href="${UpdateURL}" class="btn btn-info">Update</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </c:forEach>
                <form action="searchQuestion" method="post">
                    <input type="hidden" name="page" value="${requestScope.page}"/>
                    <input type="hidden" name="txtQuestion" value="${requestScope.txtQuestion}"/>
                    <input type="hidden" name="status" value="${requestScope.status}"/>
                    <input type="hidden" name="subject" value="${requestScope.subject}"/>
                    <input type="submit" name="action" value="<"><input type="text" value="${requestScope.page} / ${requestScope.limitPage}" style="width: 55px; text-align: center" readonly=""/><input type="submit" name="action" value=">">
                </form>
            </c:if>
        </div>
    </body>
</html>
