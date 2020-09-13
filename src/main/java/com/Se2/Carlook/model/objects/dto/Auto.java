package com.Se2.Carlook.model.objects.dto;

import java.sql.Date;

public class Auto implements java.io.Serializable{

	
private String marke;
private String baujahr;
private String info;
private String id;


	
public Auto()	{

}


public String getMarke() {
	return marke;
}

public void setMarke(String marke) {
	this.marke = marke;
}

public String getBaujahr() {
	return baujahr;
}

public void setBaujahr(String string) {
	this.baujahr = string;
}

public String getInfo() {
	return info;
}

public void setInfo(String info) {
	this.info = info;
}

public String getId() {
	return id;
}

public void setId(String string) {
	this.id = string;
}


public String getmarke() {
	return marke;
}

public String getbaujahr() {
	return baujahr;
}

public String getinfo() {
	return info;
}

public String getid() {
	return id;
}




}
