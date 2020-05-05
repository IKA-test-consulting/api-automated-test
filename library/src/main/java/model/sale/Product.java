package model.sale;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class Product {
    private String name;
    private String description;
    private double baseCost;
    private double percentVAT;
    private double discountAmount;
    private int amount;


    public Product getByName(String productName) {
        name = productName;
        description = "A delectable product of awesome quality";
        baseCost = 10.99;
        percentVAT = 0.21;
        discountAmount = 1.00;
        amount = 1;
        return this;
    }
}

