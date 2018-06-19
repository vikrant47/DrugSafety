package com.example.drugsafety.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.drugsafety.model.ResponseModel;

@Service
public class ResponseService {
	@Autowired
	private Messages messages;

	public ResponseModel response() {
		return new ResponseModel(messages);
	}
	
	public ResponseModel response(Object data) {
		return new ResponseModel(messages).data(data);
	}
	public ResponseModel response(Object data,String message,HttpStatus status,Object additionalData) {
		return new ResponseModel(messages).response(data, status, message, additionalData);
	}
}
