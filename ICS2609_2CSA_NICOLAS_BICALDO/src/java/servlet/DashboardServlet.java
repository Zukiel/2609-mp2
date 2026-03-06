package servlet;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DashboardServlet extends HttpServlet {
    private String dbUrl, dbUser, dbPass, dbDriver;

    public void init() throws ServletException {
        ServletConfig sc = getServletConfig();
        dbUrl = sc.getInitParameter("dbUrl");
        dbUser = sc.getInitParameter("dbUser");
        dbPass = sc.getInitParameter("dbPassword");
        dbDriver = sc.getInitParameter("dbDriver");
        try { Class.forName(dbDriver); } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String currentUser = (String) session.getAttribute("user");
        String currentRole = (String) session.getAttribute("role");
        
        List<Map<String, String>> userList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            String sql = currentRole.equalsIgnoreCase("admin") 
                         ? "SELECT EMAIL, USERROLE FROM USERS" 
                         : "SELECT EMAIL, USERROLE FROM USERS WHERE EMAIL=?";
            
            ps = conn.prepareStatement(sql);
            if (!currentRole.equalsIgnoreCase("admin")) {
                ps.setString(1, currentUser);
            }
            
            rs = ps.executeQuery();
            while(rs.next()){
                Map<String, String> row = new HashMap<>();
                row.put("email", rs.getString("EMAIL"));
                row.put("role", rs.getString("USERROLE"));
                userList.add(row);
            }
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("DB Error", e);
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}