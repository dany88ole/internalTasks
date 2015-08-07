package com.internal.tasks.service;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.internal.tasks.beans.ResponseRestFulWS;
import com.internal.tasks.dao.ResponseRestFulWSDAO;
import com.internal.tasks.util.ResponseRestFulWSDeserializer;

@Path("/tasks")
public class TasksService {

	private ResponseRestFulWSDAO dao = new ResponseRestFulWSDAO();

	@POST
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/task1POST")
	public Response task1POST(String input) {
		ResponseRestFulWS output = new ResponseRestFulWS();
		output.setName(input);
		output.setTime(getSystemStringDate());

		try {

			output = dao.save(output);

		} catch (Exception sqlEx) {
			return Response.status(HttpStatus.SC_CONFLICT).entity(sqlEx.getMessage()).build();
		}

		System.out.println("******* PERSISTED *******");
		return Response.status(HttpStatus.SC_OK).entity(convertToJson(output)).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/task2GET/{inputCollection}")
	public Response task2GET(@PathParam("inputCollection") String inputJson) throws Exception {

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ResponseRestFulWS.class, new ResponseRestFulWSDeserializer());
		Gson gson = builder.create();
		Type type = new TypeToken<List<ResponseRestFulWS>>() {}.getType();

		// JSON string to Collection
		List<ResponseRestFulWS> collection = gson.fromJson(URLDecoder.decode(inputJson,"UTF-8"), type);

		try {
			collection = dao.saveCollection(collection);

		} catch (Exception sqlEx) {
			return Response.status(HttpStatus.SC_CONFLICT).entity(sqlEx.getMessage()).build();
		}
		
		System.out.println("******* PERSISTED *******");
		return Response.status(HttpStatus.SC_OK).entity("DATA RECEIVED CORRECTLY").build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/task3GET/{name}")
	public Response task3GET(@PathParam("name") String name) throws Exception {

		// Retrive data from DB
		ResponseRestFulWS item = dao.read(name);
		System.out.println("******* RETRIVED *******");

		return Response.status(HttpStatus.SC_OK).entity(convertToJson(item)).build();
	}

	
	private String getSystemStringDate() {

		Date utilDate = new Date();
		Timestamp sq = new Timestamp(utilDate.getTime());
		SimpleDateFormat convertedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		return convertedDate.format(sq);
	}

	private static String convertToJson(ResponseRestFulWS item) {
		Gson gson = new Gson();
		// Convert to Json
		return gson.toJson(item);
	}
}