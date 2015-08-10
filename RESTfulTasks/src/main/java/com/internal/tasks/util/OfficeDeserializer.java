package com.internal.tasks.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.internal.tasks.beans.Office;

public class OfficeDeserializer implements JsonDeserializer<Office> {

	@Override
	public Office deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		Office object = new Office();

		
		if (jsonObject.get("name_id") != null) {
			final Long name_id  = jsonObject.get("name_id").getAsLong();
			object.setName_id(name_id);
		}
		
		final String name = jsonObject.get("name").getAsString();
		final String time = jsonObject.get("time").getAsString();
		final String operation = jsonObject.get("operation").getAsString();

		
		object.setName(name);
		object.setTime(time);
		object.setOperation(operation);

		return object;
	}
}