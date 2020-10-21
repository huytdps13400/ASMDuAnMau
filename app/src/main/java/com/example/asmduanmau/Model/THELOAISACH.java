package com.example.asmduanmau.Model;

import androidx.annotation.NonNull;

public class THELOAISACH {
    private String Matheloai;
    private String Tentheloai;
    private String Mota;
    private int Vitri;

    public THELOAISACH() {
    }

    public THELOAISACH(String matheloai, String tentheloai, String mota, int vitri) {
        Matheloai = matheloai;
        Tentheloai = tentheloai;
        Mota = mota;
        Vitri = vitri;
    }

    public String getMatheloai() {
        return Matheloai;
    }

    public void setMatheloai(String matheloai) {
        Matheloai = matheloai;
    }

    public String getTentheloai() {
        return Tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        Tentheloai = tentheloai;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public int getVitri() {
        return Vitri;
    }

    public void setVitri(int vitri) {
        Vitri = vitri;
    }

    @NonNull
    @Override
    public String toString() {
        return getMatheloai()+"-"+getTentheloai();
    }
}

