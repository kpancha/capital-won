package com.example.goaldigger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.TransactionMedium;
import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.models.Deposit;
import com.reimaginebanking.api.nessieandroidsdk.models.PostResponse;
import com.reimaginebanking.api.nessieandroidsdk.models.Purchase;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;
import android.util.Log;


import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    NessieClient client = NessieClient.getInstance("e54eea15303279c82bcf0d8b3330e5f3");
    String person1 = "5e173afc322fa016762f3794";
    String person2 = "5e173afc322fa016762f3795";
    String person3 = "5e173afb322fa016762f3796";

    public NessieClient getClient() {
        return client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        client.CUSTOMER.getCustomers(new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                List<Customer> customers = (List<Customer>) result;
                Log.d("message", customers.toString());
                //System.out.print(customers.toString());
                // do something with the list of customers here
            }

            @Override
            public void onFailure(NessieError error) {
                // handle error
                Log.e("Error", error.getMessage());
            }
        });

        getClient();


//        client.PURCHASE.createPurchase("deposit_id", purchase, new NessieResultsListener() {
//            @Override
//            public void onSuccess(Object result) {
//                PostResponse<Deposit> response = (PostResponse<Deposit>) result;
//                Deposit deposit = response.getObjectCreated();
//                // do something with the newly created deposit
//            }
//        });





    }



}


