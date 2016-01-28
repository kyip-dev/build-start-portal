package com.kyip.api.buildstartportal.external.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kyip.api.buildstartportal.external.ListProfilesResponse;
import com.kyip.api.buildstartportal.external.ListStartProjectsResponse;

@Path("/kyip/v1/start/projects")
public interface StartupResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ListStartProjectsResponse listProjects() throws Exception;

	@GET
	@Path("/profiles")
	@Produces(MediaType.APPLICATION_JSON)
	public ListProfilesResponse listProfiles() throws Exception;

}
