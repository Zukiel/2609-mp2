package servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class InsertServlet extends HttpServlet {
    private String dbUrl, dbUser, dbPass, dbDriver;

    public void init() throws ServletException {
        ServletConfig sc = getServletConfig();
        dbUrl = sc.getInitParameter("dbUrl");
        dbUser = sc.getInitParameter("dbUser");
        dbPass = sc.getInitParameter("dbPassword");
        dbDriver = sc.getInitParameter("dbDriver");
        try { Class.forName(dbDriver); } catch (Exception e) { e.printStackTrace(); }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String role = request.getParameter("role");
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("error_session.jsp");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            ps = conn.prepareStatement("INSERT INTO USERS (EMAIL, PASSWORD, USERROLE) VALUES (?, ?, ?)");
            ps.setString(1, email);
            ps.setString(2, pass);
            ps.setString(3, role);
            ps.executeUpdate();
            // Redirect to Controller to refresh data
            response.sendRedirect("DashboardServlet"); 
        } catch (SQLException e) {
            throw new ServletException(e);
        } finally {
            try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}