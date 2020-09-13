package com.Se2.Carlook.process.control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Se2.Carlook.model.objects.dto.User;
import com.Se2.Carlook.process.control.exceptions.NoSuchUserOrPassword;
import com.Se2.Carlook.services.db.JDBCConnection;
import com.Se2.Carlook.services.util.Roles;
import com.Se2.Carlook.services.util.Views;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class LoginControl {
	
	
	
	public static void checkAuthentifizierung(String login, String pw) throws NoSuchUserOrPassword {
		
		ResultSet set= null;
		
		try {
			
			Statement statement = JDBCConnection.getInstance().getStatement();
			
			set= statement.executeQuery("SELECT * FROM carlookef.user WHERE carlookef.user.email = \'" + login + "\'" + "AND carlookef.user.password =\'" + pw + "\'");
			
			
			
			
		}catch(SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,ex);
		}
		
		User user =null;
		try {
			if(set.next()) {
				
				user = new User();
				user.setName(set.getString(1));
				
			}else {
				throw new NoSuchUserOrPassword("ERROR");
			}
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,ex);
		}finally {
			JDBCConnection.getInstance().closeConnection();
		}
		
		
		VaadinSession session = UI.getCurrent().getSession();
		session.setAttribute(Roles.CURRENT_USER, user);
		
		UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
	}

}
