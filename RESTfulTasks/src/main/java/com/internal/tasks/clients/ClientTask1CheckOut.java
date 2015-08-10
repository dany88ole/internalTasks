package com.internal.tasks.clients;

import org.apache.http.HttpStatus;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ClientTask1CheckOut {

	public static void main(String[] args) {
		try {
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client = Client.create(clientConfig);
			WebResource webResource = client.resource("http://localhost:8080/RESTfulTasks/rest/tasks/task1CheckOut");
			ClientResponse response = webResource.accept("application/json").type("application/json")
					.post(ClientResponse.class, "EMPLOYEE1");

			if (response.getStatus() != HttpStatus.SC_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			String output = response.getEntity(String.class);
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}