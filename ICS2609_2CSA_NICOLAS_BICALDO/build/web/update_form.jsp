<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Ensure only admins can see this
    if (session.getAttribute("user") == null || !"admin".equalsIgnoreCase((String)session.getAttribute("role"))) {
        response.sendRedirect("error_session.jsp");
        return;
    }
    String emailToUpdate = request.getParameter("email");
%>
<h2>Update User</h2>
<form action="UpdateServlet" method="post">
    <input type="hidden" name="oldEmail" value="<%= emailToUpdate %>">
    
    <label>Email/Username:</label><br>
    <input type="text" name="email" value="<%= emailToUpdate %>" required><br>
    
    <label>Password:</label><br>
    <input type="password" name="password" required><br>
    
    <label>Role:</label><br>
    <select name="role">
        <option value="Guest">Guest</option>
        <option value="Admin">Admin</option>
    </select><br><br>
    
    <button type="submit">Save Changes</button>
    <a href="DashboardServlet">Cancel</a>
</form>