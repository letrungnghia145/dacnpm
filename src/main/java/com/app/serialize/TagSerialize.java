package com.app.serialize;

import java.io.IOException;

import com.app.model.tag.Tag;
import com.app.model.tag.Tag_;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TagSerialize extends StdSerializer<Tag> {

	private static final long serialVersionUID = 1429423794123547860L;

	protected TagSerialize(Class<Tag> t) {
		super(t);
	}

	public TagSerialize() {
		this(null);
	}

	@Override
	public void serialize(Tag value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeObjectField(Tag_.ID, value.getId());
		gen.writeStringField(Tag_.NAME, value.getName());
		gen.writeObjectField(Tag_.CURRENT_POST_INDEX, value.getCurrentPostIndex());
		gen.writeObjectField(Tag_.CREATED_DATE, value.getCreatedDate());
		gen.writeObjectField(Tag_.MODIFIED_DATE, value.getModifiedDate());
		gen.writeEndObject();
	}

}
