package com.kyip.api.buildstartportal.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kyip.api.buildstartportal.external.datatype.SingleProfile;

public class StartProjectRequest {

	@JsonProperty("profile")
	private SingleProfile profile;

	public SingleProfile getProfile() {
		return profile;
	}

	public void setProfile(SingleProfile profile) {
		this.profile = profile;
	}
}
