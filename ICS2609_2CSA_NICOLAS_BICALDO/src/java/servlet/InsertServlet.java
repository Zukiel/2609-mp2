package servlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class InsertServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletContext sc = getServletContext();
        String dbUrl = sc.getInitParameter("dbUrl").concat(sc.getInitParameter("dbName"));
        String dbUser = sc.getInitParameter("dbUsername");
        String dbPass = sc.getInitParameter("dbPassword");
        String dbDriver = sc.getInitParameter("dbDriver");
        
        try { 
            Class.forName(dbDriver);
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO USERS VALUES (?,?,?)");
            ps.setString(1, request.getParameter("e"));
            ps.setString(2, request.getParameter("p"));
            ps.setString(3, request.getParameter("r"));
            ps.executeUpdate();
            } 
            catch (Exception e){
                e.printStackTrace();
            }
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        }
        
        response.sendRedirect("success.jsp");
    }
}