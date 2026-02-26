package servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteServlet extends HttpServlet {

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
            if (!target.equals(current)) {
                try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM USERS WHERE EMAIL=?");
                    ps.setString(1, target);
                    ps.executeUpdate();
                } catch (Exception e) {
                }
            }
            response.sendRedirect("success.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
