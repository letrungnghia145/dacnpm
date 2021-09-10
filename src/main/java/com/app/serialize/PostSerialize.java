package com.app.serialize;

import java.io.IOException;

import com.app.helper.Mapper;
import com.app.model.post.Post;
import com.app.model.post.Post_;
import com.app.model.user.User_;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PostSerialize extends StdSerializer<Post> {

	private static final long serialVersionUID = -7602579209750223872L;

	protected PostSerialize(Class<Post> t) {
		super(t);
	}

	public PostSerialize() {
		this(null);
	}

	@Override
	public void serialize(Post value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		gen.writeStartObject();
		gen.writeObjectField(Post_.ID, value.getId());
		gen.writeStringField(Post_.TITLE, value.getTitle());
		gen.writeStringField(Post_.CONTENT, value.getContent());
		gen.writeObjectField(Post_.AUTHOR,
				Mapper.toMapValue(value.getAuthor(), User_.ID, User_.FIRST_NAME, User_.LAST_NAME));
		gen.writeObjectField(Post_.TAGS, value.getTags());
		gen.writeObjectField(Post_.COUNT_VIEWS, value.getCountViews());
		gen.writeObjectField(Post_.CREATED_DATE, value.getCreatedDate());
		gen.writeObjectField(Post_.MODIFIED_DATE, value.getModifiedDate());
		gen.writeEndObject();
	}
}
