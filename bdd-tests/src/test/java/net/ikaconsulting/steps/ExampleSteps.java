package net.ikaconsulting.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleSteps {
    private int number1;
    private int number2;
    private int result;

    @Given("I have numbers {int} and {int}")
    public void iHaveNumbersXAndY(int x, int y) {
        number1 = x;
        number2 = y;
    }

    @When("I add them")
    public void iAddThem() {
        result = number1 + number2;
    }

    @When("I minus them")
    public void iMinusThem() {
        result = number1 - number2;
    }

    @Then("I expect the result to be {int}")
    public void iExpectTheResultToBeZ(int z) {
        assertEquals(z, result, "The mathematical action result should match");
    }
}
