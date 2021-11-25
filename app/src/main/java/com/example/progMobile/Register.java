package com.example.progMobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.GeneralSecurityException;

public class Register extends AppCompatActivity {
    private EditText edtEmail, edtPhone, edtPassword, edtName;
    User user, altUser;
    DBToDoHelper userHelper;
    private Button altBttn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPasswordRegister);
        Intent it = getIntent();
        altUser = (User) it.getSerializableExtra("ch_user");
        user = new User();
        userHelper = new DBToDoHelper(Register.this);
        altBttn = findViewById(R.id.bttnCadastrar);

        if (altUser != null) {
            altBttn.setText("Alterar");
            edtName.setText(altUser.getName());
            edtEmail.setText(altUser.getEmail());
            edtPhone.setText(altUser.getPhone());
            edtPassword.setText(altUser.getPassword());
            user.setId(altUser.getIdUser());
        } else {
            altBttn.setText("Cadastrar");
        }

        altBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();
                long ret = -1;
                User u = new User();
                u.setName(name);
                u.setEmail(email);
                u.setPhone(phone);
                u.setPassword(password);

                if (altBttn.getText().toString().equals("Cadastrar")) {

                    try {
                        ret = userHelper.insertUser(u);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }

                    if (ret == -1) {
                        Toast.makeText(Register.this, "Não foi possível registrar o usuário",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, "Usuário registrado com sucesso!",
                                Toast.LENGTH_SHORT).show();
                        userHelper.close();
                        finish();
                    }
               } else {

                    try {
                        userHelper.updateUser(user);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }

                    userHelper.close();
                    finish();
            }
        }});

    }


    public void returnApp(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
