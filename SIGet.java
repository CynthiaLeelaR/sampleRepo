package com.cynthia.services;

import com.cynthia.base.MainBase;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

/**
 * Created by u0c000g on 11/22/2017.
 */
public class SupplyItemGet extends MainBase {

    Response response;
    String url = null;

    //returns response of get supply items by item number service
    public Response searchWithItemNumber(String itemNumber, String division, String market){
        url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/" + itemNumber;
        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetAcceptKey8"));
        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }

    public Response searchWithMultipleItemNumbers(String itemNumbers, String division, String market, String specialCondition){
        url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/" + "supplyItems";
        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetAcceptKey8"));

        //Check for no item header scenario
        if(specialCondition.contains("No Item Numbers Header")){
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }

        //cleaning up the item numbers string for any spaces
        requestSpecification = addItemOrGtinNosHeaderToSpec("itemNumbers",itemNumbers,requestSpecification);

        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }
    public Response searchWithMultipleAssortments(String itemNumbers, String division, String market, String specialCondition){
        url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/" + "assortmentShipper";
        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetOrderType"));

        //Check for no item header scenario
        if(specialCondition.contains("No Item Numbers Header")){
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }

        //cleaning up the item numbers string for any spaces
        requestSpecification = addItemOrGtinNosHeaderToSpec("itemNumbers",itemNumbers,requestSpecification);

        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }
    
    public Response searchWithMultipleAssortmentswithStoreNo(String itemNumbers,String Storereq,String StoreNo, String division, String market, String specialCondition){
        url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/" + "assortmentShipper";
        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("Accept_Normal"));

        //Check for no item header scenario
        if(specialCondition.contains("No Item Numbers Header")){
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }

