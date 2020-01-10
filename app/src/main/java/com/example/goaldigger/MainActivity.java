package com.example.goaldigger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.goaldigger.models.FragmentUiModel;
import com.example.goaldigger.ui.main.PlaceholderFragment;
import com.example.goaldigger.ui.main.SectionsPagerAdapter;
import com.example.goaldigger.ui.main.SpendTrendsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.TransactionMedium;
import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.models.Deposit;
import com.reimaginebanking.api.nessieandroidsdk.models.Merchant;
import com.reimaginebanking.api.nessieandroidsdk.models.PostResponse;
import com.reimaginebanking.api.nessieandroidsdk.models.Purchase;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;
import android.util.Log;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private final static String CHANNEL_ID = "test";


    NessieClient client = NessieClient.getInstance("4db704c8f8b013300a4a960683da8399");
    String person1 = "5e173afc322fa016762f3794";
    String person2 = "5e173afc322fa016762f3795";
    String person3 = "5e173afb322fa016762f3796";
    String startDate = "2020-01-10"; //user input changed
    String startYear = startDate.substring(0,4);
    String startMonth = startDate.substring(5,7);
    String startDay = startDate.substring(8);
    Double thisWeekSpending = 0.0;
    Double weeklyAvg = 0.0;
    Double thisWeekSaving = weeklyAvg-thisWeekSpending;  //use for circle



    Map<String, Double> categoryCost = new HashMap<>();
    ArrayList<String> allmerchIDS = new ArrayList<>();
    private String goalName;
    private double goalCost;
    private double savingsStart;
    private int numWeeks;
    public Goal goal;
    private String merch1;
    private String merch2;
    private String merch3;

    Double proportionSaved = thisWeekSaving/goalCost;  //use for circle

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.goaldigger";

    public static String GOAL_NAME_KEY = "goalName";
    public static String GOAL_COST_KEY = "goalCost";
    public static String SAVINGS_START_KEY = "savingsStart";
    public static String NUM_WEEKS_KEY = "numWeeks";




    public NessieClient getClient() {
        return client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();





        goalName = getIntent().getStringExtra(GOAL_NAME_KEY);
        goalCost = getIntent().getDoubleExtra(GOAL_COST_KEY, 0);
        savingsStart = getIntent().getDoubleExtra(SAVINGS_START_KEY, 0);
        numWeeks = getIntent().getIntExtra(NUM_WEEKS_KEY, 0);
        //Log.d("name", goalName);
        //Log.d("cost", Double.toString(goalCost));

        final MainActivity m = this;


        client.PURCHASE.getPurchasesByAccount("5e164472322fa016762f374c",
                new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {
                        List<Purchase> purchases = (List<Purchase>) result;
                        Log.d("message", purchases.toString());
                        Double totalPurchases = 0.0;
                        Integer yearBefore = 0;
                        String strMonthBefore = "";

                        if (startMonth == "01"){
                            yearBefore = Integer.parseInt(startYear)-1; //just previous year
                            strMonthBefore = yearBefore.toString(); //previous year in String
                            String othersBefore = "12-" + startDay; //change month to string
                            strMonthBefore = strMonthBefore + '-'+ othersBefore;
                        }else{
                            //don't change year
                            yearBefore = Integer.parseInt(startMonth) - 1;
                            strMonthBefore = startYear + '-' + yearBefore + '-' + startDay;
                        }


                        Log.d("beforeDate",strMonthBefore); //this is the date we will use to compare
                        for (Purchase p: purchases){
                            allmerchIDS.add(p.getMerchantId());
                        }

                        for(String merchID: allmerchIDS) {
                            final String mID = merchID;
                            client.MERCHANT.getMerchant(mID, new NessieResultsListener() {
                                @Override
                                public void onSuccess(Object result) {
                                    Merchant merchant = (Merchant) result;
                                    List<String> categories = merchant.getCategories();
                                    //essentials
                                    if (categories.contains("health")
                                            | categories.contains("grocery_or_supermarket")
                                            | categories.contains("car_repair")) {
                                        Log.d("Essential", "This is an essential purchase.");
                                        allmerchIDS.remove(mID);
                                    } else{
                                    }

                                }

                                @Override
                                public void onFailure(NessieError error) {
                                    Log.e("Error", error.getMessage());
                                }
                            });
                        }

                        ///now let's do weekly purchases

                        goal = new Goal(goalName, numWeeks, goalCost, savingsStart, weeklyAvg);
                        startDate = goal.getStartDate(); //REFORMATE

                        Integer prevWeekDate = Integer.parseInt(startDate.substring(8,10))-7; //starting date for the current (last) week
                        String startingWeekPurchase = startDate.substring(0,8)+prevWeekDate.toString() + startDate.substring(10);
                        //startingWeekPurchase is what we compare
                        Log.d("test", startingWeekPurchase);


//                        newDateString = sdf.format(d);

                        for (Purchase p : purchases){
                            if ((p.getPurchaseDate()).compareTo("2020-01-03")>=0){
                                if (allmerchIDS.contains(p.getMerchantId())) {
                                    Log.d("test2", p.getPurchaseDate());
                                    thisWeekSpending += p.getAmount();
                                }
                            }
                            Log.d("eachpurchase", p.getPurchaseDate().toString());
                            Log.d("testpls", thisWeekSpending.toString());
                        }


                        for (Purchase p : purchases){
                            if ((p.getPurchaseDate()).compareTo(strMonthBefore)>0){
                                if (allmerchIDS.contains(p.getMerchantId())) {
                                    Log.d("time", p.getPurchaseDate());
                                    totalPurchases += p.getAmount();
                                }
                            }
                            //Log.d("eachpurchase", p.getAmount().toString());

                        }

                        //Log.d("totalpurchase", totalPurchases.toString());
                        weeklyAvg = totalPurchases/4;
                        Log.d("initial week spendavg!", weeklyAvg.toString());
//                        Log.d(purchases.get(6));
                        //System.out.print(customers.toString());
                        // do something with the list of customers here


                        //CHANGE from weekspending to weeksaving

                        //if (weeklyAvg > thisWeekSpending && thisWeekSpending > weeklyAvg-20) { //CHANGE to currently this week's spending using today's date
                        displayNotification();
                        //}

                        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
                        Gson gson = new Gson();
                        if (goalName != null) {
                            goal = new Goal(goalName, numWeeks, goalCost, savingsStart, weeklyAvg);
                            goal.setThisWeekSpending(thisWeekSpending);
                            goal.setFreqList(findTopFreqs());
                            String jsonGoal = gson.toJson(goal);
                            SharedPreferences.Editor editor = mPreferences.edit();
                            editor.putString("goal", jsonGoal);
                            editor.apply();
                        } else {
                            String goalString = mPreferences.getString("goal", "goal not found");
                            Log.d("goalString", goalString);
                            goal = gson.fromJson(goalString, Goal.class);
                            Log.d("goal price", Double.toString(goal.getPRICE()));
                        }

                        ArrayList<FragmentUiModel> fragments = new ArrayList<>();
                        fragments.add(new FragmentUiModel("Your Progress", PlaceholderFragment.newInstance()));
                        fragments.add(new FragmentUiModel("Spend Trends", SpendTrendsFragment.newInstance()));

                        ViewPager viewPager = findViewById(R.id.view_pager);
                        viewPager.setAdapter(
                                new SectionsPagerAdapter(m, getSupportFragmentManager(), fragments)
                        );
                        TabLayout tabLayout = findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(viewPager);

                        List<String> l = findTopFreqs();
                        //Log.d("freq1", l.get(0));
                        //Log.d("freq2", l.get(1));

                    }

                    @Override
                    public void onFailure(NessieError error) {
                        // handle error
                        Log.e("Error", error.getMessage());
                    }
                });

