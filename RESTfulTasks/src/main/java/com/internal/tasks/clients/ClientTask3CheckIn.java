package com.internal.tasks.clients;

import java.net.URLEncoder;

import org.apache.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ClientTask3CheckIn {

	public static void main(String[] args) {
		try {

			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);
			WebResource webResource = client.resource(
					"http://localhost:8080/RESTfulTasks/rest/tasks/task3CheckIn/" + URLEncoder.encode("EMPLOYEE1", "UTF-8"));
			ClientResponse response = webResource.accept("application/json").type("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != HttpStatus.SC_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			System.out.println("Output from Server ....");
			String output = response.getEntity(String.class);
			System.out.println(output);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}