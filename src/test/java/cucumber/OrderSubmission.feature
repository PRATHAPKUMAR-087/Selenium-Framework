

Feature: Purchase the order from Ecommerce Website
  I want to use this template for my feature file

  Background:
    Given I landed on Ecommerce Page  
  @tag2
  Scenario Outline: Test of Submitting the order
  
    When I logged with the username "<name>" and "<password>"
    When I add the product "<productname>" to cart
    And I checkout "<productname>" and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on Confirmation Page

    Examples:
      | name                       | password    | productname |
      | usamanazeermalik@gmail.com | Malik@e009  | ZARA COAT 3 |