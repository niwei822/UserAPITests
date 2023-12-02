package userAPITest;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import core.BaseTest;
import core.JsonUtil;
import core.QaHttpUtil;
import core.QaHttpValidator;

public class UserApiPostTest extends BaseTest implements UserAPIConstants {

	@Test
	public void testWithCreateUser() throws ParseException, IOException {
		// Create URI with base URL
		String url = base_URL;
        
		User newUser = JsonUtil.createUser("John Doe", "john.doe13425677@example.com", "male", "active");
		String jsonStr = JsonUtil.getJsonString(newUser);
		HttpResponse response = QaHttpUtil.sendAndReceivePostMessage2(url, jsonStr, ACCESS_TOKEN);
		//System.out.println(ACCESS_TOKEN);

		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);

		System.out.println(responseMsg);
		QaHttpValidator.performBasicHttpValidation(response, HTTP_CODE_201, HTTP_STATUS_CREATED);
		Assert.assertTrue(responseMsg.contains("John Doe"));
	}
}
