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

    private TextInputEditText numWeeks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_goal);

        createGoal = findViewById(R.id.button);
        goalName = findViewById(R.id.textInputEditText2);
        goalCost = findViewById(R.id.textInputEditText3);
        numWeeks = findViewById(R.id.weeksInput);

        savingsStart = findViewById(R.id.textInputEditText);

        createGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetGoal.this, MainActivity.class);
                intent.putExtra(MainActivity.GOAL_NAME_KEY, goalName.getText().toString());
                intent.putExtra(MainActivity.GOAL_COST_KEY, Double.parseDouble(goalCost.getText().toString()));
                intent.putExtra(MainActivity.SAVINGS_START_KEY, Double.parseDouble(savingsStart.getText().toString()));
                intent.putExtra(MainActivity.NUM_WEEKS_KEY, Integer.parseInt(numWeeks.getText().toString()));
                startActivity(intent);
                //moveToMain();
            }
        });
    }

    private void moveToMain() {
        Intent intent = new Intent( this, MainActivity.class);
    }
}
