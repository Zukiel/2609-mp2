package servlet;

import exception.*;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class LoginServlet extends HttpServlet {
    private String dbUrl, dbUser, dbPass, dbDriver;

    public void init() throws ServletException {
        ServletContext sc = getServletContext();
        dbUrl = sc.getInitParameter("dbUrl").concat(sc.getInitParameter("dbName"));
        dbUser = sc.getInitParameter("dbUsername");
        dbPass = sc.getInitParameter("dbPassword");
        dbDriver = sc.getInitParameter("dbDriver");
        try { 
            Class.forName(dbDriver);
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if ((user == null || user.trim().isEmpty()) && (pass == null || pass.trim().isEmpty())) {
            try { 
                throw new NullValueException("Empty Fields"); 
            } 
            catch (NullValueException e) {
                response.sendRedirect("noLoginCredentials.jsp"); 
                return; 
            }
        }

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ?");
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                if (pass.isEmpty()) response.sendRedirect("error_1.jsp");
                else response.sendRedirect("error_3.jsp");
            } else {
                if (rs.getString("password").equals(pass)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("role", rs.getString("role"));
                    response.sendRedirect("success.jsp");
                } else {
                    response.sendRedirect("error_2.jsp");
                }
            }
        } 
        catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }
}

