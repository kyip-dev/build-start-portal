package com.kyip.api.buildstartportal.external.datatype;

import java.util.HashMap;
import java.util.Map;

public class ProcessMapping {
	private Map<String, Process> startProcessMap = new HashMap<String, Process>();

	public Map<String, Process> getStartProcessMap() {
		return startProcessMap;
	}

	public void setStartProcessMap(Map<String, Process> startProcessMap) {
		this.startProcessMap = startProcessMap;
	}
}
