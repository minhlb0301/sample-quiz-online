<%-- 
    Document   : header
    Created on : Feb 18, 2021, 4:21:33 PM
    Author     : Minh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
        <style>
            .dropdown:hover>.dropdown-menu {
                display: inherit;
            }
        </style>
        <title>header</title>
    </head>
    <body>
        <header>
            <div class="header fixed-top ">
                <nav class="nav" style="height:50px; line-height: 50px; padding-top: 5px; background-image: url('images/bg-01.jpg')" >
                    <div class="col-7">
                    </div>
                    <div class="row col-5 d-flex bd-highlight" >
                        <a class="col text-center fa-1x" id="tabHome" href="getAllCourse"><i class="fas fa-home fa-2x" style="color:white"></i></a>
                            <c:if test="${sessionScope.USER.roleId eq '1'}">
                            <a class="col text-center" href="create_question.jsp"><i class="fas fa-file-alt fa-2x" style="color:white"></i></a>
                            </c:if>
                            <c:if test="${sessionScope.USER.roleId eq '2'}">
                            <a class="col text-center" href="getHistory"><i class="fas fa-file-alt fa-2x" style="color:white"></i></a>
                            </c:if>
                        <div class="dropdown" >
                            <button class="btn btn-warning dropdown-toggle" style="margin-bottom: 12px" id="dropdownButton" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                ${sessionScope.USER.name}
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownButton">
                                <form action="logout" method="post" class="dropdown-item">
                                    <button type="submit" class="btn btn-warning" value="Logout">
                                        Logout
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </nav>
            </div> 
        </header>
    </body>
</html>
