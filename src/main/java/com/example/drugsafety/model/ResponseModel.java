package com.example.drugsafety.model;

import java.util.HashMap;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.example.drugsafety.service.util.Messages;
import com.example.drugsafety.util.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseModel {

    private Object data;
    private Object additionalData;
    private HashMap<String, String> messagemap;
    private ResponseAction action;
    private int statusCode = HttpStatus.OK.value();
    private Messages messages;
    private int total;
    // private statusCode = Htt;

    public ResponseModel(Messages messages) {
        messagemap = new HashMap<>();
        this.action = new ResponseAction(this);
        this.messages = messages;
    }

    public ResponseModel addMessage(String messageKey, String message) {
        String msg = this.messages.get(message);
        if (msg != null) {
            message = msg;
        }
        this.messagemap.put(messageKey, message);
        return this;
    }

    public ResponseModel data(Object data) {
        this.data = data;
        return this;
    }

    public ResponseModel additionalData(Object additionalData) {
        this.additionalData = additionalData;
        return this;
    }

    public ResponseModel message(String message) {
        this.messagemap.put("default", message);
        return this;
    }

    public ResponseModel total(int total) {
        this.total = total;
        return this;
    }

    public ResponseModel exception(String exception) {
        this.messagemap.put("exception", exception);
        return this;
    }

    public ResponseAction action() {
        return this.action;
    }

    public ResponseModel statusCode(HttpStatus status) {
        this.statusCode = status.value();
        return this;
    }

    public ResponseModel response(Object data, HttpStatus status, String message, Object additionalData) {
        this.data(data).statusCode(status).message(message).additionalData(additionalData);
        return this;
    }

    public ResponseModel notFoundIfNull() {
        if (this.data == null) {
            this.statusCode = HttpStatus.NOT_FOUND.value();
        }
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Object additionalData) {
        this.additionalData = additionalData;
    }

    @JsonProperty("messages")
    public HashMap<String, String> getMessageMap() {
        return messagemap;
    }

    public void setMessageMap(HashMap<String, String> messageMap) {
        this.messagemap = messageMap;
    }

    public HashMap<String, Object> getAction() {
        return action.getAction();
    }

    public void setAction(HashMap<String, Object> action) {
        this.action.setAction(action);
        ;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

}
