<%@page contentType="text/html" pageEncoding="UTF-8"  isErrorPage="true"%>
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
