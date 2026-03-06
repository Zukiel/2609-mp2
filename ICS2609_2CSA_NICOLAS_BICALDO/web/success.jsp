<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%
    // Prevent Back Button Access
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session.getAttribute("user") == null) { response.sendRedirect("index.jsp"); return; }
    
    String currentUser = (String) session.getAttribute("user");
    String currentRole = (String) session.getAttribute("role");
    List<Map<String, String>> userList = (List<Map<String, String>>) request.getAttribute("userList");
%>
<html>
<body>
    <h2>Welcome, <%= currentUser %>! (Role: <%= currentRole %>)</h2>
    <a href="LogoutServlet">Logout</a>

    <h3>User Records</h3>
    <table border="1">
        <tr><th>Email</th><th>Role</th><th>Actions</th></tr>
        <% if(userList != null) { for(Map<String, String> user : userList) { %>
            <tr>
                <td><%= user.get("email") %></td>
                <td><%= user.get("role") %></td>
                <td>
                    <% if(currentRole.equalsIgnoreCase("admin")) { %>
                        <a href="update_form.jsp?email=<%= user.get("email") %>">Update</a>
                        <% if(!user.get("email").equals(currentUser)) { %>
                            | <a href="DeleteServlet?email=<%= user.get("email") %>">Delete</a>
                        <% } %>
                    <% } else { %> View Only <% } %>
                </td>
            </tr>
        <% } } %>
    </table>

    <% if(currentRole.equalsIgnoreCase("admin")) { %>
        <h3>Insert New User</h3>
        <form action="InsertServlet" method="post">
            Email: <input type="text" name="email" required><br>
            Password: <input type="password" name="password" required><br>
            Role: <select name="role"><option value="Guest">Guest</option><option value="Admin">Admin</option></select><br>
            <button type="submit">Insert</button>
        </form>
    <% } %>
</body>
</html>