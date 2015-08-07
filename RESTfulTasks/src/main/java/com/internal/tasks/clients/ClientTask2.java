package com.internal.tasks.clients;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.google.gson.Gson;
import com.internal.tasks.beans.ResponseRestFulWS;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ClientTask2 {

	public static void main(String[] args) {
		try {

			String jsonCollection = convertFromColletctionToJson();
			System.out.println("SENT:/n"+jsonCollection);
			
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);
			WebResource webResource = client.resource("http://localhost:8080/RESTfulTasks/rest/tasks/task2GET/"+ URLEncoder.encode(jsonCollection, "UTF-8"));
			ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);

			if (response.getStatus() != HttpStatus.SC_OK) {
				
				if (response.getStatus() == HttpStatus.SC_CONFLICT) {
					throw new RuntimeException("DUPLICATE NAME INTO DB - VIOLATE CONSTRAINT");
				} else {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}
			}

			String output = response.getEntity(String.class);
			System.out.println(output);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String convertFromColletctionToJson() {
		Gson gson = new Gson();
		// Convert to Json
		return gson.toJson(getCollection());
	}

	private static List<ResponseRestFulWS> getCollection() {
		List<ResponseRestFulWS> collection = new ArrayList<ResponseRestFulWS>();
		populateCollection(collection);
		return collection;
	}

	private static void populateCollection(List<ResponseRestFulWS> input) {

		for (int i = 0; i < 3; i++) {
			ResponseRestFulWS temp = new ResponseRestFulWS();
			temp.setName("SECOND" + i);
			temp.setTime("1" + i + i + i + "-1" + i + "-1" + i + "T2" + i + ":" + i + i + ":" + i + i);
			input.add(temp);
		}
	}
}