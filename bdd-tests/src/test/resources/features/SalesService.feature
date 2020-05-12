Feature: 4. Sales service will allow client to purchase and view summaries of purchases

  Scenario: 5.2 A request with no token will be given an appropriate error response
    When I ping the Sale service with no Auth token
    Then I will get a no token error response from the sale service

  Scenario: 5.1/2 Services will provide their Up status if a valid auth token is provided
    When I ping the Sale service with an Auth token
    Then I will get response with the sale service Up status

  Scenario: 4.1/3 A client can make a purchase and a summary of the purchase cost and relevant amounts will be shown
    Given I have a Sales intent
    When I make a Sales
    Then I will be informed my Sales purchase is successful
    And I will see a summary of my Sales purchase

  Scenario: 4.2 A purchase can contain 1 or more products within a market
    Given I have a Sales intent with no products
    When I make a Sales
    Then I will be informed my Sales purchase is unsuccessful
