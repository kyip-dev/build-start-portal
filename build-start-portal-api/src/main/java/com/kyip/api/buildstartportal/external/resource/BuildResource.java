package com.kyip.api.buildstartportal.external.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kyip.api.buildstartportal.external.ListBuildProjectsResponse;

@Path("/kyip/v1/build/projects")
public interface BuildResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ListBuildProjectsResponse listProjects() throws Exception;

}
