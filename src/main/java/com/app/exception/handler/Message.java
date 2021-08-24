package com.app.exception.handler;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
	private String message;
	private String path;
	private Date date;

	public Message(String message, String path, Date date) {
		super();
		this.message = message;
		this.path = path;
		this.date = date;
	}
}
