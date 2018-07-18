package com.sams.cucutests;

import com.sams.tests.BaseTest;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.*;

/**
 * Created by u0c000g on 7/20/2017.
 */

@CucumberOptions(format = "json:target/cucumber-report-feature-composite.json",
        features = {"src/test/java/com/sams/cucufeatures/FactoryIdPrivateLabel.feature"}, plugin =
        "json:target/cucumber2" +
                ".json")
@Listeners({com.sams.extentreports.listners.ExtentTestNGITestListener.class})
public class NoActiveFactorysForVendorTest extends BaseTest{

    private TestNGCucumberRunner cukeRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        cukeRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        cukeRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features() {
        return cukeRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        cukeRunner.finish();
    }
}
