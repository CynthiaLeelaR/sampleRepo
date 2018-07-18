package com.walmart.pageObj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.walmart.base.WalmartBaseTest;
/**
 * 
 * @author cylee1
 * @date 1 Sept
 * @modified 22 Sept
 *
 */

public class CassandraConnect extends WalmartBaseTest {
	private static final Logger LOGGER = Logger.getLogger(CassandraConnect.class);
	
	public static String urlCert="jdbc:cassandra://tstr400095:9160/system_auth";
    public static String userCert = "readonly";
    public static String passwordCert = "readonly";
	public static String urlQa="jdbc:cassandra://oser400475:9160/system_auth";
    public static String userQa = "readonly";
    public static String passwordQa = "readonly";
    public static Connection con = null;

        
 
    public String cassandraGTINValidation(String GTIN) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
   
    {
//    		String GTIN ="00210365232149";
    	 String Compare_Value=null;
    	 if((prop.getProperty("ENVIRONMENT")).contentEquals("cert"))
			{ 
    		 con = DriverManager.getConnection(urlCert,userCert,passwordCert);
			}
    	 else if((prop.getProperty("ENVIRONMENT")).contentEquals("qa"))
			{
    		 con = DriverManager.getConnection(urlQa,userQa,passwordQa);
			}
    	 String query="Select * from \"Catalog\".\"node_instances3\" where id ='"+GTIN+"'";
    	 LOGGER.debug(query);
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         int count=0;
         String Cassendra_id [] = new String [200];
         String Cassendra_Attributes [] = new String [200];
         String Cassendra_Value[] =new String [200];
         
         while(rs.next())
         {
        	Cassendra_id [count]=rs.getString(1);
            Cassendra_Attributes [count]=rs.getString(3);
            Cassendra_Value [count]=rs.getString("value");
            count++;                     
         }
         Compare_Value= Cassendra_id [1];
         LOGGER.debug("Id from Cassandra is : " + Compare_Value + ". And records are found for this GTIN in cassandra");
         return Compare_Value;
     }
    public Object[] tiValidation(String GTIN) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {

    	int count=0;
        String Cassandra_Context [] = new String [200];
        String Cassendra_Attributes [] = new String [200];
        String Cassendra_Value [] =new String [200];
        if((prop.getProperty("ENVIRONMENT")).contentEquals("cert"))
		{ 
		 con = DriverManager.getConnection(urlCert,userCert,passwordCert);
		}
	 else if((prop.getProperty("ENVIRONMENT")).contentEquals("qa"))
		{
		 con = DriverManager.getConnection(urlQa,userQa,passwordQa);
		}
    	 String query="Select * from \"Catalog\".\"node_instances3\" where id ='"+GTIN+"'";
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         while(rs.next())
         {
        	Cassandra_Context[count]=rs.getString("context");
            Cassendra_Attributes [count]=rs.getString("attribute_id");
            Cassendra_Value [count]=rs.getString("value");
            count++;                     
         }
         int a =0;
         String consumableGtinfromCassandra[] = new String[200];
         String contextfromCassandra[] = new String[20];
         for (int i = 0; i < count; i++) {
 			if (Arrays.asList(Cassendra_Attributes[i]).contains("consumerGtin")) 
 			{
// 				Compare_Value1[a] = Cassendra_Value[i];
// 				Compare_Value2[a]=Cassandra_Context[i];
 				consumableGtinfromCassandra[a] = Cassendra_Value[i];
 				consumableGtinfromCassandra[a] = consumableGtinfromCassandra[a].replace("]", "");
 				consumableGtinfromCassandra[a] = consumableGtinfromCassandra[a].replace("[", "");
 				contextfromCassandra[a] = Cassandra_Context[i];
 				contextfromCassandra[a] = contextfromCassandra[a].replace("]", "");
 				contextfromCassandra[a] = contextfromCassandra[a].replace("[", "");
 				LOGGER.debug("consumable Gtin from Cassandra is : " + consumableGtinfromCassandra[a]);
 				LOGGER.debug("The context of the queried id is : "+contextfromCassandra[a]);
 				a++;
 				con.close();
 			}
         }
         return new Object[]{consumableGtinfromCassandra,contextfromCassandra};
     }
    
