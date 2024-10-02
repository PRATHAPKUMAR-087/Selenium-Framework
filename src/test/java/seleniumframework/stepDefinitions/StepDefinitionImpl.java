package seleniumframework.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import seleniumframework.TestComponents.BaseTest;
import seleniumframework.pageobjects.CartPage;
import seleniumframework.pageobjects.CheckoutPage;
import seleniumframework.pageobjects.ConfirmationPage;
import seleniumframework.pageobjects.LandingPage;
import seleniumframework.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest {

  private LandingPage landingPage;
  private ProductCatalogue productCatalogue;
  private ConfirmationPage confirmationPage;

  @Given("I landed on Ecommerce Page")
  public void iLandedOnEcommercePage() throws IOException {
    landingPage = launchApplication();
    // Launch the application
  }

  @Given("Logged in with username {string} and password {string}")
  public void loggedInWithUsernameAndPassword(String username, String password) {
    productCatalogue = landingPage.loginApplication(username, password);
  }

  @When("I add product {string} to Cart")
  public void iAddProductToCart(String productName) throws InterruptedException {
    productCatalogue.addProductToCart(productName);
  }

  @When("Checkout {string} and submit the order")
  public void checkoutAndSubmitOrder(String productName) {
    CartPage cartPage = productCatalogue.goToCartPage();

    Boolean match = cartPage.verifyProductDisplay(productName);
    Assert.assertTrue(match);
    
    CheckoutPage checkoutPage = cartPage.goToCheckout();
    checkoutPage.selectCountry("india");
    confirmationPage = checkoutPage.submitOrder();
  }

  @Then("{string} message is displayed on ConfirmationPage")
  public void messageDisplayedOnConfirmationPage(String expectedMessage) {
    String confirmMessage = confirmationPage.getConfirmationMessage();
    Assert.assertTrue(confirmMessage.equalsIgnoreCase(expectedMessage));
    driver.close();
  }

  @Then("{string} message is displayed")
  public void messageDisplayed(String expectedMessage) throws Throwable {
    Assert.assertEquals(expectedMessage, landingPage.getErrorMessage());
    driver.close();
  }
}
