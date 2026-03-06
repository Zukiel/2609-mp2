<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    HttpSession currentSession = request.getSession(false);
    if (currentSession != null) {
        currentSession.invalidate();
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <%= application.getInitParameter("headerText")%>

        <div style="display: flex; justify-content: center; flex-wrap: wrap; gap: 4rem;">
            <div style="flex: 1; min-width: 300px;">
                <h2>Log in.</h2>
            </div>

            <div style="flex: 1; min-width: 300px;">
                <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                    <label style="display:block; font-size: 0.85rem; color: #555;">USERNAME</label>
                    <input type="text" name="username"/>

                    <label style="display:block; font-size: 0.85rem; color: #555;">PASSWORD</label>
                    <input type="password" name="password" />
                    <br/><br/>
                    <button type="submit">Access Account</button>
                </form>
            </div>
        </div>

        <%= application.getInitParameter("footerText")%>
    </body>
</html>
