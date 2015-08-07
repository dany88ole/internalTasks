package com.internal.tasks.tests;

import static ch.lambdaj.Lambda.on;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.internal.tasks.beans.Hotel;
import com.internal.tasks.dao.HotelDao;
import com.internal.tasks.util.HotelDeserializer;
import com.internal.tasks.util.JsonConverter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import ch.lambdaj.Lambda;

public class RESTFulTasksTest {

	// value TEST
	private String name = "JUNIT";

	private HotelDao hotelDao = new HotelDao();

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/RESTfulTasks/rest/tasks/";
	}

	// @Test
	public void task1CheckIn() {
		given().contentType("application/json").body(name).then().statusCode(HttpStatus.SC_OK).when()
				.post("task1CheckIn");

		List<Hotel> result = hotelDao.read(name, "IN");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty());
	}

	// @Test
	public void task1CheckOut() {
		given().contentType("application/json").body(name).then().statusCode(HttpStatus.SC_OK).when()
				.post("task1CheckOut");

		List<Hotel> result = hotelDao.read(name, "OUT");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty());
	}

	// @Test
	public void task2CheckIn() throws UnsupportedEncodingException {
		String inputJson = createMockCollectionJson("IN");

		Response response = given().contentType("application/json").accept("application/json").then()
				.statusCode(HttpStatus.SC_OK).when().get("task2CheckIn/" + URLEncoder.encode(inputJson, "UTF-8"));

		List<Hotel> list = JsonConverter.convertFromJson(response.asString());

		List<Long> ids = Lambda.extract(list, on(Hotel.class).getName_id());

		List<Hotel> result = hotelDao.readListById(ids, "IN");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty() && result.size() != 3);
	}

//	@Test
	public void task2CheckOut() throws UnsupportedEncodingException {
		String inputJson = createMockCollectionJson("OUT");

		Response response = given().contentType("application/json").accept("application/json").then()
				.statusCode(HttpStatus.SC_OK).when().get("task2CheckOut/" + URLEncoder.encode(inputJson, "UTF-8"));

		List<Hotel> list = JsonConverter.convertFromJson(response.asString());

		List<Long> ids = Lambda.extract(list, on(Hotel.class).getName_id());

		List<Hotel> result = hotelDao.readListById(ids, "OUT");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty() && result.size() != 3);

	}

//	@Test
	public void task3CheckIn() throws UnsupportedEncodingException {
		Response response = given().contentType("application/json").accept("application/json").then()
				.statusCode(HttpStatus.SC_OK).when().get("task3CheckIn/" + name + "1");

		List<Hotel> list = JsonConverter.convertFromJson(response.asString());
		
		List<String> names = Lambda.extract(list, on(Hotel.class).getName());

		List<Hotel> result = hotelDao.readListByName(names, "IN");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty());

	}

	@Test
	public void task3CheckOut() throws JsonSyntaxException, UnsupportedEncodingException {
		Response response = given().contentType("application/json").accept("application/json").then()
				.statusCode(HttpStatus.SC_OK).when().get("task3CheckOut/" + name + "1");

		List<Hotel> list = JsonConverter.convertFromJson(response.asString());
		
		List<String> names = Lambda.extract(list, on(Hotel.class).getName());

		List<Hotel> result = hotelDao.readListByName(names, "OUT");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty());

	}

	private List<Hotel> fromJson(String input) throws JsonSyntaxException, UnsupportedEncodingException {

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Hotel.class, new HotelDeserializer());
		Gson gson = builder.create();
		Type type = new TypeToken<List<Hotel>>() {
		}.getType();

		// JSON string to Collection
		return gson.fromJson(URLDecoder.decode(input, "UTF-8"), type);
	}

	private String createMockCollectionJson(String operation) {
		List<Hotel> input = new ArrayList<Hotel>();

		for (int i = 0; i < 3; i++) {
			Hotel temp = new Hotel();
			temp.setName(name + i);
			temp.setTime("1" + i + i + i + "-1" + i + "-1" + i + "T2" + i + ":" + i + i + ":" + i + i);
			temp.setOperation(operation);
			input.add(temp);
		}
		return convertToJson(input);
	}

	private static String convertToJson(List<Hotel> item) {
		Gson gson = new Gson();
		// Convert to Json
		return gson.toJson(item);
	}
}
