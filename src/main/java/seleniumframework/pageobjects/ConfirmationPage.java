package seleniumframework.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import seleniumframework.AbstractComponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {

  // Constructor to initialize the WebDriver and page elements
  public ConfirmationPage(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
  }

  // Web element for the confirmation message
  @FindBy(css = ".hero-primary")
  private WebElement confirmationMessage;

  // Method to retrieve the confirmation message
  public String getConfirmationMessage() {
    waitForWebElementToAppear(confirmationMessage); // Wait for the confirmation message to be visible
    return confirmationMessage.getText();            // Return the text of the confirmation message
  }
}
