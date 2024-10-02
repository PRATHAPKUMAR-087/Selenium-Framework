Feature: purchanse order from ecommerce site  

Background:Given I am on the home page
@tag2  
Scenario Outline: Positive scenario of submitting the order
 
Given I login with "<user>" and "<pwd>"  
When I add the "<product>" in cart    
And checkout "<product>" and submit order    
Then I verify the "THANKYOU FOR THE ORDER"
 
Examples: 
      | user                         | pwd     | product     |
      | pallavi.jadhav2008@gmail.com |Study@24 | ZARA COAT 3 |   