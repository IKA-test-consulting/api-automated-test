# Requirements
## 1. Auth
1) Requests with valid user credentials will be given a token
1) Requests with invalid user credentials will be given an appropriate error response

## 2.1 Clients
1) Creating a client with mandatory data will be given a success response
1) A client record with insufficient data will be given an appropriate error response
1) A client record can be updated with additional data

### 2.2 Market X Client
1) Client does not have additional mandatory fields

### 2.3 Market Y Client
1) Client mandatory fields also include identification details
 
## 3. Products
This is currently removed from scope
1) ~~Creating a product with mandatory data will be given a success response~~
1) ~~Products without a market designation will be given an error response~~
1) ~~Each product must be unique within a market~~
1) ~~A product can be deleted~~
1) ~~Only a product's price and quantity details can be updated~~

## 4. Sales
1) A client can make a purchase
1) A purchase can contain 1 or more products within a market
1) A successful purchase will provide a summary of the purchase cost and relevant amounts
1) ~~A purchase without a product will be given a success response~~
1) ~~A purchase without a market will be given an error response~~
1) ~~A purchase with a product with no stock will be given an error response~~

## 5. Common 
1) All requests to services must use a token retrieved from the Auth service
1) A request with no token will be given an appropriate error response
1) All services will provide their 'UP' status

