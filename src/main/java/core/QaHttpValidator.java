package core;

import org.apache.http.HttpResponse;
import org.testng.Assert;

public class QaHttpValidator {
	
	public static void performBasicHttpValidation(HttpResponse response, int statusCode, String statusMsg) {
		Assert.assertEquals(response.getStatusLine().getStatusCode(), statusCode);
		Assert.assertEquals(response.getStatusLine().getReasonPhrase(), statusMsg);
	}


}
