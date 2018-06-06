package com.cynthia.pageObj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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
import com.cynthia.base.TestBase;

/**
 * 
 * @author cylee1
 *@date 15 Sept
 */
public class DB2Connect extends MainBase{
	 private static final Logger log = Logger.getLogger(DB2Connect.class);
	 protected SoftAssert sAssert = new SoftAssert();
	
	public  String url = "jdbc:db2://DSNTDRDA:446/DSNT";
	public  String user = "remott1";
	public  String password = "logont1";
	public static Connection con = null;
	public static String driver2="com.ibm.db2.jcc.DB2Driver";
	public static final String pathmappingSheet="src//main//resources//MIS_MappingSheet.xlsx";
	
	public DB2Connect()
	{
		logNdReport(log, "Db2 validation");
	}
	public  void dBConnectUSItemQueryByUPC()
	{
		try
		{
			Class.forName(driver2).newInstance();
			Thread.sleep(10000);
//			con = DriverManager.getConnection(url,user,password);
//			con = DriverManager.getConnection(url,user,password);
			Statement stmt = con.createStatement();
			String query="SELECT ITEM_NBR,UPC_NBR,ITEM1_DESC,ACCOUNT_NBR,ACCT_NBR_TYPE_CODE,DEPT_NBR FROM ITEM WHERE UPC_NBR ='0068113180660' WITH UR;";
			log.debug(query);
			ResultSet rs=stmt.executeQuery(query);
			String valueResult [] = new String[200];
			int Count =1;
			while(rs.next())
			{
				for(int i=1;i<7;i++)
				{
				valueResult [Count] = rs.getString(i);
				log.debug(valueResult[i]);
				Count++;
				
				}
			}
			con.close();
			
		}
		catch(Exception e)
		{
			log.debug(e.getMessage());
		}
	}
		
	public  void ItemQueryExcel(String query,String sheetName,String Country,String div) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException, SQLException, IOException, EncryptedDocumentException, InvalidFormatException
	{
		    Class.forName(driver2).newInstance();
			Thread.sleep(10000);
			Country=Country.toUpperCase();
			div=div.toUpperCase();
			 url=(Country+"_"+div+"_URL");
			 user=(Country+"_"+div+"_USERNAME");
			 password=(Country+"_"+div+"_PASSWORD");

			con = DriverManager.getConnection(prop.getProperty(url),prop.getProperty(user),prop.getProperty(password));

			System.out.println("connection established");
			
			Statement stmt = con.createStatement();
			
			 List<String> attributeNameDB2=new ArrayList<String>();
			FileOutputStream outputStream=null;
			 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
			 System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
			 Sheet sheet=workbook.getSheet(sheetName);
			 DataFormatter dataFormatter = new DataFormatter();
			 Iterator<Row> rowIterator = sheet.rowIterator();
//			String query="SELECT * FROM ITEM WHERE ITEM_NBR='3820201'";
			logNdReport(log,query);
			ResultSet rs=stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while(rs.next())
			{
				for(int i=1;i<=columnsNumber;i++)
				{
					attributeNameDB2.add(rsmd.getColumnName(i));
				}	
				while (rowIterator.hasNext())
			 {
		            Row row = rowIterator.next();
		            if(row.getRowNum()==0)
		            {
		            	Cell cell=row.getCell(0);
			            String cellValue = dataFormatter.formatCellValue(cell);
		            	System.out.println("The header is "+cellValue);
		            	
		            }
		            else{
		            Cell cell=row.getCell(0);
		            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
		            
		            if(attributeNameDB2.contains(AttributeNameExcel))
		            {

//		            	System.out.println(AttributeNameExcel);
		            	String valueDB2=rs.getString(AttributeNameExcel);
//		            	System.out.println(valueDB2);
		            	Cell updateCell=row.getCell(1);
		            	 if(valueDB2==null)
		 	            {
		 	            	// when the value to be enter is null
		 	            	updateCell.setCellValue("null");
		 	            }
		 	            else{
		 	            updateCell.setCellValue(valueDB2);}

		            }
		            else{
		            	Cell updateCell=row.getCell(1);
		            	updateCell.setCellValue("NA");
		            	System.out.println(AttributeNameExcel+" is missing in db2 response");}
		            
		            }
		            
			 }
	}

			 outputStream = new FileOutputStream("target//MIS_MappingSheet.xlsx");
	         workbook.write(outputStream);    
			 workbook.close();
			 outputStream.close();	
		    con.close();
			
	}	
	
