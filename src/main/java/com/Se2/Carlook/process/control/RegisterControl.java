package com.Se2.Carlook.process.control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Se2.Carlook.process.control.exceptions.NoSuchUserOrPassword;
import com.Se2.Carlook.services.db.JDBCConnection;
import com.Se2.Carlook.services.util.Views;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class RegisterControl {

	public static boolean checkIfalreadyExists(String user, String pw,String email) throws SQLException {
		ResultSet set= null;
		Statement statement = JDBCConnection.getInstance().getStatement();
		
		try {
			System.out.println(user);
			set= statement.executeQuery("SELECT * FROM carlookef.user WHERE carlookef.user.name = \'" + user + "\'"+ "AND carlookef.user.password =\'" + pw + "\'" + "OR carlookef.user.email =\'" + email + "\'");
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,ex);
		}
		
			if(set.next()) {
				Notification.show("Fehler","Bereits einen Account mit dem Benutzernamen gefunden",Notification.Type.ERROR_MESSAGE);
				return true;
			}else {
				System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIEEEEEEEEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR1");
				return false;
			}
		}
		
		

	
	public static void createNewUser(String user, String pw,String email) throws SQLException {
		Statement statement = JDBCConnection.getInstance().getStatement();
		boolean erg =checkIfalreadyExists(user, pw, email);
		
		if(erg==false) {
			try {
				System.out.println("HIIIIIIIIIIIIIIIIIIIIIIIEEEEEEEEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR2");
				Notification.show("Glückwunsch, "+ user +" dein Konto wurde erstellt!",Notification.Type.WARNING_MESSAGE);
				statement.executeQuery("INSERT INTO carlookef.user (name, password, email) VALUES (\'" + user+ "\'," + "\'" + pw + "\'," + "\'" + email + "\')");
				
				
			} catch (SQLException ex) {
				
			}
		}
		
	}
}
