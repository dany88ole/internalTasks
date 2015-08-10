package com.internal.tasks.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.internal.tasks.beans.Office;

public class JsonConverter {

	public static String convertToJson(Object item) {
		Gson gson = new Gson();
		return gson.toJson(item);
	}
	
	public static List<Office> convertFromJson(String json) throws JsonSyntaxException, UnsupportedEncodingException{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Office.class, new OfficeDeserializer());
		Gson gson = builder.create();
		Type type = new TypeToken<List<Office>>() {
		}.getType();
		
		return gson.fromJson(URLDecoder.decode(json, "UTF-8"), type);
	}
	
}
