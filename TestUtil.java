package com.cynthia.utils;

public class TestUtil {
			
	// find is the test suite is runnable
	public static boolean isSuiteRunnable(XlsReader xls, String suiteName){
		boolean isExecutable=false;
		for(int i=2;i<=xls.getRowCount("Test Suite");i++){
				String suite = xls.getCellData("Test Suite", "TSID", i);
				String runmode = xls.getCellData("Test Suite", "Runmode", i);
				if(suite.equalsIgnoreCase(suiteName)){
					if(runmode.equalsIgnoreCase("Y")){
						isExecutable=true;
					}else{
						isExecutable=false;
					}
				}
		}
		xls=null; // release memory
		return isExecutable;
		
	}
	
	// returns true if runmode of the test is equal to Y
	public static boolean isTestCaseRunnable(XlsReader xls, String testCaseName){
		boolean isExecutable=false;
		for(int i=2;i<=xls.getRowCount("Test Cases");i++){
				String tcid=xls.getCellData("Test Cases", "TCID",i);
				String runmode=xls.getCellData("Test Cases", "Runmode", i);
				if(tcid.equals(testCaseName)){
					if(runmode.equalsIgnoreCase("Y")){
						isExecutable=true;
					}else{
						isExecutable=false;
					}
				}
		}
		xls=null;
		return isExecutable;
		
	}
	
	//checking Runmode for dataset
	public static String[] getDataSetRunmodes(XlsReader xlsFile, String sheetName){
		String[] runmodes=null;
		if(!xlsFile.isSheetExist(sheetName)){
			xlsFile=null;
			sheetName=null;
			runmodes=new String[1];
			runmodes[0]="Y";
			xlsFile=null;
			sheetName=null;
			return runmodes;
		}
		runmodes=new String [xlsFile.getRowCount(sheetName)-1];
		for(int i=2;i<=runmodes.length+1;i++){
			runmodes[i-2]=xlsFile.getCellData(sheetName, "Runmode", i);
		}
		xlsFile=null;
		sheetName=null;
		return runmodes;
		
	}
	
	
	// returns the test data from a test in 2 dim array
	public static Object[][] getData(XlsReader xls , String testCaseName){
		// if the sheet is not present
		if(! xls.isSheetExist(testCaseName)){
			xls=null;
//			System.out.println("xxxxxxxx");
			return new Object[1][0];
		}
		
		int rows = xls.getRowCount(testCaseName);
		int cols = xls.getColumnCount(testCaseName);
				
		
		Object[][] data = new Object[rows-1][cols];		
		for(int rowNum=2;rowNum<=rows;rowNum++){
			for (int colNum=0;colNum<cols;colNum++){
				data[rowNum-2][colNum]=xls.getCellData(testCaseName, colNum, rowNum);
			}
			
		}
//		System.out.println("data:::"+data);
		return data;
	}
	
	// update results for a particular data sets
	public static void reportDataSetResult(XlsReader xls, String testCaseName, int rowNum, String result){
		xls.setCellData(testCaseName, "Results", rowNum, result);
		
	}

}
