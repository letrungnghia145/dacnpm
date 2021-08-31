package com.app.serialize;

import java.io.IOException;

import com.app.helper.Mapper;
import com.app.model.post.Comment;
import com.app.model.post.Comment_;
import com.app.model.user.User_;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CommentSerialize extends StdSerializer<Comment> {

	private static final long serialVersionUID = -2972058231278548613L;

	protected CommentSerialize(Class<Comment> t) {
		super(t);
	}

	public CommentSerialize() {
		this(null);
	}

	@Override
	public void serialize(Comment value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		gen.writeStartObject();
		gen.writeObjectField(Comment_.ID, value.getId());
		gen.writeStringField(Comment_.CONTENT, value.getContent());
		gen.writeObjectField(Comment_.AUTHOR,
				Mapper.toMapValue(value.getAuthor(), User_.ID, User_.FIRST_NAME, User_.LAST_NAME));
		gen.writeObjectField(Comment_.CREATED_DATE, value.getCreatedDate());
		gen.writeObjectField(Comment_.MODIFIED_DATE, value.getModifiedDate());
		gen.writeEndObject();
	}

}
