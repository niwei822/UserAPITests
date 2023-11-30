
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.BaseTest;
import core.QaHttpUtil;
import core.QaHttpValidator;
import core.XlsUtil;

public class UserAPIGetTest extends BaseTest implements UserAPIConstants {

	@DataProvider(name = "dp1")
	public Object[][] createData() {
//		String[][] ids = { 
//				{ "5711480", "Avani Kaniyar"}, 
//				{ "5713354", "Siddhi Khan"}, 
//				{ "5711487", "Rudra Arora"}, 
//				{ "5711486", "Chinmayanand Pillai"}
//				};
		String[][] ids = XlsUtil.getTableArray("customer-api-data.xls", "Sheet1", "successfulids");
		return ids;
	}
	
	@DataProvider(name = "dp2")
	public Object[][] createData2() {
		String[][] ids = XlsUtil.getTableArray("customer-api-data.xls", "Sheet1", "lockedids");
		return ids;
	}

	@Test(dataProvider="dp1")
	public void testWithCorrectUserId(String userId, String name) throws ParseException, IOException {
		String url = base_URL + "/" + userId;
		HttpResponse response = QaHttpUtil.sendAndReceiveGetMessage(url);

		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);

		System.out.println(responseMsg);
		QaHttpValidator.performBasicHttpValidation(response, HTTP_CODE_200, HTTP_STATUS_MESSAGE_OK);
		Assert.assertTrue(responseMsg.contains(name));
	}

	@Test(dataProvider="dp2")
	public void testWithNonExistingtUserId(String userId, String name) throws ParseException, IOException {
		String url = base_URL + "/" + userId;
		System.out.println(url);
		HttpResponse response = QaHttpUtil.sendAndReceiveGetMessage(url);

		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);

		System.out.println(responseMsg);
		QaHttpValidator.performBasicHttpValidation(response, HTTP_CODE_404, HTTP_STATUS_MESSAGE_NOT_FOUND);
	}

	@Test
	public void testWithSpecialChar() throws ParseException, IOException {
		String url = base_URL + "/&&&";
		HttpResponse response = QaHttpUtil.sendAndReceiveGetMessage(url);

		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);

		System.out.println(responseMsg);
		QaHttpValidator.performBasicHttpValidation(response, HTTP_CODE_404, HTTP_STATUS_MESSAGE_NOT_FOUND);
		// Assert.assertTrue(responseMsg.contains("Govinda Mahajan"));
	}

	@Test
	public void testWithBlankId() throws ParseException, IOException {
		String url = base_URL + "/";
		HttpResponse response = QaHttpUtil.sendAndReceiveGetMessage(url);

		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);

		System.out.println(responseMsg);
		QaHttpValidator.performBasicHttpValidation(response, HTTP_CODE_200, HTTP_STATUS_MESSAGE_OK);

	}

	@Test
	public void testWithCreateUser() throws ParseException, IOException {
		// Create URI with base URL
		String url = base_URL;
        
		// Create a list to store POST parameters
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("name", "coco"));
		params.add(new BasicNameValuePair("email", "coco@mail.com"));
		params.add(new BasicNameValuePair("gender", "female"));
		params.add(new BasicNameValuePair("status", "active"));

		// Encode the parameters and set them in the request entity
		HttpResponse response = QaHttpUtil.sendAndReceivePostMessage(url, params, ACCESS_TOKEN);
		//System.out.println(ACCESS_TOKEN);

		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		String responseMsg = QaHttpUtil.getStringMessageFromResponseObject(response);

		System.out.println(responseMsg);
		QaHttpValidator.performBasicHttpValidation(response, HTTP_CODE_201, HTTP_STATUS_CREATED);
		Assert.assertTrue(responseMsg.contains("coco"));
	}
}
