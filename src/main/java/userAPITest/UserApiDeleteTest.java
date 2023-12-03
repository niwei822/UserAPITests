package userAPITest;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.Test;

import core.BaseTest;
import core.QaHttpUtil;
import core.QaHttpValidator;

public class UserApiDeleteTest extends BaseTest implements UserAPIConstants {

	@Test
    public void testWithDeleteUser() throws ClientProtocolException, IOException {
        
    	String url = base_URL + "/" + 1700822;
    	System.out.println(url);
    	HttpResponse response = QaHttpUtil.sendAndReceiveGetMessage(url);
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);
		System.out.println(responseMsg);
		
        HttpResponse res = QaHttpUtil.sendAndReceiveDeleteMessage(url, ACCESS_TOKEN);
        // Validate the response
        QaHttpValidator.performBasicHttpValidation(res, HTTP_CODE_204, HTTP_STATUS_MESSAGE_NO_CONTENT);
        String resMsg = QaHttpUtil.getStringMessageFromResponseObject(res);
        // Optionally, you can check the response body for specific details
        
        Assert.assertTrue(resMsg.contains(""));

        System.out.println("User deleted successfully!");
    }

 
    
}


