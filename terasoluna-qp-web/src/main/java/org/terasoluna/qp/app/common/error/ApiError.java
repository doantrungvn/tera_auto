package org.terasoluna.qp.app.common.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ApiError")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiError implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private  String code;

	@XmlElement
	private  String message;

//	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@XmlElement
	private  String target;

//	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@XmlElementWrapper(name="details")
	@XmlElement(name="ApiError")
	private  List<ApiError> details = new ArrayList<>();

	public ApiError() {
	}
	
	public ApiError(String code, String message) {
		this(code, message, null);
	}

	public ApiError(String code, String message, String target) {
		this.code = code;
		this.message = message;
		this.target = target;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getTarget() {
		return target;
	}

	public List<ApiError> getDetails() {
		return details;
	}

	public void addDetail(ApiError detail) {
		details.add(detail);
	}

}