package com.apex.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Servlet implementation class DBconnection
 */
@WebServlet("/DBconnection")
public class DBconnection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DBconnection db = null;
	private Connection dbConn = null;
   
    private DBconnection() {
    	// Database Connection
    	String Dburl="jdbc:mysql://localhost:3306/";
    	String Dbname="mydb";
    	String Dbusername="root";
    	String Dbpassword="root";
    	
    	StringBuffer sb=new StringBuffer(Dburl);
    	try {
    		String dbDriver="com.mysql.cj.jdbc.Driver";
    		Class.forName(dbDriver);
    		dbConn=DriverManager.getConnection(sb.append(Dbname).toString(),Dbusername,Dbpassword);
    		System.out.println(" connnection made ");
    		
    	}catch(ClassNotFoundException e1) {
    		e1.printStackTrace();
    	}catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
       
    }
    
	public static DBconnection getInstance() {
           if (null == db) {
			synchronized (DBconnection.class) {
				if (null == db) {
					db = new DBconnection();
				}
			}
		}

		return db;
	}

	public final Connection getDbConn() {
		return dbConn;
	}
    
    
    @Override
	public void finalize() throws Throwable {
		dbConn.close();
		System.out.println("DB Connection closed successfully");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	


}
