package com.example.asmduanmau.Model;

public class USER {
    private String Username;
    private String Password;
    private String Phone;
    private String Hoten;

    public USER() {
    }

    public USER(String username, String password, String phone, String hoten) {
        Username = username;
        Password = password;
        Phone = phone;
        Hoten = hoten;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }
}
