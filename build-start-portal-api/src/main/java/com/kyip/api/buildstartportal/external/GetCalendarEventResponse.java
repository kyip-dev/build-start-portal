package com.kyip.api.buildstartportal.external;

import java.io.Serializable;
import java.util.List;

import com.kyip.api.buildstartportal.external.datatype.CalendarEvent;

public class GetCalendarEventResponse implements Serializable {

	private static final long serialVersionUID = -1390821302424391096L;

	private int success;
	private List<CalendarEvent> result;

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public List<CalendarEvent> getResult() {
		return result;
	}

	public void setResult(List<CalendarEvent> result) {
		this.result = result;
	}

}
