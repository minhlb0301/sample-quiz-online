<%-- 
    Document   : create_question
    Created on : Feb 11, 2021, 11:18:56 AM
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
        <title>Create New Question | Quiz Online</title>
    </head>
    <body style="padding-left: 5px">
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <div class="col-lg-12">
            <h3>Create new question</h3>
            <form action="create" method="post">
                Question: <input style="width: 500px" type="text" name="NewQuestion" value="" required=""/><br/>
                <br>
                Answer 1: <input style="width: 500px" type="text" name="Answer" value="" required=""/><br/>
                <br>
                Answer 2: <input style="width: 500px" type="text" name="Answer" value="" required=""/><br/>
                <br>
                Answer 3: <input style="width: 500px" type="text" name="Answer" value="" required=""/><br/>
                <br>
                Answer 4: <input style="width: 500px" type="text" name="Answer" value="" required=""/><br/>
                <br>
                Correct : <input style="width: 500px" type="text" name="Correct" value="" required=""/><br/>
                <br>
                Subject : <select name="Subject">
                    <c:if test="${not empty sessionScope.Subjects}">
                        <c:forEach items="${sessionScope.Subjects}" var="dto">
                            <option value="${dto.subjectId}">${dto.subjectId}</option>
                        </c:forEach>
                    </c:if>
                </select><br/>
                <br>
                <input type="submit" value="Submit"/>
            </form>
        </div>
    </body>
</html>
