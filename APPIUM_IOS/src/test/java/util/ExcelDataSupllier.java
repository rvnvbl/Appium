package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelDataSupllier {
   static Object[][] data;

   public static Object[][] getData() throws IOException {
      String sheetName = "UserTestDataiOS.xlsx";
      File excelFile = new File("./src/test/java/testdata/" + sheetName);

      FileInputStream fis = new FileInputStream(excelFile);
      XSSFWorkbook workbook = new XSSFWorkbook(fis);
      XSSFSheet sheet = workbook.getSheet("User");
      int noOfRows = sheet.getPhysicalNumberOfRows();
      int noOfCol = sheet.getRow(0).getLastCellNum();
      data = new String[noOfRows - 1][noOfCol];
      for (int i = 0; i < noOfRows - 1; i++) {
         for (int j = 0; j < noOfCol; j++) {
            DataFormatter df = new DataFormatter();
            data[i][j] = df.formatCellValue(sheet.getRow(i + 1).getCell(j));
         }
      }
      // workbook.close();
      fis.close();

      return data;
   }

   @DataProvider(name = "loginData")
   public static Object[][] getDetails(Method m) throws IOException {
      Object[][] details = new String[1][2];
      int numRow = 0;

      for (Object[] dataArr : getData()) {
         numRow++;
      }
      for (int i = 0; i < numRow; i++) {
         // Checking the method are using
         if (data[i][0].equals(m.getName())) {
            details[0][0] = data[i][1];
            details[0][1] = data[i][2];
         }
      }
      return details;
   }
}
