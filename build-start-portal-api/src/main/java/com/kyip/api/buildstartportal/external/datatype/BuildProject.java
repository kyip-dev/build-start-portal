package com.kyip.api.buildstartportal.external.datatype;


public class BuildProject extends SingleProject {

	private boolean skipTest;

	private String lastSuccess;

	public boolean isSkipTest() {
		return skipTest;
	}

	public void setSkipTest(boolean skipTest) {
		this.skipTest = skipTest;
	}

	public String getLastSuccess() {
		return lastSuccess;
	}

	public void setLastSuccess(String lastSuccess) {
		this.lastSuccess = lastSuccess;
	}

}
