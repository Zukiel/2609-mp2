<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session.getAttribute("user") == null || !"admin".equalsIgnoreCase((String)session.getAttribute("role"))) {
        response.sendRedirect("error_session.jsp");
        return;
    }
    String emailToUpdate = request.getParameter("email");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update User</title>
       
    <style>
        .form-label { 
            display: block; 
            font-size: 0.75rem; 
            color: #555; 
            text-transform: uppercase; 
            letter-spacing: 1.5px; 
            margin-bottom: 0.5rem; 
            font-weight: bold; 
            margin-top: 1.5rem;
        }
    </style>
</head>
<body>

    <%= application.getInitParameter("headerText") %>

    <h2>Update User.</h2>
    
    <fieldset style="max-width: 400px; margin-top: 1rem;">
        <form action="UpdateServlet" method="post">
            <input type="hidden" name="oldEmail" value="<%= emailToUpdate %>">
            
            <label class="form-label">Email / Username</label>
            <input type="text" name="email" value="<%= emailToUpdate %>" required>
            
            <label class="form-label">New Password</label>
            <input type="password" name="password" required>
            
            <label class="form-label">Role</label>
            <select name="role">
                <option value="guest">Guest</option>
                <option value="admin">Admin</option>
            </select><br><br>
            
            <div style="display: flex; gap: 10px; margin-top: 1rem;">
                <button type="submit">Save Changes</button>
                <a href="DashboardServlet" class="btn">Cancel</a>
            </div>
        </form>
    </fieldset>

    <%= application.getInitParameter("footerText") %>

</body>
</html>