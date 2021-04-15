<%-- 
    Document   : quiz_result
    Created on : Feb 18, 2021, 1:38:56 PM
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
        <title>Quiz Result | Quiz Online</title>
    </head>
    <body style="padding-left: 5px">
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <c:if test="${requestScope.action eq 'viewResult'}">
            <table class="table">
                <thead class="thead-light">
                    <tr>
                        <th scope="col">SubjectId</th>
                        <th scope="col">SubjectName</th>
                        <th scope="col">Correct</th>
                        <th scope="col">Point</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${requestScope.SubjectId}</td>
                        <td>${requestScope.SubjectName}</td>
                        <td>${requestScope.correct}</td>
                        <td>${requestScope.point}</td>
                    </tr>
                </tbody>
            </table>

        </c:if>
        <c:if test="${requestScope.action eq 'viewHistory'}">
            <div>
                <form action="getHistory" method="post">
                    Subject Name: <input type="text" name="subjectName" value="${param.subjectName}" /> <br/>
                    <input type="submit" name="Search" value="Search"/>
                </form>
            </div>
            <c:if test="${not empty requestScope.results}" var="check">

                <table class="table">
                    <thead class="thead-light">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">SubjectId</th>
                            <th scope="col">SubjectName</th>
                            <th scope="col">Correct</th>
                            <th scope="col">Point</th>
                            <th scope="col">Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.results}" var="dto" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.subjectId}</td>
                                <td>${dto.subjectName}</td>
                                <td>${dto.numOfCorrect}</td>
                                <td>${dto.point}</td>
                                <td>${dto.createDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div style="padding-left: 900px">
                    <form action="getHistory" method="post">
                        <input type="hidden" name="page" value="${requestScope.page}"/>
                        <input type="hidden" name="subjectName" value="${requestScope.subjectName}"/>
                        <input type="submit" name="action" value="<"><input type="text" value="${requestScope.page} / ${requestScope.limitPage}" style="width: 55px; text-align: center" readonly=""/><input type="submit" name="action" value=">">
                    </form>
                </div>
            </c:if>
            <c:if test="${!check}">
                <h5 style="color: red">No Record Found! You should take some quiz first!</h5>
            </c:if>
        </c:if>
    </body>
</html>
