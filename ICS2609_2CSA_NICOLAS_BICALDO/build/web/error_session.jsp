<%@page contentType="text/html" pageEncoding="UTF-8"  isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <%= application.getInitParameter("headerText")%>

        <h2 style="color:red;">Access Denied</h2>
        <p>You attempted to access a secure page without properly logging in, or your session has expired.</p>
        <p><a href="index.jsp">Return to Login Page</a></p>

        <%= application.getInitParameter("footerText")%>
    </body>
</html>
