
package jaskowski.vendingMachine;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(format={"html:target/cucumber-html-report", "json-pretty:target/cucumber-report.json"})
public class VendingMachineIntegrationCucumberTests {
	
}
