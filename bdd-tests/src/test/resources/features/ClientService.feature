Feature: 2.1 Client records can be added and updated

  Scenario: 5.2 A request with no token will be given an appropriate error response
    When I ping the Client service with no Auth token
    Then I will get a no token error response from the client service

  Scenario: 5.1/2 Services will provide their Up status if a valid auth token is provided
    When I ping the Client service with an Auth token
    Then I will get response with the service Up status

  Scenario: 2.1.2 Creating a client with mandatory data will be given a success response
    When I add a client to MarketX
    Then I will be informed the client is created in MarketX

  Scenario: 2.1.2 Creating a client without mandatory data will be given an error response
    When I add a client to MarketX with missing mandatory data
    Then I will be informed the client was not created

  Scenario: 2.1.3 A client record can be updated with additional data
    Given I have an existing client record
    When I update the client record
    Then I will be informed the client is updated