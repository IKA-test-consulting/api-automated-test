import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
        tags = {"~@Ignore"},
        features = {"src/test/resources/features/"},
        glue = {"net.ikaconsulting"})
public class RunTest {
}
