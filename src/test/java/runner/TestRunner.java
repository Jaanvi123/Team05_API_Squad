package runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = { "pretty", // Enhanced readable console logs
		        "html:target/cucumber-Reports.html", // HTML report
		        "json:target/cucumber.json", // JSON report for integrations
		        "junit:target/Cucumber.xml", // JUnit XML report
		        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", // Allure integration
		        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Extent report
				},
		monochrome=false,  //console output color
		features = {"src/test/resources/features" },
		glue= {"stepDefinitions", "hooks"},
		dryRun = false
		//tags = "@putClassDetails or @loginPost"		
		
		)

public class TestRunner extends AbstractTestNGCucumberTests{
	
	@Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
				
		return super.scenarios();
    }

}
