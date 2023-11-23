
import java.io.File;
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
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class UserAPIGetTest extends BaseTest implements UserAPIConstants {

	@DataProvider(name = "dp1")
	public Object[][] createData() {
//		String[][] ids = { 
//				{ "5711480", "Avani Kaniyar"}, 
//				{ "5713354", "Siddhi Khan"}, 
//				{ "5711487", "Rudra Arora"}, 
//				{ "5711486", "Chinmayanand Pillai"}
//				};
		String[][] ids = getTableArray("customer-api-data.xls", "Sheet1", "successfulids");
		return ids;
	}
	
	@DataProvider(name = "dp2")
	public Object[][] createData2() {
		String[][] ids = getTableArray("customer-api-data.xls", "Sheet1", "lockedids");
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
	
	//customer-api-data.xls, Sheet1, successfulids
	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName){
        String[][] tabArray=null;
        try{
            Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
            Sheet sheet = workbook.getSheet(sheetName);
            int startRow,startCol, endRow, endCol,ci,cj;
            Cell tableStart=sheet.findCell(tableName);
            startRow=tableStart.getRow();
            startCol=tableStart.getColumn();

            Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);                               

            endRow=tableEnd.getRow();
            endCol=tableEnd.getColumn();
            System.out.println("startRow="+startRow+", endRow="+endRow+", " +
                    "startCol="+startCol+", endCol="+endCol);
            tabArray=new String[endRow-startRow-1][endCol-startCol-1];
            ci=0;

            for (int i=startRow+1;i<endRow;i++,ci++){
                cj=0;
                for (int j=startCol+1;j<endCol;j++,cj++){
                    tabArray[ci][cj]=sheet.getCell(j,i).getContents();
                }
            }
        }
        catch (Exception e)    {
            System.out.println("error in getTableArray()");
        }

        return(tabArray);
    }


}
