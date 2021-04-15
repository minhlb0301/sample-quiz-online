<%-- 
    Document   : update_question
    Created on : Feb 11, 2021, 12:18:42 PM
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
        <title>Update Page | Quiz Online</title>
    </head>
    <body style="padding-left: 5px">
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <c:if test="${not empty requestScope.Searched_Question}">
            <form action="Update" method="post">
                Question: <input style="width: 500px" type="text" name="Content" value="${requestScope.Searched_Question.content}" required=""/> <br/>
                <br>
                <c:forEach items="${requestScope.Answers}" var="answerDTO">
                    <input type="hidden" name="AnswerId" value="${answerDTO.answerId}"/>
                    Answer : <input style="width: 500px" type="text" name="${requestScope.Searched_Question.questionId}_Answer" value="${answerDTO.answer}" required=""/> <br/>
                    <br>
                    <c:if test="${answerDTO.isCorrect eq 'true'}">
                        Correct: <input style="width: 500px" type="text" name="${requestScope.Searched_Question.questionId}_Correct" value="${answerDTO.answer}" required=""/> <br/>
                        <br>
                    </c:if>
                
                </c:forEach>

                Subject : <select name="Subject">
                    <c:if test="${not empty sessionScope.Subjects}">
                        <c:forEach items="${sessionScope.Subjects}" var="dto">
                            <option value="${dto.subjectId}" <c:if test="${requestScope.Searched_Question.subjectId == dto.subjectId}">selected="true"</c:if>>${dto.subjectId}</option>
                        </c:forEach>
                    </c:if>
                </select><br/>
                <br>
                <input type="hidden" name="QuestionId" value="${requestScope.QuestionId}"/>
                <input type="hidden" name="page" value="${requestScope.page}"/>
                <input type="hidden" name="txtQuestion" value="${requestScope.txtQuestion}"/>
                <input type="hidden" name="status" value="${requestScope.status}"/>
                <input type="hidden" name="subject" value="${requestScope.subject}"/>
                <input type="submit" value="Submit"/>
            </form>
        </c:if>
    </body>
</html>
