package userAPITest;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class UserApiDeleteTest {

	public static void main(String[] args) {
		// Create a CloseableHttpClient
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

			// Define the URL of the DELETE request
			String deleteUrl = "https://reqres.in/api/users/4";

			// Create an HttpDelete object
			HttpDelete httpDelete = new HttpDelete(deleteUrl);

			// Execute the DELETE request
			try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
				// Get the response entity
				HttpEntity entity = response.getEntity();
				System.out.println(response.getStatusLine().getStatusCode());
				System.out.println(response.getStatusLine().getReasonPhrase());

				// Print the response content
				if (entity != null) {
					System.out.println(EntityUtils.toString(entity));
				}
				if (response.getStatusLine().getStatusCode() == 204) {
					System.out.println("status code test passed");
				} else {
					System.out.println("status code test failed");
				}
				if ("No Content".equals(response.getStatusLine().getReasonPhrase())) {
					System.out.println("status phrase test passed");
				} else {
					System.out.println("status phrase test failed");
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
