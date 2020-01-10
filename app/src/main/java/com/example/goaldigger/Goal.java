package com.example.goaldigger;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Goal implements Parcelable {
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

    public Goal(Parcel p) {
        this.itemName = p.readString();
        this.numWeeksRem = p.readInt();
        this.startDate = new Date(p.readLong());
        this.PRICE = p.readDouble();
        this.remMoney = p.readDouble();
        this.INIT_AVG = p.readDouble();
    }

    public static final Creator<Goal> CREATOR = new Creator<Goal>() {
        @Override
        public Goal createFromParcel(Parcel in) {
            return new Goal(in);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };

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

    public double getPRICE() { return PRICE; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemName);
        dest.writeInt(this.numWeeksRem);
        dest.writeLong(this.startDate.getTime());
        dest.writeDouble(this.PRICE);
        dest.writeDouble(this.remMoney);
        dest.writeDouble(this.INIT_AVG);
    }
}
