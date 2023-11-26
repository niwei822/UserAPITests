import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import core.BaseTest;
import core.QaHttpUtil;
import core.QaHttpValidator;

public class UserApachePOITests extends BaseTest implements UserAPIConstants {
	@DataProvider(name = "dp1")
    public Object[][] createData() {
        String[][] ids = getExcelData("customer-api-data (4).xlsx", "Sheet1");
        for (String[] row : ids) {
            System.out.println(Arrays.toString(row));
        }
        return ids;
    }

    @DataProvider(name = "dp2")
    public Object[][] createData2() {
    	String[][] ids = getExcelData("customer-api-data (4).xlsx", "Sheet2");
    	for (String[] row : ids) {
            System.out.println(Arrays.toString(row));
        }
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
    
    private String[][] getExcelData(String filePath, String sheetName) {
        String[][] tabArray = null;
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheet(sheetName);

         // Find the table start cell (assuming there is no specific table name)
            int startRow = sheet.getFirstRowNum() + 1;
            int startCol = sheet.getRow(startRow).getFirstCellNum();

            // Find the table end cell
            int endRow = sheet.getLastRowNum() + 1; // Adding 1 to include the last row
            int endCol = sheet.getRow(startRow).getLastCellNum();

            System.out.println("startRow=" + startRow + ", endRow=" + endRow + ", " +
                    "startCol=" + startCol + ", endCol=" + endCol);

         // Read data into a 2D array
            tabArray = new String[endRow - startRow][endCol - startCol];
            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            tabArray[i - startRow][j - startCol] = String.valueOf((int) cell.getNumericCellValue());
                        } else {
                            tabArray[i - startRow][j - startCol] = cell.getStringCellValue();
                        }
                    } else {
                        tabArray[i - startRow][j - startCol] = "";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading Excel data: " + e.getMessage());
        }

        return tabArray;
}
}