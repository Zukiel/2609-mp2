<%@page contentType="text/html" pageEncoding="UTF-8"  isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <%= application.getInitParameter("headerText")%>
        <h2>Login Failed (Error 2)</h2>
        <p>The username is correct, but the password provided was invalid.</p>
        <p><i>System Message: <%= exception != null ? exception.getMessage() : ""%></i></p>
        <a href="index.jsp">Go back</a>
        <%= application.getInitParameter("footerText")%>
    </body>
</html>
