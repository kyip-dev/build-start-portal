package com.kyip.api.buildstartportal.external.resource;

import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.kyip.api.buildstartportal.external.views.MessageView;
import com.kyip.api.buildstartportal.external.websocket.MessageSender;

public class MessageResourceImpl implements MessageResource {

	@Inject
	MessageSender messageSender;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public View getView() throws Exception {
		MessageView sv = new MessageView();
		return sv;
	}

	@Override
	public void sendMessage(String message) throws Exception {
		messageSender.sendMessage(message);
	}

}
