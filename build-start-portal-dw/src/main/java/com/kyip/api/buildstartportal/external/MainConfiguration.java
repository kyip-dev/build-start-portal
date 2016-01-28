package com.kyip.api.buildstartportal.external;

import io.dropwizard.Configuration;

import com.google.common.collect.ImmutableMap;

public class MainConfiguration extends Configuration {
	private ImmutableMap<String, ImmutableMap<String, String>> viewRendererConfiguration = ImmutableMap.of();

	private String projectRegistries;

	private String profileRegistries;

	/**
	 * @return the viewRendererConfiguration
	 */
	public ImmutableMap<String, ImmutableMap<String, String>> getViewRendererConfiguration() {
		return viewRendererConfiguration;
	}

	/**
	 * @param viewRendererConfiguration
	 *            the viewRendererConfiguration to set
	 */
	public void setViewRendererConfiguration(ImmutableMap<String, ImmutableMap<String, String>> viewRendererConfiguration) {
		this.viewRendererConfiguration = viewRendererConfiguration;
	}

	public String getProjectRegistries() {
		return projectRegistries;
	}

	public void setProjectRegistries(String projectRegistries) {
		this.projectRegistries = projectRegistries;
	}

	public String getProfileRegistries() {
		return profileRegistries;
	}

	public void setProfileRegistries(String profileRegistries) {
		this.profileRegistries = profileRegistries;
	}

}
