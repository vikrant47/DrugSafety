package com.example.drugsafety.model;

import java.util.HashMap;

import com.example.drugsafety.util.CollectionUtil;

public class ResponseAction {
	private HashMap<String, Object> action = new HashMap<>();
	private ResponseModel responseModel;

	public ResponseAction(ResponseModel responseModel) {
		this.responseModel = responseModel;
	}

	public ResponseAction alert(String message) {
		action.put("alert", message);
		return this;
	}

	public ResponseAction info(String title, String body) {
		action.put("info", CollectionUtil.newChanedMap().add("title", title).add("body", body));
		return this;
	}

	public ResponseAction error(String title, String body) {
		action.put("error", CollectionUtil.newChanedMap().add("title", title).add("body", body));
		return this;
	}

	public ResponseAction confirm(String message, Object successAction, Object failedAction) {
		action.put("confirm", message);
		action.put("successAction", successAction);
		action.put("failedAction", failedAction);
		return this;
	}

	public ResponseAction dialog(String message) {
		action.put("alertDialog", message);
		return this;
	}

	public ResponseAction confirmDialog(String message, Object successAction, Object failedAction) {
		action.put("confirmDialog", message);
		action.put("successAction", successAction);
		action.put("failedAction", failedAction);
		return this;
	}

	public ResponseModel response() {
		return this.responseModel;
	}

	public HashMap<String, Object> getAction() {
		return action;
	}

	public void setAction(HashMap<String, Object> action) {
		this.action = action;
	}
}