package com.Se2.Carlook.process.control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Se2.Carlook.model.objects.dto.Auto;
import com.Se2.Carlook.model.objects.dto.User;
import com.Se2.Carlook.services.db.JDBCConnection;
import com.Se2.Carlook.services.util.Roles;
import com.Se2.Carlook.services.util.Views;
import com.vaadin.ui.UI;

public class ReservierenControl {
	
	private ReservierenControl() {
		
	}
	public static ReservierenControl reservieren =null;
	
	public static ReservierenControl init() {
		if(reservieren==null) {
			reservieren=new ReservierenControl();
			return reservieren;
		}else {
			return reservieren;
		}
	}
	
	public void reservieren(Auto auto) {
		User user=(User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
		Statement statement = JDBCConnection.getInstance().getStatement();
		try {
			//result= statement.executeQuery("SELECT * FROM carlookef.auto WHERE carlookef.auto.reserviertvon =\'" + null +"\'");

			statement.executeQuery("UPDATE carlookef.auto SET reserviertvon =\'" + user.getName() + "\' WHERE carlookef.auto.id = \'" + auto.getid() + "\'");
		} catch (SQLException e) {
			//Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,e);
		}
	}
	
	public List<Auto> reserviert(){
		User user=(User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
		ArrayList<Auto> liste = new ArrayList<Auto>();
		Auto auto =null;
		ResultSet set= null;
		Statement statement = JDBCConnection.getInstance().getStatement();
		try {
			set=statement.executeQuery("SELECT * FROM carlookef.auto WHERE carlookef.auto.reserviertvon =\'"+ user.getName() +"\'");
			
		} catch (SQLException e) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,e);
		}
		try {
			while(set.next()) {
				auto=new Auto();
				auto.setMarke(set.getString(1));
				auto.setBaujahr(set.getString(2));
				auto.setInfo(set.getString(3));
				auto.setId(set.getString(4));
				
				liste.add(auto);
			}
		} catch (SQLException e) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,e);
		}
		
		return liste;
		
	}
	
	public void entfernen(Auto auto) {
		User user=(User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
		Statement statement = JDBCConnection.getInstance().getStatement();
		try {
			statement.executeQuery("UPDATE carlookef.auto SET reserviertvon =\'NULL\' WHERE carlookef.auto.id = \'" + auto.getid() + "\'");
		} catch (SQLException e) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,e);
		}
		
	}
	
}
