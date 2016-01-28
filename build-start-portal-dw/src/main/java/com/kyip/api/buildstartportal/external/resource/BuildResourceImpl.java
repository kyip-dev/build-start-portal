package com.kyip.api.buildstartportal.external.resource;

import io.dropwizard.views.View;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.kyip.api.buildstartportal.external.ListBuildProjectsResponse;
import com.kyip.api.buildstartportal.external.MainConfiguration;
import com.kyip.api.buildstartportal.external.datatype.BuildProject;
import com.kyip.api.buildstartportal.external.views.BuildView;

public class BuildResourceImpl implements BuildResource {

	private static final Logger logger = LoggerFactory.getLogger(BuildResourceImpl.class);

	private static final String mvnHome = "C:/Tools/apache-maven-3.0.5/bin/..";

	@Inject
	MainConfiguration configuation;

	@Inject
	private ObjectMapper objectMapper;

	private List<BuildProject> projects;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public View listProject(@PathParam("projectName") String projectName) throws Exception {
		BuildView bv = new BuildView();
		bv.setProjects(getProjects());
		return bv;
	}

	@Override
	public ListBuildProjectsResponse listProjects() throws Exception {
		ListBuildProjectsResponse response = new ListBuildProjectsResponse();
		response.setProjects(getProjects());
		return response;
	}

	@GET
	@Path("/{projectName}")
	@Produces(MediaType.TEXT_HTML)
	public void buildProject(@PathParam("projectName") String projectName, @QueryParam("skipTest") boolean skipTest) throws Exception {
		logger.info("build project [NAME:{}]", projectName);

		// get project path by name
		String projectPath = null;
		for (BuildProject p : getProjects()) {
			if (p.getName().equals(projectName)) {
				projectPath = p.getPath();
			}
		}

		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(new File(projectPath + "/pom.xml"));
		String command = "clean install -e";
		if (skipTest) {
			command = command + " -DskipTests" + " -Dmaven.javadoc.skip=true";
		}
		request.setGoals(Collections.singletonList(command));

		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File(mvnHome));
		InvocationResult result = invoker.execute(request);
		if (result.getExitCode() != 0) {
			throw new IllegalStateException("Build failed.");
		}

	}

	private void buildProjectTree() throws JsonParseException, JsonMappingException, IOException {
		String projectRegistries = configuation.getProjectRegistries();
		logger.info("build tree {}", projectRegistries);
		projects = objectMapper.readValue(projectRegistries, objectMapper.getTypeFactory().constructCollectionType(List.class, BuildProject.class));
	}

	public List<BuildProject> getProjects() throws JsonParseException, JsonMappingException, IOException {
		if (CollectionUtils.isEmpty(projects)) {
			buildProjectTree();
		}
		return projects;
	}
}
