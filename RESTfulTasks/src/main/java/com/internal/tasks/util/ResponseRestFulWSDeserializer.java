package com.internal.tasks.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.internal.tasks.beans.ResponseRestFulWS;

public class ResponseRestFulWSDeserializer implements JsonDeserializer<ResponseRestFulWS> {

	@Override
	public ResponseRestFulWS deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final String name = jsonObject.get("name").getAsString();
		final String time = jsonObject.get("time").getAsString();
		
		ResponseRestFulWS object = new ResponseRestFulWS();
		object.setName(name);
		object.setTime(time);
		
		return object;
	}
}