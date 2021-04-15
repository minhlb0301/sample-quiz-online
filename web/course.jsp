<%-- 
    Document   : course
    Created on : Feb 8, 2021, 6:28:09 PM
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
        <title>Course | Quiz Online</title>
    </head>
    <body style="padding-left: 5px">
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <c:if test="${sessionScope.USER.roleId eq '1'}">
            <div style="border: solid black 1px; margin-bottom: 10px; padding: 4px 0 4px 4px; width: 500px; float: left; background: lightcyan">
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
        </c:if>
        <c:if test="${sessionScope.USER.roleId eq '2'}">
            
            <c:if test="${not empty sessionScope.Subjects}">
                <div class="container mt-5" style="margin-bottom: 50px; width: 300px">
                    <c:forEach items="${sessionScope.Subjects}" var="dto">
                        <form action="getQuiz" method="post">
                            <div class="card text-left" style="margin-bottom: 7px">
                                <div class="card-header">
                                    <h6 class="card-title">${dto.subjectId} - ${dto.subjectName}</h6>
                                    <input type="hidden" name="subjectId" value="${dto.subjectId}"/>
                                    <input type="hidden" name="subjectName" value="${dto.subjectName}"/>
                                </div>
                                <div class="card-body">
                                    <h6 class="col-6" style="float: right"><button type="submit" value="DoQuiz">Do Quiz</button></h6>
                                </div>
                            </div>
                        </form>
                    </c:forEach>
                </div>
            </c:if>
        </c:if>
    </body>
</html>
