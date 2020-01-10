package com.example.goaldigger;

public class Purchase {
    double amount;
    String merchant_id;
    String category;

    public Purchase(double a, String id, String c)
    {
        amount = a;
        merchant_id = id;
        category = c;
    }
    //get and set for amount
    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double n)
    {
        amount = n;
    }

    //get and set for Merchant
    public String getMerchant()
    {
        return merchant_id;
    }

    public void setMerchant(String s)
    {
        merchant_id = s;
    }

    //get and set for category
    public String getCategory()
    {
        return category;
    }

    public void setCategory(String s)
    {
        category = s;
    }
}
