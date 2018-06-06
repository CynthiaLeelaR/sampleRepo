package com.cynthia.pageObj;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.cynthia.base.MainBase;
/**
 * 
 * @author cylee1
 *
 */
public class ExcelCompare extends MainBase 
{
	public static final String pathmappingSheet="src//main//resources//MIS_MappingSheet.xlsx";
	private static final Logger log = Logger.getLogger(ExcelCompare.class);	

	 public void excelCompare(String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException
	 {

		 SoftAssert s_assert = new SoftAssert();
		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {//these are the header cells
	            }
	            else{
	            String cellValueDB=null;
	            Cell cellDBAttribute =	row.getCell(0);
	            String cellAttributeDB = dataFormatter.formatCellValue(cellDBAttribute)	;

	            Cell cell=row.getCell(1);
	            //saving the value by trimming out the extra spaces if any
	            cellValueDB = dataFormatter.formatCellValue(cell).trim();
	            // changing the value to boolean if Y and N are present
	            if (cellValueDB.equalsIgnoreCase("Y")) 
	            {
	            	cellValueDB = "true";
	            } 
	            else if(cellValueDB.equalsIgnoreCase("N")) {
	            		cellValueDB = "false";
	            	}
	            if(cellValueDB.equalsIgnoreCase("null")){cellValueDB="NA";}
	            if(cellValueDB.equalsIgnoreCase("[]")||cellValueDB.equalsIgnoreCase("")){cellValueDB="NA"; }
//	            System.out.println(cellValueDB);
//	           }
	            //json value retreive
	            Cell cell2=row.getCell(3);
	            String cellValueJson = dataFormatter.formatCellValue(cell2);
	            //removing if any [,]," symbols are present in the value
	            cellValueJson=cellValueJson.replace("[", "");
	            cellValueJson= cellValueJson.replace("]", "");
	            cellValueJson=cellValueJson.replaceAll("\"", "");
	            cellValueJson=cellValueJson.trim();
	            if(cellValueJson.equalsIgnoreCase("null")||cellValueJson.equalsIgnoreCase("")){cellValueJson="NA";}
//	           System.out.println(cellValueJson);
	           
	           //comparison
	            if(cellAttributeDB.contains("UPC")||cellAttributeDB.contains("AMT")||cellAttributeDB.contains("QTY")||cellAttributeDB.contains("FEE"))
	            {
	            	boolean upcMatch=false;
	            	if(cellValueJson.contains(cellValueDB))
	            	{upcMatch=true;}
	            	else if(cellValueDB.contains(cellValueJson))
	            	{upcMatch=true;}
	            	else{upcMatch=false;}
	            	s_assert.assertTrue(upcMatch, "=>The value from  DB2 is "+cellValueDB+" but the  value from json response is "+cellValueJson+" for the attribute "+cellAttributeDB);
	            }
	            else
	            {
	            s_assert.assertEquals(cellValueDB, cellValueJson,"=>The value from DB2 is "+cellValueDB+" but the value from json response is "+cellValueJson+" for the attribute "+cellAttributeDB);
	            }
	           }
	           
		 }
		 s_assert.assertAll();
		 workbook.close();
	
 }
	 
	public void excelComparedb2Multivalue(String sheetName,int nthItemNumber) throws EncryptedDocumentException, InvalidFormatException, IOException
	 {
		 int getjsonValueexcel=3;
		 if(nthItemNumber!=0){getjsonValueexcel=getjsonValueexcel+1;}
		 SoftAssert s_assert = new SoftAssert();

		 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
		 Sheet sheet=workbook.getSheet(sheetName);
		 DataFormatter dataFormatter = new DataFormatter();
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext())
		 {
	            Row row = rowIterator.next();
	            if(row.getRowNum()==0)
	            {//these are the header cells
	            }
	            else{
	            String cellValueDB=null;
	            Cell cellDBAttribute =	row.getCell(0);
	            String cellAttributeDB = dataFormatter.formatCellValue(cellDBAttribute)	;
	            //trimming out the last two digit of float
	            if(cellAttributeDB.equalsIgnoreCase("SELL_QTY"))
	            {
	            	Cell cell=row.getCell(1);
	            	cellValueDB = dataFormatter.formatCellValue(cell).trim();
	            	if(cellValueDB.contains(".00"))
	            	cellValueDB=cellValueDB.substring(0, cellValueDB.length() - 5);
	            	else if(cellValueDB.contains("."))
		            	cellValueDB=cellValueDB.substring(0, cellValueDB.length() - 2);
	            }
	            else{
	            Cell cell=row.getCell(1);
	            //saving the value by trimming out the extra spaces if any
	            cellValueDB = dataFormatter.formatCellValue(cell).trim();
	            // changing the value to boolean if Y and N are present
	            if (cellValueDB.equalsIgnoreCase("Y")) 
	            {
	            	cellValueDB = "true";
	            } 
	            else if(cellValueDB.equalsIgnoreCase("N")) {
	            		cellValueDB = "false";
	            	}
//	            System.out.println(cellValueDB);
	           }
	            if(cellValueDB.equalsIgnoreCase("null")){cellValueDB="NA";}
	            if(cellValueDB.equalsIgnoreCase("[]")||cellValueDB.equalsIgnoreCase("")){cellValueDB="NA"; }
	            //json value retreive
	            Cell cell2=row.getCell(getjsonValueexcel);
	            String cellValueJson = dataFormatter.formatCellValue(cell2);
	            

	            //removing if any [,]," symbols are present in the value
	            cellValueJson=cellValueJson.replace("[", "");
	            cellValueJson= cellValueJson.replace("]", "");
	            cellValueJson=cellValueJson.replaceAll("\"", "");
	            if(cellValueJson.equalsIgnoreCase("null")){cellValueJson="NA";}
	            if(cellAttributeDB.contains("AMT")||cellAttributeDB.contains("QTY"))
	           	 {
	            	if(cellAttributeDB.contains("BASE_UNIT_RTL_AMT"))
	            	{
	            		if(cellValueJson.endsWith(".0"))
	  	           	  {
	  	           		cellValueJson=cellValueJson+"0";  
	  	           	  }
	            	}
	            else if(cellValueJson.endsWith(".0"))
	           	  {
	           		cellValueJson=cellValueJson.split(".0")[0];  
	           	  }
	           	  
	           	 }
	            if(cellAttributeDB.contains("VNPK_COST_AMT")||cellAttributeDB.contains("WHPK_SELL_AMT"))
	            {
	            	if(cellValueDB.endsWith("00")){
	            		cellValueJson=cellValueJson+"00";
	            	}
	            	else if(cellValueDB.endsWith("0")){
	            	cellValueJson=cellValueJson+"0";}
	            }
	            
	            
//	           System.out.println(cellValueJson);
	           
	           //comparison
	            s_assert.assertEquals(cellValueDB, cellValueJson,"=>The value from DB2 is "+cellValueDB+" but the value from json response is "+cellValueJson+" for the atrribute "+cellAttributeDB);
	        
	           }
	           
		 }
		 s_assert.assertAll();
		 workbook.close();
	
 }

	 
}
