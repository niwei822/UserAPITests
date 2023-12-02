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

public class UserApiGetTest extends BaseTest implements UserAPIConstants {
	@Test
    public void testWithCorrectUserId() throws ClientProtocolException, IOException {
    	String url = base_URL + "/" + 1700815;
    	System.out.println(url);
    	
    	HttpResponse response = QaHttpUtil.sendAndReceiveGetMessage(url);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);
		System.out.println(responseMsg);
		QaHttpValidator.performBasicHttpValidation(response, HTTP_CODE_200, HTTP_STATUS_MESSAGE_OK);
		
		User user = JsonUtil.getJavaObjectFromJsonString(responseMsg);
		
		//Assert.assertTrue(responseMsg.contains("12"));
		//Assert.assertTrue(responseMsg.contains("Janet"));
		Assert.assertEquals(user.getName(), "Budhil Kaul JD");
    }
}
