package com.example.passwordvault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EnterPassActivity extends AppCompatActivity {

    EditText etPin;
    Button btnEnter;
    String Masterpassword;
    TextView changePin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpassword);

        etPin = findViewById(R.id.etPin);
        btnEnter = findViewById(R.id.btnSubmit);
        changePin = findViewById(R.id.tvChangePin);

        SharedPreferences settings = getSharedPreferences("PREFS",0);
        Masterpassword = settings.getString("password","");

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password;
                password = etPin.getText().toString();

                if (password.equals(Masterpassword)){
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   startActivity(intent);
                   finish();
                }else
                {
                    Toast.makeText(getApplicationContext(),"Enter Valid Password",Toast.LENGTH_LONG).show();
                }
            }
        });

        changePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NewPassword.class);
                startActivity(intent);
            }
        });

getSupportActionBar().hide();
    }
}
