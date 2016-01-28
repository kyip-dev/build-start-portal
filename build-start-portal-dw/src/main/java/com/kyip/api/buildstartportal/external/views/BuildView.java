package com.kyip.api.buildstartportal.external.views;

import java.util.List;

import com.kyip.api.buildstartportal.external.datatype.BuildProject;

public class BuildView extends BaseView {

	private List<BuildProject> projects;

	public BuildView() {
		this.setSectionTemplatePath("/views/buildStart/build.ftl");
		this.setScriptPath("/assets/js/buildStart/require-config-build");
	}

	public List<BuildProject> getProjects() {
		return projects;
	}

	public void setProjects(List<BuildProject> projects) {
		this.projects = projects;
	}

}
