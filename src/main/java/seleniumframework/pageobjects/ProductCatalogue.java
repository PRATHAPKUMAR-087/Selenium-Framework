package seleniumframework.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import seleniumframework.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

  private final WebDriver driver;

  @FindBy(css = ".mb-3")
  private List<WebElement> products;

  @FindBy(css = ".ng-animating")
  private WebElement spinner;

  private final By productsBy = By.cssSelector(".mb-3");
  private final By addToCart = By.cssSelector(".card-body button:last-of-type");
  private final By toastMessage = By.cssSelector("#toast-container");

  // Constructor to initialize the WebDriver and PageFactory elements
  public ProductCatalogue(WebDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  // Method to get the list of products
  public List<WebElement> getProductList() {
    waitForElementToAppear(productsBy);
    return products;
  }

  // Method to get a specific product by name
  public WebElement getProductByName(String productName) {
    return getProductList().stream()
        .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
        .findFirst()
        .orElse(null);
  }

  // Method to add a product to the cart
  public void addProductToCart(String productName) throws InterruptedException {
    WebElement prod = getProductByName(productName);
    prod.findElement(addToCart).click();
    waitForElementToAppear(toastMessage);
    waitForElementToDisappear(spinner);
  }
}
