package br.com.invillia.projetoPaloAlto;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = "br.com.invillia.projetoPaloAlto.integration",
        monochrome = true,
        plugin = {
                "pretty",
                "html:target/cucumber",
        }
)
public class CucumberRunner {
}
