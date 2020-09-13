package com.Se2.Carlook.gui.views;

import java.sql.SQLException;

import com.Se2.Carlook.process.control.LoginControl;
import com.Se2.Carlook.process.control.RegisterControl;
import com.Se2.Carlook.process.control.exceptions.NoSuchUserOrPassword;
import com.Se2.Carlook.process.control.exceptions.UserExists;
import com.Se2.Carlook.services.util.Views;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Widgetset("com.vaadin.v7.Vaadin7WidgetSet") 

public class LoginView extends VerticalLayout implements View {
	
	public void setUp() {
		
		this.setSizeFull();
		
		final Panel panel = new Panel("Bitte Logindaten eingeben");
		panel.addStyleName("login");
		
		
		final TextField userLogin = new TextField();
		userLogin.setCaption("Email");
		
		final PasswordField passwordfield = new PasswordField("Passwort");
		passwordfield.setCaption("Passwort");
		
		final Button button = new Button("Einloggen");
		button.addStyleName(ValoTheme.BUTTON_PRIMARY);
		button.setClickShortcut(KeyCode.ENTER,null);
		
		button.addClickListener(new Button.ClickListener() {
			
			
			public void buttonClick(Button.ClickEvent event) {
				
				String login= userLogin.getValue();
				String pw = passwordfield.getValue();
				if(login.equals("")||pw.equals("")) {
					Notification.show("Fehler","keine leeren Eingaben",Notification.Type.ERROR_MESSAGE);
					return;
				}else {
				
				try {
					
					LoginControl.checkAuthentifizierung(login, pw);
					
				}catch (NoSuchUserOrPassword ex) {
					
					Notification.show("Fehler","Benutzername oder Passwort falsch!",Notification.Type.ERROR_MESSAGE);
					userLogin.setValue("");
					passwordfield.setValue("");
				}
				
				
			}}
		});
		
		
		
		final Button reg = new Button("Registrieren");
		
		reg.addClickListener(new Button.ClickListener() {
			
			
			public void buttonClick(Button.ClickEvent event) {
			UI.getCurrent().getNavigator().navigateTo(Views.REGISTER);	
				
				
				
			}
		});
		
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(userLogin,passwordfield,new Label("&nbsp", ContentMode.HTML),button,reg);
		
		
		
		
				
		this.addComponents(layout,panel);
		this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
		panel.setContent(layout);
		panel.setSizeUndefined();
		
		layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(userLogin, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(passwordfield,Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(reg, Alignment.MIDDLE_CENTER);
		
		
		
	}



	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
		
	}

}
