package com.kyip.api.buildstartportal.external.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kyip.api.buildstartportal.external.GetCalendarEventResponse;

@Path("/kyip/v1/calendar")
public interface CalendarResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GetCalendarEventResponse showCalendar() throws Exception;

}
