package servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateServlet extends HttpServlet {

    private String dbUrl, dbUser, dbPass, dbDriver;

    public void init() throws ServletException {
        ServletConfig sc = getServletConfig();
        dbUrl = sc.getInitParameter("dbUrl");
        dbUser = sc.getInitParameter("dbUser");
        dbPass = sc.getInitParameter("dbPassword");
        dbDriver = sc.getInitParameter("dbDriver");
        try {
            Class.forName(dbDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldEmail = request.getParameter("oldEmail"); // The ID to find the row
        String newEmail = request.getParameter("email");    // The new value
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
            // Update the row where the email matches the old one
            ps = conn.prepareStatement("UPDATE USERS SET EMAIL = ?, PASSWORD = ?, USERROLE = ? WHERE EMAIL = ?");
            ps.setString(1, newEmail);
            ps.setString(2, pass);
            ps.setString(3, role);
            ps.setString(4, oldEmail);
            ps.executeUpdate();

            if (oldEmail.equals(session.getAttribute("user"))) {
                session.setAttribute("user", newEmail);
                session.setAttribute("role", role);
            }
            response.sendRedirect("DashboardServlet");
        } catch (SQLException e) {
            throw new ServletException(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
    }
}
