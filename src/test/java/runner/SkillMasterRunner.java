package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@SuppressWarnings("deprecation")
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/Feature/SkillMasterModule.feature",
    glue = {"stepDefinitions", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/skillmaster-report.html",
        "json:target/cucumber-reports/skillmaster-report.json",
        "junit:target/cucumber-reports/skillmaster-report.xml"
    },
    monochrome = true,
    tags = "@skillMaster",
    dryRun = false
)
public class SkillMasterRunner {
}
	