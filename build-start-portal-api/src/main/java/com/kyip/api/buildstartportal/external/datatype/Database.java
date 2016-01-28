package com.kyip.api.buildstartportal.external.datatype;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Database {

	@JsonProperty("name")
	private String name;

	@JsonProperty("url")
	private String url;

	@JsonProperty("user")
	private String user;

	@JsonProperty("password")
	private String password;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
