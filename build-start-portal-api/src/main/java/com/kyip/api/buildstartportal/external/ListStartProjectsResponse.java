package com.kyip.api.buildstartportal.external;

import java.io.Serializable;
import java.util.List;

import com.kyip.api.buildstartportal.external.datatype.StartProject;

public class ListStartProjectsResponse implements Serializable {

	private static final long serialVersionUID = 68435097504419578L;

	private List<StartProject> projects;

	public List<StartProject> getProjects() {
		return projects;
	}

	public void setProjects(List<StartProject> projects) {
		this.projects = projects;
	}

}
