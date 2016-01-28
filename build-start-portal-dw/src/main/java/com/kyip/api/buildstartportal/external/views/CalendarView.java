package com.kyip.api.buildstartportal.external.views;

import io.dropwizard.views.View;

import java.nio.charset.StandardCharsets;

public class CalendarView extends View {

	public CalendarView() {
		super("/views/calendar/calendar.ftl", StandardCharsets.UTF_8);
	}

	public CalendarView(String viewPath) {
		super("/views/calendar/tmpls/" + viewPath, StandardCharsets.UTF_8);
	}
}