//        client.CUSTOMER.getCustomers(new NessieResultsListener() {
//            @Override
//            public void onSuccess(Object result) {
//                List<Customer> customers = (List<Customer>) result;
//                Log.d("message", customers.toString());
//                //System.out.print(customers.toString());
//                // do something with the list of customers here
//            }
//
//            @Override
//            public void onFailure(NessieError error) {
//                // handle error
//                Log.e("Error", error.getMessage());
//            }
//        });

        getClient();


//        client.PURCHASE.createPurchase("deposit_id", purchase, new NessieResultsListener() {
//            @Override
//            public void onSuccess(Object result) {
//                PostResponse<Deposit> response = (PostResponse<Deposit>) result;
//                Deposit deposit = response.getObjectCreated();
//                // do something with the newly created deposit
//            }
//        });


//        client.DEPOSIT.updatePurchase("deposit_id", deposit, new NessieTestResultsListener() {
//            @Override
//            public void onSuccess(Object result) {
//                PutDeleteResponse response = (PutDeleteResponse) result;
//            }
//        });





    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "a channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void displayNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Watch out!")
                .setContentText("You're spending too much for this week.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);
        Notification n = mBuilder.build();
        //Log.d("debug", "displayNotification: " + n.toString());
        mNotificationMgr.notify(1, n );



    }

//;
    public ArrayList<String> findTopFreqs() {
        //Log.d("allMerchIDS", allmerchIDS.toString());
        Map<String, Integer> freqMap = new HashMap<>();
        for (String s : allmerchIDS) {
            if (freqMap.containsKey(s)) {
                int count = freqMap.get(s);
                count++;
                freqMap.put(s, count);
            } else {
                freqMap.put(s, 1);
            }
        }
        //Log.d("hashmap", freqMap.toString());
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(freqMap.size(), new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> stringIntegerEntry, Map.Entry<String, Integer> t1) {
                return t1.getValue().compareTo(stringIntegerEntry.getValue()); //should sort in descending order
            }
        });
        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            pq.add(entry);
        }
        ArrayList<String> list = new ArrayList<>();
        while (!pq.isEmpty()) {
            list.add(pq.remove().getKey());
        }

        merch1 = list.get(0);
        client.MERCHANT.getMerchant(merch1, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Merchant m = (Merchant) result;
                merch1 = m.getName();
            }

            @Override
            public void onFailure(NessieError error) {
                Log.e("Error", error.getMessage());
            }
        });

        merch2 = list.get(1);
        client.MERCHANT.getMerchant(merch2, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Merchant m = (Merchant) result;
                merch2 = m.getName();
            }

            @Override
            public void onFailure(NessieError error) {
                Log.e("Error", error.getMessage());
            }
        });

        merch3 = list.get(2);
        client.MERCHANT.getMerchant(merch3, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                Merchant m = (Merchant) result;
                merch3 = m.getName();
            }

            @Override
            public void onFailure(NessieError error) {
                Log.e("Error", error.getMessage());
            }
        });

        ArrayList<String> topMerchants = new ArrayList<String>(Arrays.asList(merch1, merch2, merch3) );
        Log.d("topMerchants", topMerchants.toString());

        return topMerchants;
    }

}