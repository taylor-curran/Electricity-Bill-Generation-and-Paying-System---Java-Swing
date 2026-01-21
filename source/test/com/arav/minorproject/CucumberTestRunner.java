package com.arav.minorproject;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = "com.arav.minorproject",
    plugin = {"pretty", "html:target/cucumber-report.html"}
)
public class CucumberTestRunner {
}
