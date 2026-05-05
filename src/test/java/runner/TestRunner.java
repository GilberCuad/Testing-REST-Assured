import org.junit.platform.suite.api.*;
import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "blass.academy.steps")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@smoke")

public class TestRunner {
}
