package com.kyip.api.buildstartportal.external.resource;

import io.dropwizard.views.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kyip.api.buildstartportal.external.GetCalendarEventResponse;
import com.kyip.api.buildstartportal.external.datatype.CalendarEvent;
import com.kyip.api.buildstartportal.external.util.ViewUtil;
import com.kyip.api.buildstartportal.external.views.CalendarView;

public class CalendarResourceImpl implements CalendarResource {

	private static final Logger logger = LoggerFactory.getLogger(CalendarResourceImpl.class);

	@Override
	public GetCalendarEventResponse showCalendar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public View showMainPage() throws Exception {
		CalendarView cv = new CalendarView();
		return cv;
	}

	@GET
	@Path("/tmpls/{subPath}")
	@Produces(MediaType.TEXT_HTML)
	public Response showSubPage(@PathParam("subPath") String subPath) throws Exception {
		logger.info(subPath);

		try {
			String output = ViewUtil.render(new CalendarView(subPath), Locale.UK);
			return Response.ok().entity(output).build();
		} catch (Exception e) {
			throw new InternalServerErrorException(e);
		}
	}

	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public GetCalendarEventResponse getEvents(@QueryParam("from") String from, @QueryParam("to") String to) throws Exception {
		GetCalendarEventResponse response = new GetCalendarEventResponse();
		CalendarEvent cv = new CalendarEvent();
		// http://localhost:13001/kyip/v1/events.json.php?from=1362067200000&to=1364745600000&utc_offset=-480
		cv.setStart("1362067200000");
		cv.setEnd("1364745600000");
		cv.setTitle("test test");
		cv.setId("124");
		cv.setUrl("http://www.example.com/");

		List<CalendarEvent> cvl = new ArrayList<>();
		cvl.add(cv);
		response.setSuccess(1);
		response.setResult(cvl);
		return response;
	}

}
