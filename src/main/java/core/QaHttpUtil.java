package core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;




public class QaHttpUtil {
	
	public static HttpResponse sendAndReceiveGetMessage(String url) throws IOException, ClientProtocolException {
		HttpGet request = new HttpGet(url);
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		return response;
	}
	
	public static HttpResponse sendAndReceivePostMessage(String url, List<NameValuePair> params, String accessToken) throws IOException, ClientProtocolException {
		HttpPost request = new HttpPost(url);
		//final String ACCESS_TOKEN = "62e04d19ecbbc59ff42afe82f21020b617beabe513dede11bad5c41edd4161fb";
		// Add access token to headers
		request.addHeader("Authorization", "Bearer " + accessToken);;
		
		// Check if params is not null or empty before setting the request entity
	    if (params != null && !params.isEmpty()) {
	        request.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
	    }
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		return response;
	}
	
	public static HttpResponse sendAndReceivePostMessage2(String url, String jsonBody, String accessToken) throws IOException, ClientProtocolException {
	    HttpPost request = new HttpPost(url);

	    // Add access token to headers
	    request.addHeader("Authorization", "Bearer " + accessToken);
	    request.addHeader("Content-Type", "application/json"); // Set the content type to JSON

	    // Set the request entity as JSON
	    StringEntity entity = new StringEntity(jsonBody);
	    request.setEntity(entity);

	    HttpClient client = HttpClientBuilder.create().build();
	    HttpResponse response = client.execute(request);
	    return response;
	}

	public static HttpResponse sendAndReceivePutMessage(String url, String message, String accessToken) throws IOException, ClientProtocolException {
		HttpPut request = new HttpPut(url);
		
		// Add access token to headers
	    request.addHeader("Authorization", "Bearer " + accessToken);
	    request.addHeader("Content-Type", "application/json"); // Set the content type to JSON
		
		StringEntity entity = new StringEntity(message);
		request.setEntity(entity);
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		return response;
	}
	
	public static HttpResponse sendAndReceiveDeleteMessage(String url) throws IOException, ClientProtocolException {
		HttpDelete request = new HttpDelete(url);
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		return response;
	}
	
	public static String getStringMessageFromResponseObject(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		String responseMsg = "";
	    if (entity != null) {
	        // return it as a String
	        responseMsg = EntityUtils.toString(entity);
	        //System.out.println(result);
	    }
	    return responseMsg;
	}
	
	



}
