package com.kyip.api.buildstartportal.external.resource;

import io.dropwizard.views.View;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.kyip.api.buildstartportal.external.ListProfilesResponse;
import com.kyip.api.buildstartportal.external.ListStartProjectsResponse;
import com.kyip.api.buildstartportal.external.MainConfiguration;
import com.kyip.api.buildstartportal.external.StartProjectRequest;
import com.kyip.api.buildstartportal.external.datatype.ProcessMapping;
import com.kyip.api.buildstartportal.external.datatype.SingleProfile;
import com.kyip.api.buildstartportal.external.datatype.StartProject;
import com.kyip.api.buildstartportal.external.util.CommandUtil;
import com.kyip.api.buildstartportal.external.util.FileUtil;
import com.kyip.api.buildstartportal.external.util.ProcessBuilderUtil;
import com.kyip.api.buildstartportal.external.util.SettingUtil;
import com.kyip.api.buildstartportal.external.util.ThreadUtil;
import com.kyip.api.buildstartportal.external.views.StartupView;
import com.kyip.api.buildstartportal.external.websocket.WebSocketTailerListener;

public class StartupResourceImpl implements StartupResource {

	private static final Logger logger = LoggerFactory.getLogger(StartupResourceImpl.class);

	@Inject
	MainConfiguration configuation;

	@Inject
	private ObjectMapper objectMapper;

	private List<StartProject> projects;

	private List<SingleProfile> profiles;

	private final ProcessMapping processMapping = new ProcessMapping();

	@Inject
	CommandUtil commandUtil;

	@Inject
	ProcessBuilderUtil processBuilderUtil;

	@Inject
	FileUtil fileUtil;

	@Inject
	ThreadUtil threadUtil;

	@Inject
	SettingUtil settingUtil;

	@Inject
	WebSocketTailerListener messageSender;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public View listProject() throws Exception {
		StartupView sv = new StartupView();
		sv.setProjects(getProjects());
		return sv;
	}

	@Override
	public ListStartProjectsResponse listProjects() throws Exception {
		List<StartProject> projects = getProjects();
		for (StartProject project : projects) {
			// set log
			String logPath = settingUtil.getLogPath(project.getPath());
			if (StringUtils.isNotEmpty(logPath)) {
				project.setLog(logPath);
			}
			// set pid, started
			if (project.getPort() != null) {
				Integer pid = commandUtil.getPidByPort(project.getPort());
				if (pid != null) {
					project.setStarted(true);
					project.setPid(pid);
				} else {
					project.setStarted(false);
				}
			}
		}

		ListStartProjectsResponse response = new ListStartProjectsResponse();
		response.setProjects(projects);
		return response;
	}

	@Override
	public ListProfilesResponse listProfiles() throws Exception {
		ListProfilesResponse response = new ListProfilesResponse();
		response.setProfiles(getProfiles());
		return response;
	}

	@POST
	@Path("/{projectName}")
	@Produces(MediaType.TEXT_HTML)
	public void startStopProject(@PathParam("projectName") String projectName, StartProjectRequest requset) throws Exception {
		StartProject proj = getProjectByName(projectName);
		if (proj == null) {
			logger.error("Cannot find project with [NAME:{}], no command executed", projectName);
			return;
		}

		if (proj.isStarted()) {
			stopProjectByPid(proj);
		} else {
			startProject(proj, requset.getProfile());
		}
	}

	@GET
	@Path("/{projectName}/logs")
	@Produces(MediaType.TEXT_HTML)
	public void getProjectLog(@PathParam("projectName") String projectName) throws Exception {
		StartProject proj = getProjectByName(projectName);
		if (proj == null) {
			logger.error("Cannot find project with [NAME:{}], no command executed", projectName);
			return;
		}

		String logPath = proj.getLog();
		// fileUtil.readUpdatingFile(logPath, messageSender);

		threadUtil.start(logPath, messageSender);
	}

