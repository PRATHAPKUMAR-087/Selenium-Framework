package seleniumframework.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import seleniumframework.TestComponents.BaseTest;
import seleniumframework.pageobjects.CartPage;
import seleniumframework.pageobjects.ProductCatalogue;

/**
 * Test class for deleting products from the shopping cart.
 */
public class DeleteProductsFromCart extends BaseTest {

    @Test
    public void deleteProducts() throws InterruptedException {
        // Log in to the application
        ProductCatalogue productCatalogue = landingPage.loginApplication("anshika@gmail.com", "Iamking@000");
        
        // List of products to be added to the cart
        List<String> productList = Arrays.asList("ZARA COAT 3", "ADIDAS ORIGINAL", "IPHONE 13 PRO");
        
        // Add each product to the cart
        productList.forEach(product -> {
            try {
                productCatalogue.addProductToCart(product);
            } catch (InterruptedException e) {
                // Log the exception
                e.printStackTrace();
            }
        });
        
        // Go to the cart
        productCatalogue.goToCartPage();
        Thread.sleep(4000); // Wait for the cart to load
        
        // Delete all items from the cart
        CartPage cartPage = new CartPage(driver);
        cartPage.deleteAllItems();

        // Verify if the cart is empty
        Assert.assertTrue(cartPage.isCartEmpty(), "No Products in Your Cart!");
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) { // Ensure driver is not null before quitting
            driver.quit();
        }
    }
}
