package com.internal.tasks.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.internal.tasks.beans.ResponseRestFulWS;

public class ClientTask2 {

	public static void main(String[] args) {
		try {

			String jsonCollection = convertFromColletctionToJson();
			System.out.println(jsonCollection);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet("http://localhost:8080/RESTfulTasks/rest/tasks/task2GET/"
					+ URLEncoder.encode(jsonCollection, "UTF-8"));
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server ....");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

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
			temp.setName("PERSON" + i);
			temp.setTime("1"+i+i+i+"-1"+i+"-1"+i+"T2"+i+":"+i+i+":"+i+i);
			input.add(temp);
		}
	}
}