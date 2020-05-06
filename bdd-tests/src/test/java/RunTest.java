import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
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
