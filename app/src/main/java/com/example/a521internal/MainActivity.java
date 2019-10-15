package com.example.a521internal;



import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.view.View;

import android.widget.EditText;

import android.widget.Toast;


import java.io.BufferedReader;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {
    private EditText mEditLogin;
    private EditText mEditPassword;

    private final String experience = "exp";
    private final int AUTH_EXPERIENCE = 10;
   private int experience_counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (savedInstanceState != null) {
            experience_counter = savedInstanceState.getInt(experience);
        } else {
            experience_counter = 1;
        }
    }

    private void initView() {
        mEditLogin = findViewById(R.id.editLogin);
        mEditPassword = findViewById(R.id.editPassword);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCorrect()) {
                    checkData();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.incorrect_input_message), Toast.LENGTH_LONG).show();

                }
            }
        });


        findViewById(R.id.btnRegistration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCorrect()) {
                    saveData();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.incorrect_input_message), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private boolean inputCorrect() {
        return !mEditLogin.getText().toString().equals("")
                && !mEditPassword.getText().toString().equals("");
    }

    private void checkData() {
        String ofn = mEditLogin.getText().toString().hashCode() + ".data";
        try {
            FileInputStream authFile = openFileInput(ofn);
            InputStreamReader reader = new InputStreamReader(authFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            int hashedPassword = Integer.valueOf(bufferedReader.readLine());

            bufferedReader.close();
            reader.close();

            if (mEditPassword.getText().toString().hashCode() == hashedPassword) {
                Toast.makeText(getApplicationContext(), getString(R.string.auth_message), Toast.LENGTH_LONG).show();

            } else {
                userNotAuth();
            }

        } catch (FileNotFoundException e) {
            userNotAuth();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userNotAuth() {
        Toast.makeText(getApplicationContext(), getString(R.string.no_auth_message), Toast.LENGTH_LONG).show();

       experience_counter++;
        if (experience_counter > AUTH_EXPERIENCE) {
            Toast.makeText(getApplicationContext(), getString(R.string.auth_failed_toast_message), Toast.LENGTH_LONG).show();

            finish();
        }
    }

    private void saveData() {
        String afn = mEditLogin.getText().toString().hashCode() + ".data";
        try {
            FileOutputStream authFile = openFileOutput(afn, MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(authFile);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(String.valueOf(mEditPassword.getText().toString().hashCode()));

            bufferedWriter.close();
            writer.close();
            Toast.makeText(getApplicationContext(), getString(R.string.registration_message), Toast.LENGTH_LONG).show();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}

