package com.internal.tasks.service;

import static ch.lambdaj.Lambda.forEach;

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

import com.internal.tasks.beans.Office;
import com.internal.tasks.dao.OfficeDao;
import com.internal.tasks.util.JsonConverter;

@Path("/tasks")
public class TasksService {

	private OfficeDao dao = new OfficeDao();

	@POST
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/task1CheckIn")
	public Response task1CheckIn(String input) {
		Office output = new Office();
		output.setName(input);
		output.setTime(getSystemStringDate());
		output.setOperation("IN");

		try {

			output = dao.save(output);

		} catch (Exception sqlEx) {
			return Response.status(HttpStatus.SC_CONFLICT).entity(sqlEx.getMessage()).build();
		}

		System.out.println("*******CHECKIN PERSISTED *******");
		return Response.status(HttpStatus.SC_OK).entity(JsonConverter.convertToJson(output)).build();
	}

	@POST
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/task1CheckOut")
	public Response task1CheckOut(String input) {
		Office output = new Office();
		output.setName(input);
		output.setTime(getSystemStringDate());
		output.setOperation("OUT");

		try {

			output = dao.save(output);

		} catch (Exception sqlEx) {
			return Response.status(HttpStatus.SC_CONFLICT).entity(sqlEx.getMessage()).build();
		}

		System.out.println("*******CHECKOUT PERSISTED *******");
		return Response.status(HttpStatus.SC_OK).entity(JsonConverter.convertToJson(output)).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/task2CheckIn/{inputCollection}")
	public Response task2CheckIn(@PathParam("inputCollection") String inputJson) throws Exception {

		List<Office> collection = JsonConverter.convertFromJson(inputJson);
		try {
			collection = dao.saveCollection(collection);

		} catch (Exception sqlEx) {
			return Response.status(HttpStatus.SC_CONFLICT).entity(sqlEx.getMessage()).build();
		}

		System.out.println("******* PERSISTED *******");
		return Response.status(HttpStatus.SC_OK).entity(JsonConverter.convertToJson(collection)).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/task2CheckOut/{inputCollection}")
	public Response task2CheckOut(@PathParam("inputCollection") String inputJson) throws Exception {

		List<Office> collection = JsonConverter.convertFromJson(inputJson);

		try {
			collection = dao.saveCollection(collection);

		} catch (Exception sqlEx) {
			return Response.status(HttpStatus.SC_CONFLICT).entity(sqlEx.getMessage()).build();
		}

		System.out.println("******* PERSISTED *******");
		return Response.status(HttpStatus.SC_OK).entity(JsonConverter.convertToJson(collection)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/task3CheckIn/{name}")
	public Response task3CheckIn(@PathParam("name") String name) throws Exception {

		// Retrive data from DB
		List<Office> item = dao.read(name, "IN");
		System.out.println("******* RETRIVED *******");

		return Response.status(HttpStatus.SC_OK).entity(JsonConverter.convertToJson(item)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/task3CheckOut/{name}")
	public Response task3CheckOut(@PathParam("name") String name) throws Exception {

		// Retrive data from DB
		List<Office> item = dao.read(name, "OUT");
		System.out.println("******* RETRIVED *******");

		return Response.status(HttpStatus.SC_OK).entity(JsonConverter.convertToJson(item)).build();
	}

	private String getSystemStringDate() {

		Date utilDate = new Date();
		Timestamp sq = new Timestamp(utilDate.getTime());
		SimpleDateFormat convertedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		return convertedDate.format(sq);
	}

//	private static String convertToJson(Object item) {
//		Gson gson = new Gson();
//		// Convert to Json
//		return gson.toJson(item);
//	}
}