package com.example.goaldigger;

import java.util.List;

public class Week {
    List<Purchase> purchaseList;
    double moneySpent;
    double spendingGoal;

    public double calcMoneySpent() {
        return -1;
    }

    public void setSpendingGoal(double goal) {
        this.spendingGoal = goal;
    }

    public double getMoneySpent() {
        return moneySpent;
    }
}
