package com.kyip.api.buildstartportal.external.views;

import io.dropwizard.views.View;

import java.nio.charset.StandardCharsets;

public class BaseView extends View {

	private String sectionTemplatePath;

	private String scriptPath;

	public BaseView() {
		super("/views/global-template.ftl", StandardCharsets.UTF_8);
	}

	public String getSectionTemplatePath() {
		return sectionTemplatePath;
	}

	public void setSectionTemplatePath(String sectionTemplatePath) {
		this.sectionTemplatePath = sectionTemplatePath;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}

}
