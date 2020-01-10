package com.example.goaldigger;
import java.util.Date;

public class Goal {
    private Date startDate; //when goal starts
    private int numWeek; //what week it is
    private final double PRICE; //
    private final double INIT_AVG;
    private double remMoney;
    private String itemName;
    private int numWeeksRem;
    private Date firstDayofWeek;
    private Week currWeek;

    public Goal(String itemName, int numWeeks, double price, double moneySaved, double avgSpending) {
        this.itemName = itemName;
        this.numWeeksRem = numWeeks;
        this.startDate = new Date();
        this.PRICE = price;
        this.remMoney = PRICE - moneySaved;
        this.INIT_AVG = avgSpending;
    }

    public double getPercentSaved() {
        return (PRICE - remMoney) / PRICE;
    }

    public double calcWeeklyGoal() {
        //calc weekly savings
        double weeklySavings = INIT_AVG - currWeek.getMoneySpent() > 0 ? INIT_AVG - currWeek.getMoneySpent() : 0;
        remMoney -= weeklySavings;
        double remWeeklyGoal = remMoney / numWeeksRem;
        currWeek.setSpendingGoal(remWeeklyGoal);
        return remWeeklyGoal;
    }

    public boolean goalReached() {
        return remMoney <= 0;
    }
}
