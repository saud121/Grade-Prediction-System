package com.example.gradepredictionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class SecondActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Email;
    private EditText Phone;
    private ShowHidePasswordEditText Password;
    private ShowHidePasswordEditText ConfirmPassword;
    private TextView Info;
    private ImageView Register;
    private LinearLayout login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Name=(EditText) findViewById(R.id.name);
        Email=(EditText) findViewById(R.id.email);
        Phone=(EditText) findViewById(R.id.ph);
        Password=(ShowHidePasswordEditText) findViewById(R.id.pass);
        ConfirmPassword=(ShowHidePasswordEditText) findViewById(R.id.Cpass);
        Register=(ImageView) findViewById(R.id.reg);
        login=(LinearLayout) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Password.getText().toString(),ConfirmPassword.getText().toString());
            }
        });

    }
    private void validate(String password1, String password2) {
        if(password1.equals(password2)){
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
//            toast here
        }
    }
}
