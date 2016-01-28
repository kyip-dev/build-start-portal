package com.kyip.api.buildstartportal.external.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/kyip/v1/messages")
public interface MessageResource {

	@POST
	public void sendMessage(String message) throws Exception;

}
