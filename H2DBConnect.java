package com.cynthia.pageObj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
//import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
/**
 * 
 * @author cylee1
 * @date 15 Sept
 *
 */
public class H2DBConnect {
	private static final Logger LOGGER = Logger.getLogger(H2DBConnect.class);
	
	public static String url="jdbc:h2:tcp://172.16.142.251:9124/~/../log/apache-tomcat/linked-charges-job/linked-charges";
    public static String user = "emi";
    public static String password = "emi";
    public static  String compareValue ;
    

        
  
    
    public void h2Connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
	
    	Connection con = DriverManager.getConnection(url,user,password);
    	 String query="SELECT * FROM US_WM_POS_LINKED_UPC WHERE PRIMARY_UPC IN ('0000000051376','0000000020680');";
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         int count=0;
         String PRIMARY_UPC [] = new String [20];
         String LINKED_UPC [] =new String [20];
         String EFFECTIVE_DATE [] =new String [20];
         while(rs.next())
         {
        	 PRIMARY_UPC [count]=rs.getString("PRIMARY_UPC");
            LOGGER.debug("the attribute name is : "+PRIMARY_UPC [count]);
            LINKED_UPC [count]=rs.getString("LINKED_UPC");
            LOGGER.debug("The value is : "+LINKED_UPC [count]); 
            EFFECTIVE_DATE [count]=rs.getString("EFFECTIVE_DATE");
            LOGGER.debug("The value is : "+EFFECTIVE_DATE [count]); 
            count++;                     
         }
                
         con.close();
    }
    
    public void h2ConnectLinkedChargesComparison(String uPCs,int arrayCount,String[] primaryUPCResponse,String[] linkedUPCResponse) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
	
    	Connection con = DriverManager.getConnection(url,user,password);
    	 String query="SELECT * FROM US_WM_POS_LINKED_UPC WHERE PRIMARY_UPC IN "+uPCs+";";
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         int count=0;
         String PRIMARY_UPC [] = new String [1000];
         String LINKED_UPC [] =new String [1000];
         String EFFECTIVE_DATE [] =new String [1000];
         while(rs.next())
         {
        	 PRIMARY_UPC [count]=rs.getString("PRIMARY_UPC");
            LINKED_UPC [count]=rs.getString("LINKED_UPC"); 
            EFFECTIVE_DATE [count]=rs.getString("EFFECTIVE_DATE");
            count++;                     
         }
         con.close();

 		String H2DB[]= new String[count];
 		String H2Res[]= new String[count];
 		int lcount=0;
 		for (int i=0; i<count;i++)
 		{
 			if(PRIMARY_UPC[i]!="null")
 			{ 				H2DB[i]=PRIMARY_UPC[i]+LINKED_UPC[i]; 			}
 		}
 		
 		for (int i=0; i<arrayCount;i++)
 		{
 			primaryUPCResponse[i]=("0000000000000" + primaryUPCResponse[i]).substring(primaryUPCResponse[i].length());
 			
 			if(linkedUPCResponse[i]!="null")
 			{
 				H2Res[lcount]=primaryUPCResponse[i]+linkedUPCResponse[i];

 				lcount++;
 			}
 		}
 		Assert.assertArrayEquals( H2DB, H2Res);
 		Reporter.log("Cross verified and ensured the response from response and DB are same.");
    }
    
    public boolean h2ConnectNegative(String uPCs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
	
    	Boolean flag=false;
    	Connection con = DriverManager.getConnection(url,user,password);
    	 String query="SELECT * FROM US_WM_POS_LINKED_UPC WHERE PRIMARY_UPC IN "+uPCs+";";
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         int count=0;
         String PRIMARY_UPC [] = new String [100];
         String LINKED_UPC [] =new String [100];
         String EFFECTIVE_DATE [] =new String [100];
       
         while(rs.next())
         {
        	 PRIMARY_UPC [count]=rs.getString("PRIMARY_UPC");
//            LOGGER.debug("the attribute name is : "+PRIMARY_UPC [count]);
            LINKED_UPC [count]=rs.getString("LINKED_UPC");
//            LOGGER.debug("The value is : "+LINKED_UPC [count]); 
            EFFECTIVE_DATE [count]=rs.getString("EFFECTIVE_DATE");
//            LOGGER.debug("The value is : "+EFFECTIVE_DATE [count]); 
            count++;                     
         }
         
       if(count>0){flag=false;
       LOGGER.debug("Data is available for the queried primary UPC in the H2 database");
       Reporter.log("Data is available for the queried primary UPC in the H2 database");}
       else{flag=true;
       LOGGER.debug("There is no data available in the H2 database for the quried primary UPC as expected");
       Reporter.log("There is no data available in the H2 database for the quried primary UPC as expected");}
         con.close();
         return flag;
    }

    public Object[] h2ConnectPartial(String uPC,String uRL) throws SQLException{
    	String validUrl;
    	String UPC_query=uPC.replace("(", "");
    	UPC_query=UPC_query.replace("'", "");
    	UPC_query=UPC_query.replace(")", "");
    	String Upc[]= UPC_query.split(",");
    	List<String> validUpcsList = new ArrayList<String>();

    	Connection con = DriverManager.getConnection(url,user,password);
    	for(int i=0;i<Upc.length;i++)
    	{
   	    String query="SELECT * FROM US_WM_POS_LINKED_UPC WHERE PRIMARY_UPC ='"+Upc[i]+"';";
   	    LOGGER.debug(query);
   	    Statement stmt = con.createStatement();
        ResultSet rs=stmt.executeQuery(query);
        int count=0;
        String PRIMARY_UPC [] = new String [100];
        String LINKED_UPC [] =new String [100];
            
        while(rs.next())
        {
       	  PRIMARY_UPC [count]=rs.getString("PRIMARY_UPC");
           LINKED_UPC [count]=rs.getString("LINKED_UPC"); 
           count++;                     
        }
        if(count>0)
        {
        	validUpcsList.add(Upc[i]);
         LOGGER.debug("Data is available for the given primary UPC "+Upc[i]+" in the H2 database");
        Reporter.log("Data is available for the given primary UPC "+Upc[i]+" in the H2 database");
        }
        else
        {
//        InValidUpcs_list.add(Upc[i]);
        LOGGER.debug("There is no data available in the H2 database for the  primary UPC "+Upc[i]);
        Reporter.log("There is no data available in the H2 database for the  primary UPC "+Upc[i]);
        }
    	}
    	//changing the UPCs dataprovider value with the valid UPC alone
    	String validUPC=StringUtils.join(validUpcsList, "','");
    	validUPC="('"+validUPC;
    	validUPC=validUPC+"')";
       //Changing the Url with valid upcs alone
//    	String PrimaryUpc_Url[] = new String [array_count];
		String PrimaryUpc_Url1[]=uRL.split("upc/");
		if(uRL.contains("?"))
		{
			String PrimaryUpc_Url2[]=PrimaryUpc_Url1[1].split("\\?");
			validUrl=PrimaryUpc_Url1[0]+"upc/"+StringUtils.join(validUpcsList, ",")+"?"+PrimaryUpc_Url2[1];
			
		}
		else
		{
			validUrl=PrimaryUpc_Url1[0]+"upc/"+StringUtils.join(validUpcsList, ",");
		}
		
    	
    return new Object[]{validUPC,validUrl};	
    }
    
}
