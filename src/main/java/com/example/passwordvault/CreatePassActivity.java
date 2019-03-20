package com.example.passwordvault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePassActivity extends AppCompatActivity {

    EditText etpassword1,etpassword2;
    Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpassword);

        etpassword1 = findViewById(R.id.etPassword);
        etpassword2 = findViewById(R.id.etrepassword);

        btnSave = findViewById(R.id.btnsave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass1 = etpassword1.getText().toString();
                String pass2 = etpassword2.getText().toString();

                if(pass1.equals("") || pass2.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter All Fields",Toast.LENGTH_LONG).show();
                }
                else if (pass1.equals(pass2)){

                    SharedPreferences settings = getSharedPreferences("PREFS",0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("password",pass1);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Passwords Dont Match",Toast.LENGTH_LONG).show();
                }
            }
        });

        getSupportActionBar().hide();
    }
}
