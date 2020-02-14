package model.client;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class Preferences {
    private boolean gpdrAccepted = true;
    private boolean tncAccepted = true;
    private boolean optInEmail = false;
    private boolean optInPostal = false;
    private boolean optInSMS = false;
    private boolean optInPhone = false;

    public Preferences gpdrAccepted(boolean gpdrAccepted) {
        this.gpdrAccepted = gpdrAccepted;
        return this;
    }

    public Preferences tncAccepted(boolean tncAccepted) {
        this.tncAccepted = tncAccepted;
        return this;
    }

    public Preferences optInEmail(boolean optInEmail) {
        this.optInEmail = optInEmail;
        return this;
    }

    public Preferences optInPostal(boolean optInPostal) {
        this.optInPostal = optInPostal;
        return this;
    }

    public Preferences optInSMS(boolean optInSMS) {
        this.optInSMS = optInSMS;
        return this;
    }

    public Preferences optInPhone(boolean optInPhone) {
        this.optInPhone = optInPhone;
        return this;
    }
}