        //cleaning up the item numbers string for any spaces
        requestSpecification = addmoreItemOrGtinNosHeaderToSpec("itemNumbers",itemNumbers,"storeRetailRequired",Storereq,"storeNbr",StoreNo,requestSpecification);
       
        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }
    
    public Response searchWithSupplierAndDept(String suppliernumber,String deptNo, String division, String market){
        url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/" + "supplierItems";
        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        
        //cleaning up the item numbers string for any spaces
        requestSpecification = twoHeaderToSpec("supplierNumber",suppliernumber,"deptNbr",deptNo,requestSpecification);
       
        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }


    public Response searchWithDiffTypeUpc(String upc, String division, String market, String upcType, String descRequired){

        if(descRequired.equalsIgnoreCase("") && !upcType.equalsIgnoreCase("")){
            url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/upc/" + upc.trim()
                    + "?type=" + upcType.trim();
        } else if(descRequired.equalsIgnoreCase("") && upcType.equalsIgnoreCase("")) {
            url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/upc/" + upc.trim();

        } else if(!descRequired.equalsIgnoreCase("") && !upcType.equalsIgnoreCase("")){
            url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/upc/" + upc.trim()
                    + "?type=" + upcType.trim() + "&descRequired=" + descRequired;
        } else if (!descRequired.equalsIgnoreCase("") && upcType.equalsIgnoreCase("")){
            url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/upc/" + upc.trim()
                    + "?descRequired=" + descRequired;
        }

        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetWtihInfoAcceptKey"));

        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }

    public Response searchWithSupplierStockId(String market, String division, String supplierStockId, String descriptionReqd, String deptNumber, String specialCondition) {

        String initialurl = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/supplierStockId";

        if (!descriptionReqd.equalsIgnoreCase("")) {
            initialurl = initialurl + "?descRequired=" + descriptionReqd;
            if (!deptNumber.equalsIgnoreCase("")) {
                initialurl = initialurl + "&deptNbr=" + deptNumber;
            } else {
                if (!deptNumber.equalsIgnoreCase("")) {
                    initialurl = initialurl + "?deptNbr=" + deptNumber;
                }
            }
        }

        RequestSpecification requestSpecification = setHeaders(domain, initialurl, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetWtihInfoAcceptKey"));
        if(!supplierStockId.isEmpty()){
            requestSpecification.headers("supplierStockId",supplierStockId.trim());
        }
        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(initialurl);

    }

    public Response searchWithMultipleUpcNbrsAndSuplierNbrWithDesc(String upcNbrs, String supplierNbr, String descRequired, String market, String division, String specialCondition){
        url = "/mis-legacy/rest/supply-item/" + market.toUpperCase().trim() + "/" + division.toUpperCase().trim() + "/upcNbrsAndSupplierNbr?descRequired=" + descRequired.trim();
        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetWtihInfoAcceptKey"));

        //Check for no upcNbrs and supplierNbr header scenario
        if(specialCondition.contains("No Upc Numbers Header") && specialCondition.contains("No Supplier Number Header")){
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }

        //Check for no supplierNbr header scenario
        if(specialCondition.contains("No Supplier Number Header")){
            requestSpecification = addItemOrGtinNosHeaderToSpec("upcNumbers",upcNbrs,requestSpecification);
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }

        //Check for no upcNbr header scenario
        if(specialCondition.contains("No Upc Numbers Header")){
            requestSpecification.headers("supplierNumber", supplierNbr.trim());
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }
        //adding upcNbrs and supplierNbr headers
        requestSpecification = addItemOrGtinNosHeaderToSpec("upcNumbers",upcNbrs,requestSpecification);
        requestSpecification = addItemOrGtinNosHeaderToSpec("supplierNumber", supplierNbr, requestSpecification);

        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }

    //as request headers are different between wm and sams division, having two different service cals for the same service
    //and division hard coded accoordingly
    public Response searchWithMultipleItemNbrsAndStoreNbrsWM(String itemNbrs, String storeNbr, String market, String specialCondition){
        url = "/mis-legacy/rest/supply-item/" + market.toLowerCase().trim() + "/wm/supplyItems";
        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetAcceptKey8"));

        //Check for no upcNbrs and supplierNbr header scenario
        if(specialCondition.contains("No Item Numbers Header") && specialCondition.contains("No Store Number Header")){
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }

        //Check for no supplierNbr header scenario
        if(specialCondition.contains("No Item Numbers Header")){
            requestSpecification = addItemOrGtinNosHeaderToSpec("storeNbr",storeNbr,requestSpecification);
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }
        //adding upcNbrs and supplierNbr headers
        requestSpecification = addItemOrGtinNosHeaderToSpec("itemNumbers",itemNbrs,requestSpecification);
        requestSpecification = addItemOrGtinNosHeaderToSpec("storeNbr", storeNbr, requestSpecification);

        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }

    public Response searchWithMultipleItemNbrsAndStoreNbrsSams(String itemNbrs, String clubNbrs, String market, String specialCondition){
        url = "/mis-legacy/rest/supply-item/" + market.toLowerCase().trim() + "/sams/supplyItems";
        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetAcceptKey8"));

        //Check for no upcNbrs and supplierNbr header scenario
        if(specialCondition.contains("No Item Numbers Header") && specialCondition.contains("No Store Number Header")){
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }

        //Check for no supplierNbr header scenario
        if(specialCondition.contains("No Item Numbers Header")){
            requestSpecification = addItemOrGtinNosHeaderToSpec("clubNumbers",clubNbrs,requestSpecification);
            return sendReqWithNoItemOrGtinNbrHeader(requestSpecification, url);
        }
        //adding upcNbrs and supplierNbr headers
        requestSpecification = addItemOrGtinNosHeaderToSpec("itemNumbers",itemNbrs,requestSpecification);
        requestSpecification = addItemOrGtinNosHeaderToSpec("clubNumbers", clubNbrs, requestSpecification);

        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }

    //get supply item with gtin and business unit
    public Response searchWithGtinAndBu(String gtin, String bu, String division, String market, String specialCondition){
        url = "/mis-legacy/rest/supply-item/" + market.toLowerCase().trim() + "/" + division.toLowerCase().trim()
                + "/gtin/" + gtin + "/businessUnit/" + bu.toLowerCase().trim();

        RequestSpecification requestSpecification = setHeaders(domain, url, "GET");
        requestSpecification.accept(prop.getProperty("SupplyItemGetAcceptKey8"));

        return response = given()
                .filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(requestSpecification)
                .when()
                .get(url);
    }


}