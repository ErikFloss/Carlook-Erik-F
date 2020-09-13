package com.Se2.Carlook.services.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCConnection {

	private static JDBCConnection connection = null;
	private Connection conn;
	private String login="efloss2s";
	private String pw ="efloss2s";
	private String url= "jdbc:postgresql://dumbo.inf.h-brs.de:5432/efloss2s";
	
	public static JDBCConnection getInstance() {
		if(connection==null) {
			connection = new JDBCConnection();
		}
		return connection;
	}
	
	private JDBCConnection()  {
		this.initConnection();
	}
	
	
	public void initConnection(){
		
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,ex);
		}
		this.openConnection();
		
		
	}
	
	public void openConnection() {
		try {	
			
		/*if(!conn.isClosed()) {
			return;
		}*/
		Properties probs = new Properties();
		probs.setProperty("user", "efloss2s");
		probs.setProperty("password", "efloss2s");	
		
		conn = DriverManager.getConnection(url,probs);
		
		}catch(SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,ex);
		}
	}
		
	/*	Statement st;
		st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT mark * FROM carlookef.auto");
		
		while(rs.next()){
			System.out.println("Login: "+rs.getString(1)+" )");
		} */
	
	
	public Statement getStatement() {
		
		try {
			
			if(conn.isClosed()) {
				
				openConnection();
			}
			return this.conn.createStatement();
			
		} catch (SQLException ex) {
			
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,ex);
			
			return null;
		}
		
		
	}
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,ex);
		}
	}
}
