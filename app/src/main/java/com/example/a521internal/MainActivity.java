package com.example.a521internal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private static final String LOGIN_FILE_NAME = "login";
    private static final String PASSWORD_FILE_NAME = "password";
    EditText editLogin;
    EditText editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);

        findViewById(R.id.btnRegistration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegistration(v);
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onClickLogin(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void onClickLogin(View view) throws IOException{

        String savedLogin = readSavedData(LOGIN_FILE_NAME);
        String savedPassword = readSavedData(PASSWORD_FILE_NAME);

        if (editLogin.getText().toString().equals(savedLogin) && editPassword.getText().toString().equals(savedPassword))
        {
            Toast.makeText(this, "Верный логин и пароль", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }

    }

    private String readSavedData(String fileName) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void onClickRegistration(View view) {
        FileOutputStream fileLogins = null;
        FileOutputStream fileLPassword = null;
        if (editLogin.getText().equals("") || editPassword.getText().equals("")) {
            Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
        } else {
            try {
                fileLogins = openFileOutput(LOGIN_FILE_NAME, MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileLogins);
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            try {
                bw.write(editLogin.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileLPassword = openFileOutput(PASSWORD_FILE_NAME, MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            OutputStreamWriter outputStreamWriterPassword = new OutputStreamWriter(fileLPassword);
            BufferedWriter bwp = new BufferedWriter(outputStreamWriterPassword);
            try {
                bwp.write(editPassword.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bw.close();
                bwp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, R.string.login_and_password_saved_successfully, Toast.LENGTH_SHORT).show();
        }
    }
}
