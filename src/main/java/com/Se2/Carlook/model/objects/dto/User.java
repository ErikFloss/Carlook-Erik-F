package com.Se2.Carlook.model.objects.dto;

import java.util.List;

public class User {


	private String name=null;
	private List<Auto> autos;
	private String passwort =null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void addAuto(Auto auto) {
		autos.add(auto);
	}
	
	public List<Auto> returnReseviert(){
		return autos;
	}
	
	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	
}
