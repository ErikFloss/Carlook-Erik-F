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
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class AutoSuche {
	
	

	private AutoSuche() {
		
	}
	
	public static AutoSuche autosuche = null;
	
	public static AutoSuche init() {
		if(autosuche==null) {
			autosuche = new AutoSuche();
		}
		return autosuche;
	}
	
	public List<Auto> getAutoByMarke (String suche){
		User user=(User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
		
		ResultSet result =null;
		Statement statement = JDBCConnection.getInstance().getStatement();
		try {
			result= statement.executeQuery("SELECT * FROM carlookef.auto WHERE carlookef.auto.marke = \'" + suche + "\'" +" Or carlookef.auto.baujahr = \'" + suche + "\'");
		} catch (SQLException e) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,e);
		}
		
		ArrayList<Auto> liste = new ArrayList<Auto>();

		Auto auto =null;
		int zaehler =0;
		
		//VaadinSession session = UI.getCurrent().getSession();
		//String user =(String) session.getAttribute(Roles.CURRENT_USER);
		
		try {
			while(result.next()) {
				zaehler++;
				System.out.println(zaehler);
				auto=new Auto();
				auto.setMarke(result.getString(1));
				auto.setBaujahr(result.getString(2));
				auto.setInfo(result.getString(3));
				auto.setId(result.getString(4));
				if(result.getString(5)==null||result.getString(5).equals(user.getName())||result.getString(5).equals("NULL")) {
					liste.add(auto);
				}
			}
		} catch (SQLException e) {
			Logger.getLogger(JDBCConnection.class.getName()).log(Level.SEVERE,null,e);
		}
		
		
		
		

		
		
		return liste;
		
		
		
	}
	
}
