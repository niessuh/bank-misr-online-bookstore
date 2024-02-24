package com.bankmisr.onlinebookstore.model;

public class ResponseMsg {
	String message;
	public ResponseMsg(String msg) {
		this.message = msg;
	}
	public String getMessage() {
		return message;
	}
}
