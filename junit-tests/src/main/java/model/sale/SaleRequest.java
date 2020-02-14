package model.sale;

import model.client.Client;

import java.util.List;

@SuppressWarnings({"unused"})
public class SaleRequest {
    String businessId;
    Client client;
    List<Product> products;
    List<String> coupons;

    public SaleRequest market(String businessId) {
        this.businessId = businessId;
        return this;
    }

    public SaleRequest client(Client client) {
        this.client = client;
        return this;
    }

    public SaleRequest products(List<Product> products) {
        this.products = products;
        return this;
    }

    public SaleRequest coupons(List<String> coupons) {
        this.coupons = coupons;
        return this;
    }
/*
    {
  "sale-request": {
    "businessId": "MarketX",
    "client": {
      "foreName": "John",
      "surName": "Doe",
      "identificationNumber": "ID-123",
      "identificationType": "Passport",
      "clientExternalId": "E18-123",
      "clientId": "I18-123"
    },
    "products": [
      {
        "name": "Product 1",
        "description": "A delectable product of awesome quality",
        "baseCost": 10.99,
        "percentVAT": 0.21,
        "discountCost": 1.00,
        "amount": 1
      }
    ],
    "coupons": [
      "SALE10",
      "GPS-121"
    ]
  }
}
     */
}
