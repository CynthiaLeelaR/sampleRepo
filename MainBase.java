package com.cynthia.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.util.Properties;

import io.restassured.RestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

/**
 * @author cylee1
 */
public class MainBase {

    private static final Logger LOGGER = Logger.getLogger(MainBase.class);
    InputStream input = null;
    public static Properties prop = new Properties();
    public static String env = null;
    public static String domain = null;
    public static String baseUri;

    public MainBase() {
        env = System.getProperty("env");

        if (env == null) {
//            env = "cert";
            env = "qa";
//        	 env = "prod";
        }
        else if(env.equalsIgnoreCase("stage-mis")){ env = "qa"; }
        if (domain == null) {
//            domain = "OneOps";
            domain = "apiPortal";
        }
        propertyRead();
        setBaseUri();
    }

    public void propertyRead() {
        if (env != null) {
            try {
                input = new FileInputStream("src/main/resources/" + env + ".properties");
                prop.load(input);
                input.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setBaseUri() {
        RestAssured.useRelaxedHTTPSValidation();
        if (domain != null && domain == "OneOps") {
            baseUri = prop.getProperty("OneOpsHostUrl");
            RestAssured.baseURI = baseUri;
        } else {
            baseUri = prop.getProperty("apiPortalHostUrl");
            RestAssured.baseURI = baseUri;
        }
    }

    protected static RequestSpecification setHeaders(String domain, String url, String method) {
        RequestSpecification reqBuilder = null;
        if (domain.equalsIgnoreCase("OneOps")) {
            try {
                baseUri = prop.getProperty("OneOpsHostUrl");
                reqBuilder = new RequestSpecBuilder()
                        //.addHeader("Accept", prop.getProperty("Accept_Nodeinstances"))
                        .addHeader("Authorization", AuthorizationKey.authKey(url, method))
                        .setBaseUri(baseUri)
                        .build();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            baseUri = prop.getProperty("apiPortalHostUrl");
            reqBuilder = new RequestSpecBuilder()
                    .addHeader("WMT-API-KEY", prop.getProperty("WMT-API-KEY"))
                    .setBaseUri(baseUri)
                    .build();
        }
        return reqBuilder;
    }

    public static void logNdReport(Logger logger, String message) {
        logger.debug(message);
        Reporter.log(message);
    }

    public RequestSpecification addItemOrGtinNosHeaderToSpec(String headerName, String headerValues, RequestSpecification requestSpecification) {

        String newValues = "";

        if (headerValues != null) {
            String[] tempArray = headerValues.split(",");
            for (int i = 0; i < tempArray.length; i++) {
                newValues = newValues + "," + tempArray[i].trim();
            }
            newValues = newValues.replaceFirst(",", "");
        } else {
            newValues = headerValues;
        }

        //adding cleaned item numbers to header
        RequestSpecification reqSpecBuilder = new RequestSpecBuilder()
                .addHeader(headerName, newValues)
                .build();

        return requestSpecification.spec(reqSpecBuilder);
    }
    public RequestSpecification addmoreItemOrGtinNosHeaderToSpec(String headerName, String headerValues,String headerName1, String headerValues1,String headerName2, String headerValues2, RequestSpecification requestSpecification) {

        String newValues = "";

        if (headerValues != null) {
            String[] tempArray = headerValues.split(",");
            for (int i = 0; i < tempArray.length; i++) {
                newValues = newValues + "," + tempArray[i].trim();
            }
            newValues = newValues.replaceFirst(",", "");
        } else {
            newValues = headerValues;
        }

        //adding cleaned item numbers to header
        RequestSpecification reqSpecBuilder = new RequestSpecBuilder()
                .addHeader(headerName, newValues)
                .addHeader(headerName1, headerValues1)
                .addHeader(headerName2, headerValues2)
                .build();

        return requestSpecification.spec(reqSpecBuilder);
    }
    
    public RequestSpecification twoHeaderToSpec(String headerName, String headerValues,String headerName1, String headerValues1,RequestSpecification requestSpecification) {

    //adding cleaned item numbers to header
        RequestSpecification reqSpecBuilder = new RequestSpecBuilder()
                .addHeader(headerName, headerValues)
                .addHeader(headerName1, headerValues1)
                
                .build();

        return requestSpecification.spec(reqSpecBuilder);
    }

    public Response sendReqWithNoItemOrGtinNbrHeader(RequestSpecification requestSpecification, String url) {
        return given()
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }

}