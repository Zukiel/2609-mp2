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
        <h2>Error 404</h2>
        <p>The requested page or context path was not found on this server.</p>
        <a href="index.jsp">Go back to Login</a>
        <%= application.getInitParameter("footerText")%>
    </body>
</html>
