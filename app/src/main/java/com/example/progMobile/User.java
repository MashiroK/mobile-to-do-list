package com.example.progMobile;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String nome, email, telefone, senha; //local

    public User(){} //Default

    //No ID
    public User(String name, String email, String phone, String password) {
        this.nome = name;
        this.email = email;
        this.telefone = phone;
        this.senha = password;

    }

    //ID
    public User(int id, String name, String email, String phone, String password) {
        this.id = id;
        this.nome = name;
        this.email = email;
        this.telefone = phone;
        this.senha = password;

    }

    //const, set e gets
    public User(String email) {
        this.email = email;
    }
    public int getIdUser() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nome;
    }
    public void setName(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return telefone;
    }
    public void setPhone(String telefone) {
        this.telefone = telefone;
    }

    public String getPassword() {
        return senha;
    }
    public void setPassword(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
