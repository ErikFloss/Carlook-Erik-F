package com.Se2.Carlook.gui.views;

import java.util.List;

import com.Se2.Carlook.model.objects.dto.Auto;
import com.Se2.Carlook.process.control.AutoSuche;
import com.Se2.Carlook.process.control.ReservierenControl;
import com.Se2.Carlook.services.util.Views;
import com.vaadin.v7.data.util.BeanContainer;
import com.vaadin.v7.data.util.BeanItem;
import com.vaadin.v7.event.ItemClickEvent;
import com.vaadin.annotations.Widgetset;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@Widgetset("com.vaadin.v7.Vaadin7WidgetSet") 

public class UserView extends VerticalLayout implements View{
	
	private Auto autoselektiert = null;
	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setup();
		
	}
	
	public void setup() {
			
			HorizontalLayout horizont = new HorizontalLayout();
		
			Button logout = new Button("Abmelden");
			logout.addClickListener(new Button.ClickListener(){
	       		public void buttonClick(ClickEvent event) {
	       			UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
	       		}
	       	});
			
			Button suche = new Button("zurück zur Suche");
			suche.addClickListener(new Button.ClickListener(){
	       		public void buttonClick(ClickEvent event) {
	       			UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
	       		}
	       	});
			
			horizont.addComponents(suche,logout);
			//addComponent(new Label("&nbsp", ContentMode.HTML));
			addComponent(horizont);
			setComponentAlignment(horizont, Alignment.TOP_LEFT);
			
			
			
			
		 	BeanContainer<Integer,Auto> data = new BeanContainer<Integer,Auto>(Auto.class);
	        data.setBeanIdProperty("id");
	        
	        Table tabelle = new Table("Reserviert", data);
	        tabelle.setSizeFull();
	        tabelle.setSelectable(true);
	        
	        Button entfernen = new Button("Entfernen");
	        entfernen.setClickShortcut(KeyCode.ENTER,null);
	        entfernen.addStyleName(ValoTheme.BUTTON_PRIMARY);
	        
	        entfernen.addClickListener(new Button.ClickListener() {
	        	
	        	public void buttonClick(ClickEvent event) {
	        		if(autoselektiert==null) {
	        			Notification.show("Fehler","Kein Auto ausgewählt",Notification.Type.ERROR_MESSAGE);
	        			return;
	        		}else {
	        			// auto reservieren
	        			System.out.println("Auto selektiert: "+autoselektiert.getmarke());
	        			ReservierenControl.init().entfernen(autoselektiert);
	        			Notification.show(null,autoselektiert.getmarke()+" wurde entfernt", Notification.Type.WARNING_MESSAGE);
	        			UI.getCurrent().getNavigator().navigateTo(Views.USER);
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
	        
	        
	        List<Auto> liste = ReservierenControl.init().reserviert();
			
			if(liste.size()==0) {
				Notification.show(null,"keine Reservierungen", Notification.Type.WARNING_MESSAGE);
				removeComponent(tabelle);
				removeComponent(entfernen);

				}else{
			
				addComponent(tabelle);
			
			
				data.removeAllItems();
				data.addAll(liste);
			
				tabelle.setPageLength(tabelle.size());
				tabelle.setCaption("insgesamt ( "+ tabelle.size() +" )"+"Reservierungen" );
				addComponent(new Label("&nbsp", ContentMode.HTML));
				addComponent(entfernen);
				setComponentAlignment(entfernen, Alignment.MIDDLE_CENTER);
				
	        }
			
	        
	}

}
