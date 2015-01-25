package com.hb.models;

import java.io.Serializable;

public class UserModel implements Serializable {

	private static final long serialVersionUID = -4083854273590537247L;
	
	private String id;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
