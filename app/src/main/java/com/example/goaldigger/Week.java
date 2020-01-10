package com.example.goaldigger;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

//public class Week implements Parcelable {
//    List<Purchase> purchaseList;
//    double moneySpent;
//    double spendingGoal;
//    String startDate = com.example.goaldigger.Goal.getStartDate();
//
////    public Week(double moneySpent, double spendingGoal, String startDate) //ADD purchase list
////    {
////
////    }
//
//    public Week(Parcel p) {
//        this.moneySpent = 100;
//        this.spendingGoal = 500;
//        this.startDate = com.example.goaldigger.Goal.getStartDate();
//    }
//    public double calcMoneySpent() {
//        return -1;
//    }
//
//    public void setSpendingGoal(double goal) {
//        this.spendingGoal = goal;
//    }
//
//    public double getMoneySpent() {
//        return moneySpent;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.startDate);
//    }
//}

//package com.example.goaldigger;
//
//import java.util.List;

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
