package com.kyip.api.buildstartportal.external;

import java.io.Serializable;
import java.util.List;

import com.kyip.api.buildstartportal.external.datatype.BuildProject;

public class ListBuildProjectsResponse implements Serializable {

	private static final long serialVersionUID = 68435097504419578L;

	private List<BuildProject> projects;

	public List<BuildProject> getProjects() {
		return projects;
	}

	public void setProjects(List<BuildProject> projects) {
		this.projects = projects;
	}

}
