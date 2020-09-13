package com.Se2.Carlook.gui.ui;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.xml.crypto.Data;

import com.Se2.Carlook.gui.views.LoginView;
import com.Se2.Carlook.gui.views.MainView;
import com.Se2.Carlook.gui.views.RegistrierView;
import com.Se2.Carlook.gui.views.UserView;
import com.Se2.Carlook.model.objects.dto.Auto;
import com.Se2.Carlook.process.control.AutoSuche;
import com.Se2.Carlook.process.control.RegisterControl;
import com.Se2.Carlook.services.util.Views;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.v7.data.util.BeanContainer;
import com.vaadin.v7.data.util.BeanItem;
import com.vaadin.v7.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.AbstractErrorMessage.ContentMode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.v7.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@Title("Carlook")
@Theme("mytheme")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet") 

public class MyUI extends UI {


	@Override
    protected void init(VaadinRequest vaadinRequest) {
    
		Navigator navi = new Navigator(this,this);
		navi.addView(Views.MAIN, MainView.class);
		navi.addView(Views.LOGIN, LoginView.class);
		navi.addView(Views.USER, UserView.class);
		navi.addView(Views.REGISTER, RegistrierView.class);
		
		getUI().getCurrent().getNavigator().navigateTo(Views.LOGIN);
	}
    	

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
