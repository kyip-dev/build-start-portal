package com.kyip.api.buildstartportal.external;

import java.io.Serializable;
import java.util.List;

import com.kyip.api.buildstartportal.external.datatype.SingleProfile;

public class ListProfilesResponse implements Serializable {

	private static final long serialVersionUID = -2618249171549554270L;

	private List<SingleProfile> profiles;

	public List<SingleProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<SingleProfile> profiles) {
		this.profiles = profiles;
	}

}
