package seleniumframework.pageobjects;

import java.time.Duration;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumframework.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

  private final WebDriver driver;
  private final WebDriverWait wait;

  // Constructor to initialize WebDriver and WebDriverWait
  public LandingPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait
  }

  @FindBy(id = "userEmail")
  private WebElement userEmail;

  @FindBy(id = "userPassword")
  private WebElement passwordEle;

  @FindBy(id = "login")
  private WebElement submit;

  @FindBy(css = "[class*='flyInOut']")
  private WebElement errorMessage;

  @FindBy(xpath = "(//input[@name='search'])[2]")
  private WebElement search;

  @FindBy(xpath = "//*[@id='products']/div[1]/div[2]/div/div/div/h5") // Selector for the product list
  private WebElement productList;

  @FindBy(xpath = "(//button[@class='btn btn-custom'])[1]")
  private WebElement homeButton;

  // Method to log in to the application
  public ProductCatalogue loginApplication(String email, String password) {
    userEmail.sendKeys(email);
    passwordEle.sendKeys(password);
    submit.click();
    return new ProductCatalogue(driver); // Return new ProductCatalogue instance
  }

  // Method to retrieve the error message if login fails
  public String getErrorMessage() {
    waitForWebElementToAppear(errorMessage);
    return errorMessage.getText();
  }

  // Method to navigate to the landing page
  public void goTo() {
    driver.get("https://rahulshettyacademy.com/client");
  }

  // Method to search for a product
  public void searchProduct(String text) {
    waitForElementToBeClickable(search);
    search.click();
    search.clear(); // Clear the search field before entering new text
    search.sendKeys(text);
    search.sendKeys(Keys.ENTER);
    waitForSearchResults(); // Wait for search results to appear
  }

  // Overloaded method to clear the search field
  public void searchProduct() {
    waitForElementToBeClickable(search);
    search.click();
    search.clear(); 
  }

  // Wait for the product list to be visible after performing the search
  private void waitForSearchResults() {
    wait.until(ExpectedConditions.visibilityOf(productList));
  }

  // Wait for a specific element to be clickable
  private void waitForElementToBeClickable(WebElement element) {
    wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  // Method to click the Home button
  public void clickHomeLink() {
    homeButton.click();
  }
}
