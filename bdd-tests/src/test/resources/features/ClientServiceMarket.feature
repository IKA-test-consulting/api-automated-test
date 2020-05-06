Feature: Client service may have additional requirements for each market

  Scenario: 2.2.1 Client does not have additional mandatory fields for MarketX
    When I add a client to MarketX
    Then I will be informed the client is created in MarketX

  Scenario: 2.3.1 Client mandatory fields also include identification details for MarketY
    When I add a client to MarketY with identification details
    Then I will be informed the client is created in MarketY

  Scenario: 2.3.1 Client mandatory fields also include identification details for MarketY
    When I add a client to MarketY without identification details
    Then I will be informed the client was not created in MarketY