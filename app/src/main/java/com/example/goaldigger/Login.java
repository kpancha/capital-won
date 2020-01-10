package com.example.goaldigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.goaldigger.ui.main.SetGoal;
public class Login extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signIn = (Button)findViewById(R.id.sign_in);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    move();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void move() {
        Intent intent = new Intent( Login.this, SetGoal.class);
        startActivity(intent);
    }
}