    public boolean tiValidationNegative(String GTIN) throws SQLException 
    {
    	int count=0;
    	boolean flag=false;
        String Cassendra_Attributes [] = new String [200];
        String Cassendra_Value [] =new String [200];
    	 
    	 if((prop.getProperty("ENVIRONMENT")).contentEquals("cert"))
			{ 
 		 con = DriverManager.getConnection(urlCert,userCert,passwordCert);
			}
 	 else if((prop.getProperty("ENVIRONMENT")).contentEquals("qa"))
			{
 		 con = DriverManager.getConnection(urlQa,userQa,passwordQa);
			}
    	 String query="Select * from \"Catalog\".\"node_instances3\" where id ='"+GTIN+"'";
    	 LOGGER.debug(query);
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         
  		if (rs.next()) 
  			{
  			 while(rs.next())
  	         {
  	        	
  	        	
  	            Cassendra_Attributes [count]=rs.getString("attribute_id");
  	            Cassendra_Value [count]=rs.getString("value");
  	            count++;                     
  	         }
//  			 List <String> Cassandra_Attributes=Arrays.asList(Cassendra_Attributes);
  			 for (int i = 0; i < count; i++) {
  				if(Cassendra_Value [i]!=null){ 
  			 if (!Cassendra_Attributes[i].equalsIgnoreCase("hierarchyNode")) 
  	 			{
  				 
  	 				if(Cassendra_Value [i].equalsIgnoreCase("[]")){
  	 				flag= true;}
  	 			}
  				}
  	 			
  	 			}

  			}
          else
  			{
  				LOGGER.debug("The Cassandra doesn't have data for the GTIN: "+GTIN); 	
  				con.close(); 
  				flag= true;
  				
  			}
  			return flag;
         }
    
    public boolean cassandraContent(String id) throws SQLException 
    {
    	int count=0;
    	boolean flag=false;
        String Cassendra_Attributes [] = new String [200];
        String Cassendra_Value [] =new String [200];
    	 
    	 if((prop.getProperty("ENVIRONMENT")).contentEquals("cert"))
			{ 
 		 con = DriverManager.getConnection(urlCert,userCert,passwordCert);
			}
 	 else if((prop.getProperty("ENVIRONMENT")).contentEquals("qa"))
			{
 		 con = DriverManager.getConnection(urlQa,userQa,passwordQa);
			}
    	 String query="Select * from \"Catalog\".\"node_instances3\" where id ='"+id+"'";
    	 LOGGER.debug(query);
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         
  		if (rs.next()) 
  			{
  			 while(rs.next())
  	         {
  	        	
  	        	
  	            Cassendra_Attributes [count]=rs.getString("attribute_id");
  	            Cassendra_Value [count]=rs.getString("value");
  	            count++;                     
  	         }
			
  			flag= true;
  			}
          else
  			{
  				LOGGER.debug("The Cassandra doesn't have data for the GTIN: "+id); 	
  				con.close(); 
  				flag= false;
  				
  			}
  			return flag;
         }
        
