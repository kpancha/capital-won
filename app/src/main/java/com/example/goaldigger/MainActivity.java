package com.example.goaldigger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.goaldigger.models.FragmentUiModel;
import com.example.goaldigger.ui.main.PlaceholderFragment;
import com.example.goaldigger.ui.main.SectionsPagerAdapter;
import com.example.goaldigger.ui.main.SpendTrendsFragment;
import com.google.android.material.tabs.TabLayout;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.TransactionMedium;
import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.models.Deposit;
import com.reimaginebanking.api.nessieandroidsdk.models.PostResponse;
import com.reimaginebanking.api.nessieandroidsdk.models.Purchase;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;
import android.util.Log;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    NessieClient client = NessieClient.getInstance("4db704c8f8b013300a4a960683da8399");
    String person1 = "5e173afc322fa016762f3794";
    String person2 = "5e173afc322fa016762f3795";
    String person3 = "5e173afb322fa016762f3796";
    String startDate = "2020-01-10"; //CHANGE ONCE RECEIVE USER INPUT
    String startYear = startDate.substring(0,4);
    String startMonth = startDate.substring(5,7);
    String startDay = startDate.substring(8);
    Double weeklyAvg = 0.0;
    private String goalName;
    private double goalCost;
    private double savingsStart;

    public static String GOAL_NAME_KEY = "goalName";
    public static String GOAL_COST_KEY = "goalCost";
    public static String SAVINGS_START_KEY = "savingsStart";

    public NessieClient getClient() {
        return client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goalName = getIntent().getStringExtra(GOAL_NAME_KEY);
        goalCost = getIntent().getDoubleExtra(GOAL_COST_KEY, 0);
        savingsStart = getIntent().getDoubleExtra(SAVINGS_START_KEY, 0);


        client.PURCHASE.getPurchasesByAccount("5e164472322fa016762f374c",
                new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {
                        List<Purchase> purchases = (List<Purchase>) result;
                        Log.d("message", purchases.toString());
                        Double totalPurchases = 0.0;
                        Integer yearBefore = 0;
                        String strMonthBefore = "";

                        if (startDate.charAt(6) == '1'){
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


                        for (Purchase p : purchases){
                            if ((p.getPurchaseDate()).compareTo(strMonthBefore)>0){
                                Log.d("time", p.getPurchaseDate());
                                totalPurchases += p.getAmount();
                            }

                            //Log.d("eachpurchase", p.getAmount().toString());

                        }

                        Log.d("totalpurchase", totalPurchases.toString());
                        weeklyAvg = totalPurchases/4;
                        Log.d("initial week spendavg!", weeklyAvg.toString());
//                        Log.d(purchases.get(6));
                        //System.out.print(customers.toString());
                        // do something with the list of customers here
                    }

                    @Override
                    public void onFailure(NessieError error) {
                        // handle error
                        Log.e("Error", error.getMessage());
                    }
                });

        ArrayList<FragmentUiModel> fragments = new ArrayList<>();
        fragments.add(new FragmentUiModel("Title 1", PlaceholderFragment.newInstance()));
        fragments.add(new FragmentUiModel("Spend Trends", SpendTrendsFragment.newInstance()));

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(
                new SectionsPagerAdapter(this, getSupportFragmentManager(), fragments)
        );
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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



}


