package com.apex.servlets;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
    	response.getWriter().append("Served at: "+username+password);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");

            // Prepare the SQL statement
            String sql = "SELECT username,password FROM mydb.users WHERE username=? AND password=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            

            // Execute the query
            rs = pstmt.executeQuery();

            // Check if username and password are correct
            if (rs.next()) {
            	out.println("Data in Db");
            	response.getWriter().append("Served at: ");
                // If correct, redirect to the success page
//                response.sendRedirect(/Shopping/Shopping);
            	 RequestDispatcher rd=request.getRequestDispatcher("Shopping.html");
                 rd.include(request, response);
            } else {
                // If incorrect, display an error message
            	response.getWriter().append("Served at:" );
                out.println("Invalid username or password. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        } finally {
            try {
                // Close the database resources
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                out.println("Error: " + e.getMessage());
            }
        }
    }
}
