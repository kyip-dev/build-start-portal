package com.kyip.api.buildstartportal.external.datatype;

import java.util.List;

public class StartProject extends SingleProject {

	private Integer port;

	private boolean started;

	private Integer pid; // for stop process

	private List<Database> database; // for replace yml

	private String log; // for show log

	public List<Database> getDatabase() {
		return database;
	}

	public void setDatabase(List<Database> database) {
		this.database = database;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
