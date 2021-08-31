package com.app.serialize;

import java.io.IOException;

import com.app.model.user.User;
import com.app.model.user.User_;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class UserSerialize extends StdSerializer<User> {

	private static final long serialVersionUID = -4158504706014405872L;

	protected UserSerialize(Class<User> t) {
		super(t);
	}

	public UserSerialize() {
		this(null);
	}

	@Override
	public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField(User_.ID, value.getId());
		gen.writeStringField(User_.EMAIL, value.getEmail());
		gen.writeStringField(User_.PHONE, value.getPhone());
		gen.writeStringField(User_.FIRST_NAME, value.getFirstName());
		gen.writeStringField(User_.LAST_NAME, value.getLastName());
		gen.writeObjectField(User_.AGE, value.getAge());
		gen.writeObjectField(User_.ROLE, value.getRole());
		gen.writeObjectField(User_.PASSWORD, value.getPassword());
		gen.writeObjectField(User_.CREATED_DATE, value.getCreatedDate());
		gen.writeObjectField(User_.MODIFIED_DATE, value.getModifiedDate());
		gen.writeEndObject();
	}

}
