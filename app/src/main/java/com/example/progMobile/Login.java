package com.example.progMobile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.security.GeneralSecurityException;


public class Login extends AppCompatActivity {

    private EditText edtLoginUser, edtLoginPassword;
    private Button btnLogin;
    DBToDoHelper userHelper;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtLoginUser = findViewById(R.id.edtLoginUser);
        edtLoginPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.bttnLogin);
        userHelper = new DBToDoHelper(Login.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String user = edtLoginUser.getText().toString();
                String pass = edtLoginPassword.getText().toString();

                String password = "";

                try {
                    password = userHelper.findPasswordCrypto(user);
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }

                userHelper.close();

                if (!user.equals("") && !pass.equals("")) {
                    if (password.equals(pass)) {
                        Intent it = new Intent(Login.this, TaskID.class);
                        startActivity(it);
                    } else {
                        Toast toast = Toast.makeText(Login.this, "Senha e/ou Usuário incorretos", Toast.LENGTH_LONG);
                        toast.show();
                    }
            }
                else {
                    Toast toast = Toast.makeText(Login.this, "Campos requeridos!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }

    }
