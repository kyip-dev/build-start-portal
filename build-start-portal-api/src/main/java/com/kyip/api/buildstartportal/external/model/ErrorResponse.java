package com.kyip.api.buildstartportal.external.model;

import java.io.Serializable;

import javax.validation.Valid;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ErrorResponse", description = "Basic REST API error response")
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 3511567916351389889L;

	@Valid
	@ApiModelProperty(required = true, value = "REST API error code")
	private Integer errorCode;

	@Valid
	@ApiModelProperty(required = true, value = "REST API error message")
	private String errorMessage;

	@Valid
	@ApiModelProperty(required = true, value = "REST API debug info")
	private String debugInfo;

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
