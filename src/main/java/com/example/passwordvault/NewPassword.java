package com.example.passwordvault;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPassword extends AppCompatActivity {

    EditText etOldPin, etNewPin, etNewPinRe;
    Button btnSave;

    String MasterPassword,oldpin,newpin,newpinre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        MasterPassword = settings.getString("password","");

        etOldPin = findViewById(R.id.etOldPin);
        etNewPin = findViewById(R.id.etNewPin);
        etNewPinRe = findViewById(R.id.etNewPinRe);
        btnSave = findViewById(R.id.btnSAVE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpin = etOldPin.getText().toString();
                newpin = etNewPin.getText().toString();
                newpinre = etNewPinRe.getText().toString();

                if(oldpin.equals(MasterPassword)){
                    if (newpinre.equals(newpin)){

                        MasterPassword = newpin;

                        Toast.makeText(getApplicationContext(),"New Pin Updated",Toast.LENGTH_LONG).show();


                        SharedPreferences settings = getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password",MasterPassword);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(),EnterPassActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),"Passwords Don't Match",Toast.LENGTH_LONG).show();
                    }
                }else {
                    etOldPin.setError("Passwords Don't Match");
                }

            }
        });

    }
}
