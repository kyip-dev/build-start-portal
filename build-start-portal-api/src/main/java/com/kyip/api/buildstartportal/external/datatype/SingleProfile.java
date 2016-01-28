/**
 * Copyright (C) 2015 Powa Technologies
 * All rights reserved.
 */
package com.kyip.api.buildstartportal.external.datatype;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SingleProfile {

	@JsonProperty("name")
	private String name;

	@JsonProperty("url")
	private String url;

	@JsonProperty("user")
	private String user;

	@JsonProperty("password")
	private String password;

	@JsonProperty("database")
	private List<Database> database;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Database> getDatabase() {
		return database;
	}

	public void setDatabase(List<Database> database) {
		this.database = database;
	}

}