	public  void tiQueryExcel(String upcnumber,String sheetName,String Country,String div) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException, SQLException, IOException, EncryptedDocumentException, InvalidFormatException
	{
		    Class.forName(driver2).newInstance();
			Thread.sleep(10000);
			Country=Country.toUpperCase();
			div=div.toUpperCase();
			 url=(Country+"_"+div+"_URL");
			 user=(Country+"_"+div+"_USERNAME");
			 password=(Country+"_"+div+"_PASSWORD");
			
			con = DriverManager.getConnection(prop.getProperty(url),prop.getProperty(user),prop.getProperty(password));

			System.out.println("connection established");
			String BATT_PWR_TYPE_CODEvalueDB2 = null,
					UCC_GLN_IDvalueDB2 = null,UCC_PRODUCT_TYP_CDvalueDB2 = null,
					UCC_ORDERABLE_INDvalueDB2 = null,UCC_CSUMR_UNIT_INDvalueDB2=null,
							BRICK_NBRvalueDB2=null;
			Statement stmt = con.createStatement();
			
			 List<String> attributeNameDB2=new ArrayList<String>();
			FileOutputStream outputStream=null;
			 Workbook workbook = WorkbookFactory.create(new File(pathmappingSheet));
			 System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
			 Sheet sheet=workbook.getSheet(sheetName);
			 DataFormatter dataFormatter = new DataFormatter();
			 Iterator<Row> rowIterator = sheet.rowIterator();
//			String query="SELECT * FROM ITEM WHERE ITEM_NBR='3820201'";
			 String queryItem="SELECT * FROM ITEM WHERE UPC_NBR='"+upcnumber+"'";
			 String queryTI="SELECT * FROM GLOBAL_TRADE_ITEM WHERE UPC_NBR='"+upcnumber+"'";
			 String queryItemUcc="SELECT * FROM ITEM_UCCNET WHERE ITEM_NBR in(SELECT ITEM_NBR FROM ITEM WHERE upc_nbr IN('"+upcnumber+"'))";
			logNdReport(log,queryItem);
			logNdReport(log,queryTI);
			logNdReport(log,queryItemUcc);
			ResultSet rsti=stmt.executeQuery(queryTI);
			while(rsti.next())
			{
				BATT_PWR_TYPE_CODEvalueDB2=rsti.getString("BATT_PWR_TYPE_CODE");
			}
			ResultSet rsUcc=stmt.executeQuery(queryItemUcc);
			while(rsUcc.next())
			{
				UCC_GLN_IDvalueDB2=rsUcc.getString("UCC_GLN_ID");
				UCC_PRODUCT_TYP_CDvalueDB2=rsUcc.getString("UCC_PRODUCT_TYP_CD");
				UCC_ORDERABLE_INDvalueDB2=rsUcc.getString("UCC_ORDERABLE_IND");
				UCC_CSUMR_UNIT_INDvalueDB2=rsUcc.getString("UCC_CSUMR_UNIT_IND");
				BRICK_NBRvalueDB2=rsUcc.getString("BRICK_NBR");
			}
			
			ResultSet rs=stmt.executeQuery(queryItem);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while(rs.next())
			{
				for(int i=1;i<=columnsNumber;i++)
				{
					attributeNameDB2.add(rsmd.getColumnName(i));
				}	
				while (rowIterator.hasNext())
			 {
		            Row row = rowIterator.next();
		            if(row.getRowNum()==0)
		            {
		            	Cell cell=row.getCell(0);
			            String cellValue = dataFormatter.formatCellValue(cell);
		            	System.out.println("The header is "+cellValue);
		            	
		            }
		            else{
		            Cell cell=row.getCell(0);
		            String AttributeNameExcel = dataFormatter.formatCellValue(cell);
		            
		            if(attributeNameDB2.contains(AttributeNameExcel))
		            {

//		            	System.out.println(AttributeNameExcel);
		            	String valueDB2=rs.getString(AttributeNameExcel);
//		            	System.out.println(valueDB2);
		            	Cell updateCell=row.getCell(1);
		            	 if(valueDB2==null)
		 	            {
		 	            	// when the value to be enter is null
		 	            	updateCell.setCellValue("null");
		 	            }
		 	            else{
		 	            updateCell.setCellValue(valueDB2);}

		            }
		            else{
		            	if(AttributeNameExcel.equalsIgnoreCase("BATT_PWR_TYPE_CODE")){
		            		Cell updateCell=row.getCell(1);
			            	 if((BATT_PWR_TYPE_CODEvalueDB2)==null)
			 	            {updateCell.setCellValue("null"); }
			 	            else{ updateCell.setCellValue(BATT_PWR_TYPE_CODEvalueDB2);}
		            }	
		            	else if(AttributeNameExcel.equalsIgnoreCase("UCC_GLN_ID"))
		            	{
		            		Cell updateCell=row.getCell(1);
			            	 if((UCC_GLN_IDvalueDB2)==null)
			 	            {updateCell.setCellValue("null"); }
			 	            else{ updateCell.setCellValue(UCC_GLN_IDvalueDB2);}
		            	}
		            	else if(AttributeNameExcel.equalsIgnoreCase("UCC_PRODUCT_TYP_CD"))
		            	{
		            		Cell updateCell=row.getCell(1);
			            	 if((UCC_PRODUCT_TYP_CDvalueDB2)==null)
			 	            {updateCell.setCellValue("null"); }
			 	            else{ updateCell.setCellValue(UCC_PRODUCT_TYP_CDvalueDB2);}
		            	}
		            	else if(AttributeNameExcel.equalsIgnoreCase("UCC_ORDERABLE_IND"))
		            	{
		            		Cell updateCell=row.getCell(1);
			            	 if((UCC_ORDERABLE_INDvalueDB2)==null)
			 	            {updateCell.setCellValue("null"); }
			 	            else{ updateCell.setCellValue(UCC_ORDERABLE_INDvalueDB2);}
		            	}
		            	else if(AttributeNameExcel.equalsIgnoreCase("UCC_CSUMR_UNIT_IND"))
		            	{
		            		Cell updateCell=row.getCell(1);
			            	 if((UCC_CSUMR_UNIT_INDvalueDB2)==null)
			 	            {updateCell.setCellValue("null"); }
			 	            else{ updateCell.setCellValue(UCC_CSUMR_UNIT_INDvalueDB2);}
		            	}
		            	else if(AttributeNameExcel.equalsIgnoreCase("BRICK_NBR"))
		            	{
		            		Cell updateCell=row.getCell(1);
			            	 if((BRICK_NBRvalueDB2)==null)
			 	            {updateCell.setCellValue("null"); }
			 	            else{ updateCell.setCellValue(BRICK_NBRvalueDB2);}
		            	}
		            	else
		            	{
		            	Cell updateCell=row.getCell(1);
		            	updateCell.setCellValue("NA");
		            	System.out.println(AttributeNameExcel+" is missing in db2 response");}
		            }
		            }
		            
			 }
	}

			 outputStream = new FileOutputStream("target//MIS_MappingSheet.xlsx");
	         workbook.write(outputStream);    
			 workbook.close();
			 outputStream.close();	
		    con.close();
			
	}
	public List<String> ValidItemQuery(String query, String Country, String div) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException, SQLException {
		 Class.forName(driver2).newInstance();
		Thread.sleep(10000);
		Country=Country.toUpperCase();
		div=div.toUpperCase();
		 url=(Country+"_"+div+"_URL");
		 user=(Country+"_"+div+"_USERNAME");
		 password=(Country+"_"+div+"_PASSWORD");
		 con = DriverManager.getConnection(prop.getProperty(url),prop.getProperty(user),prop.getProperty(password));
		 System.out.println("connection established");
		Statement stmt = con.createStatement();
		logNdReport(log,query);
		ResultSet rs=stmt.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		List <String> StoreDb2=new ArrayList<String>();
		while(rs.next())
		{
			for(int i=1;i<=columnsNumber;i++)
			{
			String attributeName=rsmd.getColumnName(i);	
			String value=rs.getString(i);
			StoreDb2.add(value);
		    System.out.println(value);
			}
		}
		return StoreDb2;
		
	}	

}
