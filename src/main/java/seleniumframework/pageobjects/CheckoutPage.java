package seleniumframework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import seleniumframework.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

  private final WebDriver driver;

  // Constructor to initialize the WebDriver and page elements
  public CheckoutPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  // Web elements on the checkout page
  @FindBy(css = ".action__submit")
  private WebElement submit;

  @FindBy(css = "[placeholder='Select Country']")
  private WebElement country;

  @FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
  private WebElement selectCountry;

  // Method to select a country from the dropdown
  public void selectCountry(String countryName) {
    Actions actions = new Actions(driver);
    actions.sendKeys(country, countryName).build().perform();  // Type the country name into the input field
    waitForElementToAppear(By.cssSelector(".ta-results"));     // Wait for the results to appear
    selectCountry.click();                                     // Click the country from the dropdown
  }

  // Submit the order and navigate to the confirmation page
  public ConfirmationPage submitOrder() {
    waitForWebElementToAppear(submit); // Ensure the submit button is visible before clicking
    submit.click();                    // Click the submit button to place the order
    return new ConfirmationPage(driver); // Return the confirmation page instance
  }
}
