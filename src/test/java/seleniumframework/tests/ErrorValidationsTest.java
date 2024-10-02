package seleniumframework.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumframework.TestComponents.BaseTest;
import seleniumframework.TestComponents.Retry;
import seleniumframework.pageobjects.CartPage;
import seleniumframework.pageobjects.ProductCatalogue;

/**
 * Test class for validating error messages in the application.
 */
public class ErrorValidationsTest extends BaseTest {

    /**
     * Test for validating login error messages.
     */
    @Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void loginErrorValidation() throws IOException, InterruptedException {
        // Attempt to log in with incorrect credentials
        landingPage.loginApplication("anshika@gmail.com", "Iamki000");

        // Assert that the correct error message is displayed
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
    }

    /**
     * Test for validating product error messages when adding to the cart.
     */
    @Test
    public void productErrorValidation() throws IOException, InterruptedException {
        String productName = "ZARA COAT 3";

        // Log in to the application
        ProductCatalogue productCatalogue = landingPage.loginApplication("rahulshetty@gmail.com", "Iamking@000");
        
        // Add product to the cart
        productCatalogue.addProductToCart(productName);
        
        // Navigate to the cart page
        CartPage cartPage = productCatalogue.goToCartPage();

        // Verify that a non-existent product is not displayed in the cart
        boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
        Assert.assertFalse(match);
    }
}
