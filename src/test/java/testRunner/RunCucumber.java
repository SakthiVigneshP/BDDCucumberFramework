package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(glue = "stepDefinitions",features="feature",
tags= "@loginApp",
plugin= {"pretty","html:src\\test\\resources\\rep1.html",
		"json:src\\test\\resources\\rep2.json"
		,"junit:src\\test\\resources\\rep3.xml"
		},monochrome=true)

public class RunCucumber {

}
