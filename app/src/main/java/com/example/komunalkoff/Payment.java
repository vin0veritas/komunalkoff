package com.example.komunalkoff;

public class Payment {
    private String serviceType;
    private double amount;
    private String date;

    public Payment(String serviceType, double amount, String date) {
        this.serviceType = serviceType;
        this.amount = amount;
        this.date = date;
    }

    public String getServiceType() {
        return serviceType;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
