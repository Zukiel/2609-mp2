package servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import exception.*;

public class LoginServlet extends HttpServlet {

    private String dbUrl, dbUser, dbPass, dbDriver;

    public void init() throws ServletException {
        ServletConfig sc = getServletConfig();
        dbUrl = sc.getInitParameter("dbUrl");
        dbUser = sc.getInitParameter("dbUser");
        dbPass = sc.getInitParameter("dbPassword");
        dbDriver = sc.getInitParameter("dbDriver");
        
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Driver not found", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if (isInvalid(user) && isInvalid(pass)) {
            throw new NullValueException("Missing Fields");
        }
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM USERS WHERE email = ?")) {
            
            ps.setString(1, user);
            
            try (ResultSet rs = ps.executeQuery()) {
                boolean validUser = rs.next();
                
                if (!validUser && isInvalid(pass)) {
                    response.sendRedirect("error_1.jsp");
                    return;
                }
                if(!validUser)
                {
                    response.sendRedirect("error_3.jsp");
                    return;
                }

                String storedPass = rs.getString("password");
                String role = rs.getString("userrole");
                
                if (storedPass.equals(pass)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("role", role);
                    response.sendRedirect("DashboardServlet");
                    return;
                } else {
                    throw new AuthenticationException("Incorrect Username and Password");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        } catch (AuthenticationException e) {
            response.sendRedirect("error_2.jsp");
            return;
        }
    }

    private boolean isInvalid(String input) {
        return input == null || input.trim().isEmpty();
    }
}