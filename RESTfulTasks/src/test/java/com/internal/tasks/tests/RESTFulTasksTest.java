package com.internal.tasks.tests;

import static ch.lambdaj.Lambda.on;
import static com.jayway.restassured.RestAssured.given;

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
import com.internal.tasks.beans.Office;
import com.internal.tasks.dao.OfficeDao;
import com.internal.tasks.util.OfficeDeserializer;
import com.internal.tasks.util.JsonConverter;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import ch.lambdaj.Lambda;

public class RESTFulTasksTest {

	// value TEST
	private String name = "EMPLOYEE";

	private OfficeDao OfficeDao = new OfficeDao();

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/RESTfulTasks/rest/tasks/";
	}

	 @Test
	public void task1CheckIn() {
		given().contentType("application/json").body(name).then().statusCode(HttpStatus.SC_OK).when()
				.post("task1CheckIn");

		List<Office> result = OfficeDao.read(name, "IN");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty());
	}

	 @Test
	public void task1CheckOut() {
		given().contentType("application/json").body(name).then().statusCode(HttpStatus.SC_OK).when()
				.post("task1CheckOut");

		List<Office> result = OfficeDao.read(name, "OUT");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty());
	}

	 @Test
	public void task2CheckIn() throws UnsupportedEncodingException {
		String inputJson = createMockCollectionJson("IN");

		Response response = given().contentType("application/json").accept("application/json").then()
				.statusCode(HttpStatus.SC_OK).when().get("task2CheckIn/" + URLEncoder.encode(inputJson, "UTF-8"));

		List<Office> list = JsonConverter.convertFromJson(response.asString());

		List<Long> ids = Lambda.extract(list, on(Office.class).getName_id());

		List<Office> result = OfficeDao.readListById(ids, "IN");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty() && result.size() != 3);
	}

	@Test
	public void task2CheckOut() throws UnsupportedEncodingException {
		String inputJson = createMockCollectionJson("OUT");

		Response response = given().contentType("application/json").accept("application/json").then()
				.statusCode(HttpStatus.SC_OK).when().get("task2CheckOut/" + URLEncoder.encode(inputJson, "UTF-8"));

		List<Office> list = JsonConverter.convertFromJson(response.asString());

		List<Long> ids = Lambda.extract(list, on(Office.class).getName_id());

		List<Office> result = OfficeDao.readListById(ids, "OUT");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty() && result.size() != 3);

	}

	@Test
	public void task3CheckIn() throws UnsupportedEncodingException {
		Response response = given().contentType("application/json").accept("application/json").then()
				.statusCode(HttpStatus.SC_OK).when().get("task3CheckIn/" + name + "1");

		List<Office> list = JsonConverter.convertFromJson(response.asString());
		
		List<String> names = Lambda.extract(list, on(Office.class).getName());

		List<Office> result = OfficeDao.readListByName(names, "IN");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty());

	}

	@Test
	public void task3CheckOut() throws JsonSyntaxException, UnsupportedEncodingException {
		Response response = given().contentType("application/json").accept("application/json").then()
				.statusCode(HttpStatus.SC_OK).when().get("task3CheckOut/" + name + "1");

		List<Office> list = JsonConverter.convertFromJson(response.asString());
		
		List<String> names = Lambda.extract(list, on(Office.class).getName());

		List<Office> result = OfficeDao.readListByName(names, "OUT");

		System.out.println(result.size());
		Assert.assertFalse(result.isEmpty());

	}

	private List<Office> fromJson(String input) throws JsonSyntaxException, UnsupportedEncodingException {

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Office.class, new OfficeDeserializer());
		Gson gson = builder.create();
		Type type = new TypeToken<List<Office>>() {
		}.getType();

		// JSON string to Collection
		return gson.fromJson(URLDecoder.decode(input, "UTF-8"), type);
	}

	private String createMockCollectionJson(String operation) {
		List<Office> input = new ArrayList<Office>();

		for (int i = 0; i < 3; i++) {
			Office temp = new Office();
			temp.setName(name + i);
			temp.setTime("1" + i + i + i + "-1" + i + "-1" + i + "T2" + i + ":" + i + i + ":" + i + i);
			temp.setOperation(operation);
			input.add(temp);
		}
		return convertToJson(input);
	}

	private static String convertToJson(List<Office> item) {
		Gson gson = new Gson();
		// Convert to Json
		return gson.toJson(item);
	}
}
