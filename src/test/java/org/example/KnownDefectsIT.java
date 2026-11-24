package org.example;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

// Suite de diagnostico: no forma parte de las 20 pruebas de aceptacion.
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/known-defects", glue = "org.example")
public class KnownDefectsIT { }
