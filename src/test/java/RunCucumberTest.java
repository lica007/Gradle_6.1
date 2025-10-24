import io.cucumber.java.After;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import page.PersonalAccountPage;

import static data.DataHelper.getFirstCardInfo;
import static data.DataHelper.getSecondCardInfo;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "summary"},
        features = {"src/test/resources/features"},
        glue = {"steps"})
public class RunCucumberTest {
}