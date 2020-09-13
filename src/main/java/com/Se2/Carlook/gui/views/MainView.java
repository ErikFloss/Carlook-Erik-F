package com.Se2.Carlook.gui.views;

import java.util.List;


import com.Se2.Carlook.model.objects.dto.Auto;
import com.Se2.Carlook.model.objects.dto.User;
import com.Se2.Carlook.process.control.AutoSuche;
import com.Se2.Carlook.process.control.ReservierenControl;
import com.Se2.Carlook.services.util.Roles;
import com.Se2.Carlook.services.util.Views;
import com.vaadin.v7.data.util.BeanContainer;
import com.vaadin.v7.data.util.BeanItem;
import com.vaadin.v7.event.ItemClickEvent;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

@Widgetset("com.vaadin.v7.Vaadin7WidgetSet") 

public class MainView extends VerticalLayout implements View{
	
	//private int anzahlSuche= 0;
	private Auto autoselektiert = null;


	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
		
	}
	 @SuppressWarnings({ "deprecation", "serial" })
	public void setUp()	{
		 
	        setMargin(true);
	        setSpacing(true);
	        
	        
	        HorizontalLayout horizont = new HorizontalLayout();
	        HorizontalLayout horizont2 = new HorizontalLayout();
	        
	        User user=(User) UI.getCurrent().getSession().getAttribute(Roles.CURRENT_USER);
	       
	       	Button logout = new Button("Abmelden");
	       	Button profil = new Button(user.getName()+"'s Profil");
	       	
	       	logout.addClickListener(new Button.ClickListener(){
	       		public void buttonClick(ClickEvent event) {
	       			UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
	       		}
	       	});
	       	
	       	profil.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					UI.getCurrent().getNavigator().navigateTo(Views.USER);
					
				}
			});
	       

	        Button button = new Button("Suche", FontAwesome.SEARCH);
	        button.setClickShortcut(KeyCode.ENTER,null);
	        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
	        
	        
	        TextField textfield = new TextField();
	        Label labelText = new Label("Willkommen "+user.getName()+", hier kannst du Nach Autos suchen: ");
	        
	        BeanContainer<Integer,Auto> data = new BeanContainer<Integer,Auto>(Auto.class);
	        data.setBeanIdProperty("id");
	        
	        Table tabelle = new Table("Treffer", data);
	        tabelle.setSizeFull();
	        tabelle.setSelectable(true);
	        
	        BeanContainer<Integer,Auto> data2 = new BeanContainer<Integer,Auto>(Auto.class);
	        data2.setBeanIdProperty("id");
	        
	        Table reservierteautos = new Table("Reservierungen",data2);
	        reservierteautos.setSizeFull();
	        reservierteautos.setSelectable(true);
	        
	        Button reservieren = new Button("Reservieren");
	        
	        reservieren.addClickListener(new Button.ClickListener() {
	        	
	        	public void buttonClick(ClickEvent event) {
	        		if(autoselektiert==null) {
	        			return;
	        		}else {
	        			// auto reservieren
	        			System.out.println("Auto selektiert: "+autoselektiert.getmarke());
	        			ReservierenControl.init().reservieren(autoselektiert);
	        			Notification.show(null,autoselektiert.getmarke()+" wurde reserviert", Notification.Type.WARNING_MESSAGE);
	        		}
	        		
	        	}
	        });
	        	 
	        tabelle.addItemClickListener(new ItemClickEvent.ItemClickListener() {
				
				@Override
				public void itemClick(ItemClickEvent event) {
					System.out.println("Zeile: "+ event.getItemId().toString());
					BeanItem<Auto> autoBean = data.getItem(event.getItemId());
					autoselektiert = autoBean.getBean();
				}
			});
	        
	        button.addClickListener(new Button.ClickListener() {
				
				

				@Override
				public void buttonClick(ClickEvent event) {
					String marke = textfield.getValue();
				
					if(marke.equals("")) {
						Notification.show(null,"keine Eingabe", Notification.Type.WARNING_MESSAGE);
					}else{
						
						
						//MainView.this.anzahlSuche++;
						
						List<Auto> liste = AutoSuche.init().getAutoByMarke(marke);
						
						if(liste.size()==0) {
							Notification.show(null,"keine Treffer", Notification.Type.WARNING_MESSAGE);
							removeComponent(tabelle);
							removeComponent(reservieren);
						}else{
						
						addComponent(tabelle);
						
						
						data.removeAllItems();
						data.addAll(liste);
						
						tabelle.setPageLength(tabelle.size());
						tabelle.setCaption("Treffer für " + marke + " (Anzahl der Treffer: "+ tabelle.size() +" )" );
						
						addComponent(reservieren);
						setComponentAlignment(reservieren, Alignment.MIDDLE_CENTER);
						
					
						
						
						}
					}
					
				}
			});
	        
	        horizont.addComponents(labelText,textfield,button );
	        horizont.setComponentAlignment(labelText, Alignment.MIDDLE_CENTER);
	       
	        
	        
	       horizont2.addComponents(profil,logout);
	       addComponents(horizont2,horizont);
	       
	       setComponentAlignment(horizont, Alignment.MIDDLE_CENTER);
	       setComponentAlignment(horizont2, Alignment.TOP_LEFT);

	        
	        
	        
	        
	        
	    }
	private static void setContent(VerticalLayout layout) {
		// TODO Auto-generated method stub
		
	}

}
