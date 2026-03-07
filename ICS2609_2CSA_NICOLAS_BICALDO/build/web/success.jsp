<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session.getAttribute("user") == null) { 
        response.sendRedirect("error_session.jsp"); 
        return; 
    }
    
    String currentUser = (String) session.getAttribute("user");
    String currentRole = (String) session.getAttribute("role");
    List<Map<String, String>> userList = (List<Map<String, String>>) request.getAttribute("userList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dashboard</title>
    
    <style>
        table { width: 100%; border-collapse: collapse; margin-bottom: 3rem; margin-top: 1rem; }
        th, td { padding: 16px 10px; text-align: left; border-bottom: 1px solid #eee; }
        th { font-size: 0.85rem; text-transform: uppercase; letter-spacing: 1.5px; color: #555; font-weight: 600; }
        .action-links a { margin-right: 10px; font-size: 0.7rem; padding: 8px 16px; border-width: 1px; }
        .view-only { color: #aaa; font-style: italic; font-size: 0.85rem; }
        
        .form-label { display: block; font-size: 0.75rem; color: #555; text-transform: uppercase; letter-spacing: 1.5px; margin-bottom: 0.5rem; font-weight: bold; }
    </style>
</head>
<body>
    
    <%= application.getInitParameter("headerText") %>

    <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 2rem;">
        <div>
            <h2>Welcome, <%= currentUser %>.</h2>
            <p style="text-transform: uppercase; font-size: 0.85rem; letter-spacing: 1.5px; color: #555; margin: 0;">Role: <%= currentRole %></p>
        </div>
        <a href="LogoutServlet" class="btn">Logout</a>
    </div>

    <h3>User Records</h3>
    <table>
        <tr>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        <% if(userList != null) { for(Map<String, String> user : userList) { %>
            <tr>
                <td><%= user.get("email") %></td>
                <td><%= user.get("role") %></td>
                <td class="action-links">
                    <% if(currentRole.equalsIgnoreCase("admin")) { %>
                        <a href="update_form.jsp?email=<%= user.get("email") %>" class="btn">Update</a>
                        <% if(!user.get("email").equals(currentUser)) { %>
                            <a href="DeleteServlet?email=<%= user.get("email") %>" class="btn">Delete</a>
                        <% } %>
                    <% } else { %> 
                        <span class="view-only">View Only</span> 
                    <% } %>
                </td>
            </tr>
        <% } } %>
    </table>

    <% if(currentRole.equalsIgnoreCase("admin")) { %>
        <fieldset>
            <legend>Insert New User</legend>
            <form action="InsertServlet" method="post">
                <label class="form-label">Email</label>
                <input type="text" name="email" required><br>
                
                <label class="form-label">Password</label>
                <input type="password" name="password" required><br>
                
                <label class="form-label">Role</label>
                <select name="role">
                    <option value="guest">Guest</option>
                    <option value="admin">Admin</option>
                </select><br><br>
                
                <button type="submit">Insert User</button>
            </form>
        </fieldset>
    <% } %>

    <%= application.getInitParameter("footerText") %>

</body>
</html>