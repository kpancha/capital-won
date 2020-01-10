package com.example.goaldigger.ui.main;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.goaldigger.MainActivity;
import com.example.goaldigger.R;
import com.google.android.material.textfield.TextInputEditText;

public class SetGoal extends AppCompatActivity {

    private Button createGoal;

    private TextInputEditText goalName;

    private TextInputEditText goalCost;

    private TextInputEditText savingsStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createGoal = findViewById(R.id.button);
        goalName = findViewById(R.id.textInputEditText2);
        goalCost = findViewById(R.id.textInputEditText3);
        savingsStart = findViewById(R.id.textInputEditText);

        createGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMain();
            }
        });
    }

    private void moveToMain() {
        Intent intent = new Intent( this, MainActivity.class);
    }
}
