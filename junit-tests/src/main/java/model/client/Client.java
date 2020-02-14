package model.client;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class Client {
    private String foreName;
    private String middleName = "Fitzgerald";
    private String surName;
    private String externalClientId;
    private String clientId;
    private String identificationNumber;
    private String identificationType;
    private String emailAddress = "test@test.123";
    private String contactNumber = "+997271773662";
    private String postalAddress1 = "PO Box 123";
    private String postalAddress2 = "";
    private String postalAddressState = "";
    private String postalAddressCity = "PostalCity";
    private String postalAddressPostCode = "A18-123";
    private String billingAddress1 = "123 Garden Vista";
    private String billingAddress2 = "Hill Street";
    private String billingAddressState = "Eden";
    private String billingAddressCity = "BillingCity";
    private String billingAddressPostCode = "E18-123";

    public Client ForeName(String foreName) {
        this.foreName = foreName;
        return this;
    }

    public Client MiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public Client SurName(String surName) {
        this.surName = surName;
        return this;
    }

    public Client ExternalClientId(String externalClientId) {
        this.externalClientId = clientId;
        return this;
    }

    public Client ClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public Client IdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    public Client IdentificationType(String identificationType) {
        this.identificationType = identificationType;
        return this;
    }

    public Client EmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public Client ContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public Client PostalAddress1(String postalAddress1) {
        this.postalAddress1 = postalAddress1;
        return this;
    }

    public Client PostalAddress2(String postalAddress2) {
        this.postalAddress2 = postalAddress2;
        return this;
    }

    public Client PostalAddressState(String postalAddressState) {
        this.postalAddressState = postalAddressState;
        return this;
    }

    public Client PostalAddressCity(String postalAddressCity) {
        this.postalAddressCity = postalAddressCity;
        return this;
    }

    public Client PostalAddressPostCode(String postalAddressPostCode) {
        this.postalAddressPostCode = postalAddressPostCode;
        return this;
    }

    public Client BillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
        return this;
    }

    public Client BillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
        return this;
    }

    public Client BillingAddressState(String billingAddressState) {
        this.billingAddressState = billingAddressState;
        return this;
    }

    public Client BillingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
        return this;
    }

    public Client BillingAddressPostCode(String billingAddressPostCode) {
        this.billingAddressPostCode = billingAddressPostCode;
        return this;
    }

    public Client withMandatory(String foreName, String surName) {
        this.foreName = foreName;
        this.surName = surName;
        return this;
    }

    public Client withIdMandatory() {
        identificationNumber = "ID-123";
        identificationType = "PASSPORT";
        return this;
    }
}
