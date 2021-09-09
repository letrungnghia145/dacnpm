package com.app.serialize;

import java.io.IOException;

import com.app.model.Category;
import com.app.model.Category_;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CategorySerialize extends StdSerializer<Category> {

	private static final long serialVersionUID = 2098226446820337266L;

	protected CategorySerialize(Class<Category> t) {
		super(t);
	}

	public CategorySerialize() {
		this(null);
	}

	@Override
	public void serialize(Category value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField(Category_.ID, value.getId());
		gen.writeStringField(Category_.NAME, value.getName());
		gen.writeObjectField(Category_.CREATED_DATE, value.getCreatedDate());
		gen.writeObjectField(Category_.MODIFIED_DATE, value.getModifiedDate());
		gen.writeEndObject();
	}

}