	/**
	 * Start project with different profile
	 * 
	 * @param proj
	 * @param profileId
	 * @throws Exception
	 */
	private void startProject(StartProject proj, SingleProfile profile) throws Exception {
		logger.info("start/stop project [NAME:{}][PROFILE_NAME:{}]", proj.getName());

		// get project path by name
		String projectPath = proj.getPath();
		String ymlPath = settingUtil.getYmlPath(projectPath);
		String jarPath = settingUtil.getJarPath(projectPath);

		// replace yml with correct profile
		String newYmlPath = ymlPath;
		if (profile != null) {
			String[] fileContent = fileUtil.readOriginalFile(ymlPath);
			String[] updatedContent = fileUtil.replaceFileForMuliDbProfiles(fileContent, proj.getDatabase(), profile.getDatabase());
			newYmlPath = ymlPath + profile.getName() + ".yml";
			fileUtil.writeToNewFile(updatedContent, newYmlPath);
		}

		// start up
		String startCommand = "java -jar " + jarPath + " server " + ymlPath + "";
		Process p = processBuilderUtil.buildTask(startCommand);
		processMapping.getStartProcessMap().put(proj.getName(), p);
		proj.setStarted(true);
		// processBuilderUtil.monitorProcess(p);
	}

	/**
	 * If process started by this application, it will contains a process in mapping
	 * 
	 * Else need kill process by PID detected when application started
	 * 
	 * @param proj
	 * @throws IOException
	 */
	private void stopProject(StartProject proj) throws IOException {
		Process p = processMapping.getStartProcessMap().get(proj.getName());
		if (p != null) {
			stopProjectByProcess(p);
		} else {
			stopProjectByPid(proj);
		}

		proj.setStarted(false);
	}

	private void stopProjectByPid(StartProject proj) throws IOException {
		Integer pid = proj.getPid();
		if (pid == null) {
			logger.info("PID is null, try to get pid to stop");
			pid = commandUtil.getPidByPort(proj.getPort());
			if (pid == null) {
				logger.info("PID is really null, cannot stop project");
				proj.setStarted(false);
				return;
			}
		}
		String stopCommand = "taskkill /F /PID " + pid;
		commandUtil.executeCommand(stopCommand);

		String projectPath = proj.getPath();
		String logPath = projectPath + "/" + proj.getLog();
		// fileUtil.closeReaders(logPath);
		threadUtil.stop(logPath);

		proj.setPid(null);
		proj.setStarted(false);
	}

	private void stopProjectByProcess(Process p) {
		p.destroy();
	}

	private StartProject getProjectByName(String projectName) throws JsonParseException, JsonMappingException, IOException {
		for (StartProject project : getProjects()) {
			if (project.getName().equals(projectName)) {
				return project;
			}
		}
		return null;
	}

	// check port is available
	@Deprecated
	private static boolean available(int port) {
		try (Socket ignored = new Socket("localhost", port)) {
			return false;
		} catch (IOException ignored) {
			return true;
		}
	}

	private void buildProjectTree() throws JsonParseException, JsonMappingException, IOException {
		if (projects == null) {
			String projectRegistries = configuation.getProjectRegistries();
			logger.info("build tree {}", projectRegistries);
			List<StartProject> allprojects = objectMapper.readValue(projectRegistries, objectMapper.getTypeFactory().constructCollectionType(List.class, StartProject.class));

			projects = new ArrayList<>();
			for (StartProject project : allprojects) {
				if (project.getPort() != null) {
					projects.add(project);
				}
			}
		}
	}

	private void buildProfileTree() throws JsonParseException, JsonMappingException, IOException {
		String profileRegistries = configuation.getProfileRegistries();
		logger.info("build tree {}", profileRegistries);
		profiles = objectMapper.readValue(profileRegistries, objectMapper.getTypeFactory().constructCollectionType(List.class, SingleProfile.class));
	}

	public List<StartProject> getProjects() throws JsonParseException, JsonMappingException, IOException {
		if (CollectionUtils.isEmpty(projects)) {
			buildProjectTree();
		}
		return projects;
	}

	public List<SingleProfile> getProfiles() throws JsonParseException, JsonMappingException, IOException {
		if (CollectionUtils.isEmpty(profiles)) {
			buildProfileTree();
		}
		return profiles;
	}
}
