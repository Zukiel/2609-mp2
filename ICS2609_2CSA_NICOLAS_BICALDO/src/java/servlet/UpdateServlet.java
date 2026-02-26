package servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext sc = getServletContext();
        String dbUrl = sc.getInitParameter("dbUrl").concat(sc.getInitParameter("dbName"));
        String dbUser = sc.getInitParameter("dbUsername");
        String dbPass = sc.getInitParameter("dbPassword");
        String dbDriver = sc.getInitParameter("dbDriver");
        try {
            Class.forName(dbDriver);
            String target = request.getParameter("user");
            String current = (String) request.getSession().getAttribute("user");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
                PreparedStatement ps = conn.prepareStatement("UPDATE USERS SET EMAIL = ?, PASSWORD = ?, USERROLE = ? WHERE EMAIL=?");
                String user = request.getParameter("e");
                String role = request.getParameter("r");
                ps.setString(1, user);
                ps.setString(2, request.getParameter("p"));
                ps.setString(3, role);
                ps.executeUpdate();

                if (target.equals(current)) {
                    request.getSession().setAttribute("user", user);
                    request.getSession().setAttribute("role", role);
                }
            } catch (Exception e) {
            }
            response.sendRedirect("success.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
