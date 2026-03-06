package servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteServlet extends HttpServlet {
    private String dbUrl, dbUser, dbPass, dbDriver;

    public void init() throws ServletException {
        ServletConfig sc = getServletConfig();
        dbUrl = sc.getInitParameter("dbUrl");
        dbUser = sc.getInitParameter("dbUser");
        dbPass = sc.getInitParameter("dbPassword");
        dbDriver = sc.getInitParameter("dbDriver");
        try { Class.forName(dbDriver); } catch (Exception e) { e.printStackTrace(); }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetEmail = request.getParameter("email");
        HttpSession session = request.getSession(false);
        String currentUser = (String) session.getAttribute("user");

        // Rule: Cannot delete own record
        if (targetEmail != null && !targetEmail.equals(currentUser)) {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                ps = conn.prepareStatement("DELETE FROM USERS WHERE EMAIL = ?");
                ps.setString(1, targetEmail);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new ServletException(e);
            } finally {
                try { if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (SQLException e) {}
            }
        }
        response.sendRedirect("DashboardServlet");
    }
}