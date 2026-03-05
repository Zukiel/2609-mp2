<%-- 
    Document   : error_1
    Created on : 03 1, 26, 5:35:12 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <%= application.getInitParameter("headerText")%>
        <h2>Login Failed (Missing Credentials)</h2>
        <p style="color:red;">You must provide both a username and a password.</p>
        <p><i><%= exception != null ? exception.getMessage() : ""%></i></p>
        <a href="index.jsp">Go back</a>
        <%= application.getInitParameter("footerText")%>
    </body>
</html>
