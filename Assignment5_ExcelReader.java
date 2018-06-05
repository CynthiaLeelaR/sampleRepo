package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
/**
 * 
 * @author SA765030
 * Task : Read an excel sheet consisting of following elements:
name          firstname
user id       firstuserid
email id      firstemailid
phone         firstphone

 */
public class Assignment5_ExcelReader {
	@Test
	public static void excelReader() throws IOException{
		
		String path = "src\\Assigmnet5_ExcelReading.xlsx";
		FileInputStream fs = new FileInputStream(path);
		Workbook wb = new XSSFWorkbook(fs);
		Sheet sh = wb.getSheet("Sheet1");
        Iterator<Row> iterator = sh.iterator();
        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if(nextRow.getRowNum()==1){
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                 System.out.print(cell.getStringCellValue() +"\n");         
                }
            }
        }
         
        wb.close();
        fs.close();
 
		
	}

}
