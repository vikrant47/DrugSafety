package com.example.drugsafety.model;

import com.example.drugsafety.service.util.Messages;
import java.util.HashMap;

import com.example.drugsafety.util.CollectionUtil;

public class ResponseAction {

    private HashMap<String, Object> action = new HashMap<>();
    private ResponseModel responseModel;
    private Messages messages;

    public ResponseAction(ResponseModel responseModel) {
        this.responseModel = responseModel;
        this.messages = this.responseModel.getMessages();
    }

    public ResponseAction alert(String message) {
        action.put("alert", this.messages.get(message));
        return this;
    }

    public ResponseAction info(String title, String body) {
        action.put("info", CollectionUtil.newChanedMap().add("title", this.messages.get(title)).add("body", this.messages.get(body)));
        return this;
    }

    public ResponseAction error(String title, String body) {
        action.put("error", CollectionUtil.newChanedMap().add("title", this.messages.get(title)).add("body", this.messages.get(body)));
        return this;
    }

    public ResponseAction confirm(String message, Object successAction, Object failedAction) {
        action.put("confirm", this.messages.get(message));
        action.put("successAction", successAction);
        action.put("failedAction", failedAction);
        return this;
    }

    public ResponseAction dialog(String message) {
        action.put("alertDialog", message);
        return this;
    }

    public ResponseAction confirmDialog(String message, Object successAction, Object failedAction) {
        action.put("confirmDialog", this.messages.get(message));
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