    public Object[] tiValidationWithContext(String GTIN) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {

    	int count=0;
        String Cassandra_Context [] = new String [200];
        String Cassendra_Attributes [] = new String [200];
        String Cassendra_Value [] =new String [200];
        String id[] = new String[200];
        String contextfromCassandra[] = new String[20];
        List<String> contextfromCassandraList = new ArrayList<String>();
        if((prop.getProperty("ENVIRONMENT")).contentEquals("cert"))
		{ 
		 con = DriverManager.getConnection(urlCert,userCert,passwordCert);
		}
	 else if((prop.getProperty("ENVIRONMENT")).contentEquals("qa"))
		{
		 con = DriverManager.getConnection(urlQa,userQa,passwordQa);
		}
    	 String query="Select * from \"Catalog\".\"node_instances3\" where id ='"+GTIN+"'";
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         while(rs.next())
         {
        	 id[count]=rs.getString("id");
        	Cassandra_Context[count]=rs.getString("context");
            Cassendra_Attributes [count]=rs.getString("attribute_id");
            Cassendra_Value [count]=rs.getString("value");
            count++;                     
         }
         int a =0;
         String idCass=null;
         for (int i = 0; i < count; i++) {
 			if (Arrays.asList(Cassendra_Attributes[i]).contains("isConsumableInd")&&Cassandra_Context[i]!=null) 
 			{
// 				Compare_Value1[a] = Cassendra_Value[i];
// 				Compare_Value2[a]=Cassandra_Context[i];
 				idCass = id[i];
 				idCass = idCass.replace("]", "");
 				idCass = idCass.replace("[", "");
 				contextfromCassandra[a] = Cassandra_Context[i];
 				contextfromCassandra[a] = contextfromCassandra[a].replace("]", "");
 				contextfromCassandra[a] = contextfromCassandra[a].replace("[", "");
 				LOGGER.debug("consumable Gtin from Cassandra is : "+ idCass);
 				LOGGER.debug("The context of the queried id is : "+contextfromCassandra[a]);
 				a++;
 				con.close();
 			}
 		}
         for(int k=0;k<contextfromCassandra.length;k++){
         if(contextfromCassandra[k]==null){}else{contextfromCassandraList.add(contextfromCassandra[k]);}
         }
         return new Object[]{idCass,contextfromCassandraList};
     }
   
    
    public Object[] retreiveCassandraValue(String GTIN,String Context) throws SQLException{
    	int count=0;
    	String Cassandra_Context [] = new String [200];
        String Cassendra_Attributes [] = new String [200];
        String Cassendra_Attributes1 [] = new String [200];
        String Cassendra_Value [] =new String [200];
        String Cassendra_Value1 [] =new String [200];
        List<String> CassandraValueList = new ArrayList<String>();
        List<String> CassandraAttributeList = new ArrayList<String>();
        String context_cassandra=null;
        if((prop.getProperty("ENVIRONMENT")).contentEquals("cert"))
		{ 
		 con = DriverManager.getConnection(urlCert,userCert,passwordCert);
		}
	 else if((prop.getProperty("ENVIRONMENT")).contentEquals("qa"))
		{
		 con = DriverManager.getConnection(urlQa,userQa,passwordQa);
		}
    	 String query="Select * from \"Catalog\".\"node_instances3\" where id ='"+GTIN+"'";
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         while(rs.next())
         {
        	
        	Cassandra_Context[count]=rs.getString("context");
            Cassendra_Attributes [count]=rs.getString("attribute_id");
            Cassendra_Value [count]=rs.getString("value");

            count++;                     
         }
        int a=0;       
         for (int i = 0; i < count; i++) {
        	 
        	 
        	 
        	if(Cassandra_Context[i]==null){}
        	else{
 			if (Cassandra_Context[i].equalsIgnoreCase(Context)) 
 			{
 				context_cassandra=Cassandra_Context[i];
 				Cassendra_Attributes1[a]=Cassendra_Attributes[i];
 				Cassendra_Value1[a] = Cassendra_Value[i];
 				Cassendra_Value1[a] = Cassendra_Value1[a].replace("[", "");
 				Cassendra_Value1[a] = Cassendra_Value1[a].replace("]", "");
 				a++;
 				con.close();
 			}
 						
 			
        }
 		}
         for(int k=0;k<Cassendra_Value1.length;k++){
         if(Cassendra_Value1[k]==null){}
         else{
        	 CassandraAttributeList.add(Cassendra_Attributes1[k]);
        	 CassandraValueList.add(Cassendra_Value1[k]);}
         }
         return new Object[]{CassandraAttributeList,CassandraValueList,context_cassandra} ;
       }
    
