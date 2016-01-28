package com.kyip.api.buildstartportal.external.views;

import java.util.List;

import com.kyip.api.buildstartportal.external.datatype.StartProject;

public class StartupView extends BaseView {

	private List<StartProject> projects;

	public StartupView() {
		this.setSectionTemplatePath("/views/buildStart/start.ftl");
		// this.setScriptPath("/assets/js/buildStart/start_app");
		this.setScriptPath("/assets/js/buildStart/require-config-start");
	}

	public List<StartProject> getProjects() {
		return projects;
	}

	public void setProjects(List<StartProject> projects) {
		this.projects = projects;
	}

}
