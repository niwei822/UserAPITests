










package userAPITest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class UserApiPutTest {

    public static void main(String[] args) {
        // Create a CloseableHttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // Define the base URL of the PUT request
            String baseUrl = "https://reqres.in/api/users";

            // Set parameters for the PUT request
            URI uri = new URIBuilder(baseUrl)
                    .setPath("/2")  // Specify the resource path or ID you want to update
                    .build();

            // Create an HttpPut object
            HttpPut httpPut = new HttpPut(uri);

            // Set the request body
            String requestBody = "{\n" +
                    "    \"name\": \"morpheus\",\n" +
                    "    \"job\": \"zion resident\"\n" +
                    "}";
            httpPut.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
            
            // Execute the PUT request
            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                // Get the response entity
                HttpEntity entity = response.getEntity();
                System.out.println(response.getStatusLine().getStatusCode());
                System.out.println(response.getStatusLine().getReasonPhrase());

                // Print the response content
                if (entity != null) {
                    System.out.println(EntityUtils.toString(entity));
                }
                if (response.getStatusLine().getStatusCode() == 200) {
                    System.out.println("status code test passed");
                } else {
                    System.out.println("status code test failed");
                }
                if ("OK".equals(response.getStatusLine().getReasonPhrase())) {
                    System.out.println("status phrase test passed");
                } else {
                    System.out.println("status phrase test failed");
                }

            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
