<%-- 
    Document   : do_quiz
    Created on : Feb 16, 2021, 8:51:59 PM
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
        <title>Do Quiz | Quiz Online</title>
    </head>
    <body style="padding-left: 5px" >
        <%@include file="header.jsp" %>
        <br>
        <br>
        <br>
        <c:if test="${not empty requestScope.GetQuizError}">
            <h5 style="color: red">${requestScope.GetQuizError}</h5>
        </c:if>
        <c:if test="${not empty sessionScope.Questions}">
            <form id="myForm" action="checkCorrect" method="post">
                <div class="sidebar" style="border: solid gray 1px; margin-bottom: 10px; padding: 4px 0 4px 4px; height: 17%; width: 500px; background: lightcyan">
                    <h4>Time Remain:<p id="countdown"></p> </h4>
                    Answer: ${requestScope.index } / ${requestScope.TotalQuestion} Question <br/>
                    <input type="hidden" name="subjectId" value="${requestScope.SubjectId}"/>
                    <input type="hidden" name="subjectName" value="${requestScope.SubjectName}"/>
                    <input type="hidden" name="totalQuestion" value="${requestScope.TotalQuestion}"/>
                    <button type="submit" class="btn btn-success" name="SubmitQuiz">Submit</button>
                </div>
                <c:if test="${requestScope.index < requestScope.TotalQuestion}" var="check">
                    <div style="margin-left: 600px; padding: 1px 16px">
                        <input type="hidden" name="questionId" value="${sessionScope.Questions[requestScope.index].questionId}"/>
                        <c:set var="id" value="${sessionScope.Questions[requestScope.index].questionId}"/>
                        <c:set var="count" value="0"/>
                        <h5 style="background: greenyellow; text-justify: auto; width: max-content">Q.${requestScope.index + 1}: ${sessionScope.Questions[requestScope.index].content}</h5>
                        <ul class="list-group" style="list-style-type: none; width: max-content">
                            <c:forEach items="${sessionScope.ListAnswer}" var="answerDTO">
                                <c:if test="${answerDTO.questionId eq pageScope.id}">
                                    <h6>
                                        <li class="list-group-item">
                                            <input type="radio" name="q_${pageScope.id}" value="${answerDTO.answer}"> ${answerDTO.answer}
                                        </li>
                                    </h6>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
            </form >
            <c:if test="${check}">
                <input style="margin-left: 600px; padding: 1px 16px" type="submit" onclick="doNextQuestion()" name="action" value="Next"/>
            </c:if>
        </c:if>

        <script>
            var milliSec;
            var minute;
            var second;
            var time;

            function countDown() {
                milliSec = ${sessionScope.QuizTime};
                time = milliSec / 1000;
                minute = Math.floor(time / 60);
                second = Math.floor(time - (minute * 60));
                var interval = setInterval(function () {

                    time--;
                    second = second - 1;
                    if (second < 0) {
                        minute = minute - 1;
                        second = 59;
                    }
                    document.getElementById("countdown").innerHTML = minute + "m " + second + "s";
                    if (time < 0) {
                        clearInterval(interval);
                        document.getElementById("myForm").submit();
                    }
                }, 1000);
            }
            countDown();
            var answer = document.getElementsByName("q_${pageScope.id}");

            function doNextQuestion() {
                var value;
                for (var i = 0; i < answer.length; i++) {
                    if (answer[i].checked) {
                        value = answer[i].value;
                    }
                }
                location.href = 'getQuiz?index=${requestScope.index}&subjectId=${requestScope.SubjectId}&action=Next&answer=' + value + '&questionId=${pageScope.id}&subjectName=${requestScope.SubjectName}';
            }
        </script>
    </body>
</html>
