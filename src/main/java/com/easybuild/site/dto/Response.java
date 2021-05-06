package com.easybuild.site.dto;

import com.easybuild.site.constants.Code;
import com.easybuild.site.constants.Constant;


import net.minidev.json.JSONObject;

public class Response {
	private String status;
	private int code;
	private String message;
	private JSONObject data;
	
	public Response() {
		super();
		this.status=Constant.STATUS_SUCCESS;
		this.code=Code.SUCCESS;
	}

	public Response(String status, int code, String message, JSONObject data) {
		super();
		this.status = status;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		JSONObject childJsonObject = new JSONObject();
		jsonObject.put(Constant.RESULT, childJsonObject);
		childJsonObject.put(Constant.STATUS, status);
		childJsonObject.put(Constant.CODE, code);
		childJsonObject.put(Constant.MESSAGE, message);
		if (data != null)
			childJsonObject.put(Constant.DATA, data);

		return childJsonObject.toJSONString();
	}

}
