package com.kyip.api.buildstartportal.external.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/kyip/v1/html/parser")
public interface HtmlParserResource {

	@GET
	@Path("/links")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLinks(@QueryParam("url") String url) throws Exception;

	@GET
	@Path("/medias")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMedias(@QueryParam("url") String url) throws Exception;

}
