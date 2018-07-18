package com.sams.cucutests;

import com.sams.pojo.StageItem;
import com.sams.tests.BaseTest;
import com.sams.utils.StageItemGsonBuilder;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

/**
 * Created by u0c000g on 7/20/2017.
 */
public class NoActiveFactoryStepDefs extends BaseTest{

    Response response;
    StageItemGsonBuilder builder = new StageItemGsonBuilder();
    String errorMessage = "A value must be provided.";

    @When("^private label yes, and no factory id is sent while creating stage item$")
    public void private_label_yes_and_no_factory_id_is_sent_while_creating_stage_item() throws Throwable {
        response = given().filter(new RequestLoggingFilter()).filter(new ResponseLoggingFilter())
                .spec(setRequestHeaders("samssss", "20"))
                .body(readFile("noFactoryIdErrorInput.txt"))
                .when().post("sams-item-catalog/rest/item");
    }

    @Then("^valid error message should be sent for factory id$")
    public void valid_error_message_should_be_sent_for_factory_id() throws Throwable {
        String expectedErrorMsg = "Factory ID is required for all Private Label items. Please enter a valid Factory " +
                "ID that has been assigned to your Supplier number by the Ethical Standards team. Ethicalstnds@wal-mart.com.";
        response.then().assertThat().statusCode(400);
        StageItem stageItem = builder.getPreItemObject(response.asString());
        int noOfAttributes = stageItem.getPayload().getAttributes().getAttributeList().size();
        for(int i=0; i<noOfAttributes; i++){
            if(stageItem.getPayload().getAttributes().getAttributeList().get(i).getKey().equalsIgnoreCase("factoryIds")){
                String actualErrorMsg = stageItem.getPayload().getAttributes().getAttributeList().get(i).getErrors()
                        .get(0);
                System.out.println("error message received is: " + actualErrorMsg);
                Assert.assertTrue(actualErrorMsg.equalsIgnoreCase(expectedErrorMsg));
            }
        }
    }
}
