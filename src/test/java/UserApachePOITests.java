import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.BaseTest;
import core.QaHttpUtil;
import core.QaHttpValidator;

public class UserApachePOITests extends BaseTest implements UserAPIConstants {
	@DataProvider(name = "dp1")
    public Object[][] createData() {
        String[][] ids = getExcelData("customer-api-data.xlsx", "Sheet1", "successfulids");
        return ids;
    }

    @DataProvider(name = "dp2")
    public Object[][] createData2() {
        String[][] ids = getExcelData("customer-api-data.xlsx", "Sheet1", "lockedids");
        return ids;
    }
    
    @Test(dataProvider = "dp1")
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
    
    @Test(dataProvider = "dp2")
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
    
    private String[][] getExcelData(String filePath, String sheetName, String tableName) {
        String[][] tabArray = null;
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheet(sheetName);

            // Find the table start cell
            Cell tableStart = sheet.findCell(tableName);
            int startRow = tableStart.getRow();
            int startCol = tableStart.getColumn();

            // Find the table end cell
            Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, 100, 64000, false);

            int endRow = tableEnd.getRow();
            int endCol = tableEnd.getColumn();

            System.out.println("startRow=" + startRow + ", endRow=" + endRow + ", " +
                    "startCol=" + startCol + ", endCol=" + endCol);

            tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];

            int ci = 0;

            for (int i = startRow + 1; i < endRow; i++, ci++) {
                int cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++) {
                    tabArray[ci][cj] = sheet.getRow(i).getCell(j).getStringCellValue();
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading Excel data: " + e.getMessage());
        }

        return tabArray;
    }
}

