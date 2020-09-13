package com.Se2.Carlook.gui.views;

import java.sql.SQLException;

import com.Se2.Carlook.process.control.RegisterControl;
import com.Se2.Carlook.services.util.Views;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Widgetset("com.vaadin.v7.Vaadin7WidgetSet") 

public class RegistrierView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		this.setup();
		
	}
	
	public void setup() {
		final Panel panel = new Panel("Bitte Emailadresse, Benutzernamen und Passwort festlegen");
		panel.addStyleName("Register");
		
		
		final TextField email = new TextField();
		email.setCaption("Emailadresse");
		
		final TextField benutzername = new TextField();
		benutzername.setCaption("Benutzername");
		
		final PasswordField passwordfield = new PasswordField("Passwort");
		passwordfield.setCaption("Passwort");
		
		final Button button = new Button("Registrieren");
		button.addStyleName(ValoTheme.BUTTON_PRIMARY);
		button.setClickShortcut(KeyCode.ENTER,null);
		
		final Button zurueck = new Button("zurück");
	
		zurueck.addClickListener(new Button.ClickListener() {
			public void buttonClick(Button.ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
			}
		});
		
		button.addClickListener(new Button.ClickListener() {
			
			
			public void buttonClick(Button.ClickEvent event) {
				
				String emailadresse = email.getValue();
				String login = benutzername.getValue();
				String pw = passwordfield.getValue();
				String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
				java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailPattern);
				java.util.regex.Matcher m = p.matcher(emailadresse);
				boolean isvalid= m.matches();
				
				if(emailadresse.equals("")||login.equals("")||pw.equals("")){
					Notification.show("Fehler","Bitte keine LEEREN Eingaben!",Notification.Type.ERROR_MESSAGE);
					return;
				}
				if(isvalid==false) {
					Notification.show("Fehler","Es werden nur gültige Emailadressen akzeptiert",Notification.Type.ERROR_MESSAGE);
					return;
				}else {
			
				try {
					RegisterControl.createNewUser(login, pw, emailadresse);
					UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				
				
				
			}
		});
		
		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(email,new Label("&nbsp", ContentMode.HTML),benutzername,new Label("&nbsp", ContentMode.HTML),passwordfield,new Label("&nbsp", ContentMode.HTML),button);
		HorizontalLayout horizont = new HorizontalLayout();
		horizont.addComponents(zurueck,new Label("&nbsp", ContentMode.HTML));

		
		
		this.addComponent(new Label("&nbsp", ContentMode.HTML));
		this.addComponents(horizont,new Label("&nbsp", ContentMode.HTML));	
		this.setComponentAlignment(horizont, Alignment.TOP_RIGHT);
		this.addComponents(new Label("&nbsp", ContentMode.HTML),layout,panel);
		this.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
		panel.setContent(layout);
		panel.setSizeUndefined();
		
		layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(benutzername, Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(passwordfield,Alignment.MIDDLE_CENTER);
		layout.setComponentAlignment(email, Alignment.MIDDLE_CENTER);
	}
	
	
}