    public List<String> siCassandra(String itemnumber) throws SQLException{
    	
    if((prop.getProperty("ENVIRONMENT")).contentEquals("cert"))
		{ 
		 con = DriverManager.getConnection(urlCert,userCert,passwordCert);
		}
	 else if((prop.getProperty("ENVIRONMENT")).contentEquals("qa"))
		{
		 con = DriverManager.getConnection(urlQa,userQa,passwordQa);
		}
        int count=0;
        String Cassendra_Attributes [] = new String [200];
        String Cassendra_Value [] =new String [200];
        List<String> CassandraAtrriValueList = new ArrayList<String>();
    	 String query="Select * from \"Catalog\".\"node_instances3\" where id ='USWM"+itemnumber+"'";
    	 System.out.println(query);
    	 Statement stmt = con.createStatement();
         ResultSet rs=stmt.executeQuery(query);
         while(rs.next())
         {
        	Cassendra_Attributes [count]=rs.getString("attribute_id");         
            Cassendra_Value [count]=rs.getString("value");
            count++;                     
         }
         con.close();

         for(int k=0;k<Cassendra_Value.length;k++){
        	 
        if(Cassendra_Attributes[k]!=null)
        {
        	if(Cassendra_Attributes[k].equals("REL_CLS_auditable"))	
              {LOGGER.debug("We are not going to validate REL_CLS_auditable attribute."); }
        	else if(Cassendra_Attributes[k].equals("description"))
        	 {LOGGER.debug("We are not going to validate description attribute.");}
        	else if(Cassendra_Attributes[k].equals("seasonYearNbr"))
        	{LOGGER.debug("We are not going to validate seasonYearNbr attribute.");}
        	else if(Cassendra_Attributes[k].equals("supplyItemSecondaryDescription"))
        	{LOGGER.debug("We are not going to validate supplyItemSecondaryDescription attribute.");}
      else{	
    	  
         if(Cassendra_Value[k]==null)
         { CassandraAtrriValueList.add(Cassendra_Attributes[k]+":null") ; }
         else{
        	 if(Cassendra_Attributes[k].equals("destinationFormatCode"))
        	 {	 
        		 Cassendra_Value[k]=Cassendra_Value[k].replace("[", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replaceAll(" ","");
            	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]); }
        	 else if(Cassendra_Attributes[k].equals("factoryId"))
        	 {	 
        		 Cassendra_Value[k]=Cassendra_Value[k].replace("[", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replaceAll(" ","");
            	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]); }
        	 else if(Cassendra_Attributes[k].equals("originCountryCode"))
        	 {	 
        		 Cassendra_Value[k]=Cassendra_Value[k].replace("[", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replaceAll(" ","");
            	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]); }
        	 
        	 else if(Cassendra_Attributes[k].equals("sendStoreDate"))
        	 {  String trimtime=Cassendra_Value[k].split("T")[0];
        		 Cassendra_Value[k]=trimtime.replace("[", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
            	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]); }
        	 else if(Cassendra_Attributes[k].equals("supplyItemCreateDate"))
        	 {  String trimtime=Cassendra_Value[k].split("T")[0];
        		 Cassendra_Value[k]=trimtime.replace("[", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
            	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]); }
        	 else if(Cassendra_Attributes[k].equals("supplyItemEffectiveDate"))
        	 {  String trimtime=Cassendra_Value[k].split("T")[0];
        		 Cassendra_Value[k]=trimtime.replace("[", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
            	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]); }
        	 else if(Cassendra_Attributes[k].equals("supplyItemExpireDate"))
        	 {  String trimtime=Cassendra_Value[k].split("T")[0];
        		 Cassendra_Value[k]=trimtime.replace("[", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
            	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]); }
        	 
        	 else if(Cassendra_Attributes[k].equals("supplyItemStatusChangeDate"))
        	 {  String trimtime=Cassendra_Value[k].split("T")[0];
        		 Cassendra_Value[k]=trimtime.replace("[", "");
            	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
            	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]); }
        	 
        	 else
        	 {
        	 Cassendra_Value[k]=Cassendra_Value[k].replace("[", "");
        	 Cassendra_Value[k]=Cassendra_Value[k].replace("]", "");
        	 CassandraAtrriValueList.add(Cassendra_Attributes[k]+":"+Cassendra_Value[k]);
        	 }
          }
         }
        }
         }
         Collections.sort(CassandraAtrriValueList);
        LOGGER.debug("Cassandralist===>"+CassandraAtrriValueList);
        LOGGER.debug(CassandraAtrriValueList.size());
         return CassandraAtrriValueList ;
       }


    }
    

