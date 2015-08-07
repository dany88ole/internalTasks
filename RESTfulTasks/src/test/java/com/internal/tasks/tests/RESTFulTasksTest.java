package com.internal.tasks.tests;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.internal.tasks.beans.ResponseRestFulWS;
import com.jayway.restassured.RestAssured;

public class RESTFulTasksTest {

	//value TEST
	private String name = "TEST";
	
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/RESTfulTasks/rest/tasks/";
	}

//	@Test //inserts name=TEST into DB 
	public void task1POST() {
		given()
		.contentType("application/json")
		.body(name)
		.then()
		.statusCode(HttpStatus.SC_OK)
		.body("name", equalTo("TEST"))
		.when()
		.post("task1POST");
	}

	@Test //persists a collection sent in JSON  
	public void task2GET() throws UnsupportedEncodingException{
		given()
		.contentType("application/json")
		.accept("application/json")
		.then()
		.statusCode(HttpStatus.SC_OK)
		.when()
		.get("task2GET/"+URLEncoder.encode(createMockCollectionJson(),"UTF-8"));
	}

//	@Test // retrives record from DB by name
	public void task3GET() {

		given()
		.contentType("application/json")
		.then()
		.body("name", equalTo(name))
		.statusCode(HttpStatus.SC_OK)
		.when()
		.get("task3GET/"+name);
	}

	
	private String createMockCollectionJson() {
		List<ResponseRestFulWS> input = new ArrayList<ResponseRestFulWS>();
		
		for (int i = 0; i < 3; i++) {
			ResponseRestFulWS temp = new ResponseRestFulWS();
			temp.setName(name + i);
			temp.setTime("1" + i + i + i + "-1" + i + "-1" + i + "T2" + i + ":" + i + i + ":" + i + i);
			input.add(temp);
		}
		return convertToJson(input);
	}

	private static String convertToJson(List<ResponseRestFulWS> item) {
		Gson gson = new Gson();
		// Convert to Json
		return gson.toJson(item);
	}
}
