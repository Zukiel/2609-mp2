<%-- 
    Document   : error_1
    Created on : 03 1, 26, 5:35:12 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <%= application.getInitParameter("headerText")%>
        <h2>Login Failed (Error 3)</h2>
        <p>Both the username and password provided are incorrect.</p>
        <a href="index.jsp">Go back</a>
        <%= application.getInitParameter("footerText")%>
    </body>
</html>
