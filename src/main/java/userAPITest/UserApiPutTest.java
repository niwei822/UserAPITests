package userAPITest;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.Test;

import core.BaseTest;
import core.JsonUtil;
import core.QaHttpUtil;
import core.QaHttpValidator;

public class UserApiPutTest extends BaseTest implements UserAPIConstants {

    @Test
    public void testWithUpdateUser() throws ClientProtocolException, IOException {
        // Assuming you have a method to retrieve an existing user by ID
    	String url = base_URL + "/" + 1700818;
    	System.out.println(url);
    	HttpResponse response = QaHttpUtil.sendAndReceiveGetMessage(url);
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);
		System.out.println(responseMsg);
		
		User userToUpdate = JsonUtil.getJavaObjectFromJsonString(responseMsg);
		
        // Modify the user data
		userToUpdate.setName("Aj");
		userToUpdate.setEmail("jaddd@example.com");
		userToUpdate.setGender("male");
		userToUpdate.setStatus("active");
		String jsonStr = JsonUtil.getJsonString(userToUpdate);
        // Send a PUT request to update the user
        HttpResponse res = QaHttpUtil.sendAndReceivePutMessage(url, jsonStr, ACCESS_TOKEN);
        //System.out.println(res);
        // Validate the response
        QaHttpValidator.performBasicHttpValidation(res, HTTP_CODE_200, HTTP_STATUS_MESSAGE_OK);
        String resMsg = QaHttpUtil.getStringMessageFromResponseObject(res);
        // Optionally, you can check the response body for specific details
        
        Assert.assertTrue(resMsg.contains("Aj"));

        System.out.println("User updated successfully!");
    }

 
    
}
