package com.example.asmduanmau.Model;

import androidx.annotation.NonNull;

public class SACH {
  private   String Masach;
   private String Matheloai;
   private String Tieude;
   private String Tacgia;
   private String Nxb;
   private double Giabia;
   private int Soluong;

    public SACH() {
    }

    public SACH(String masach, String matheloai, String tieude, String tacgia, String nxb, double giabia, int soluong) {
        Masach = masach;
        Matheloai = matheloai;
        Tieude = tieude;
        Tacgia = tacgia;
        Nxb = nxb;
        Giabia = giabia;
        Soluong = soluong;
    }

    public String getMasach() {
        return Masach;
    }

    public void setMasach(String masach) {
        Masach = masach;
    }

    public String getMatheloai() {
        return Matheloai;
    }

    public void setMatheloai(String matheloai) {
        Matheloai = matheloai;
    }

    public String getTieude() {
        return Tieude;
    }

    public void setTieude(String tieude) {
        Tieude = tieude;
    }

    public String getTacgia() {
        return Tacgia;
    }

    public void setTacgia(String tacgia) {
        Tacgia = tacgia;
    }

    public String getNxb() {
        return Nxb;
    }

    public void setNxb(String nxb) {
        Nxb = nxb;
    }

    public double getGiabia() {
        return Giabia;
    }

    public void setGiabia(double giabia) {
        Giabia = giabia;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    @NonNull
    @Override
    public String toString() {
        return getMasach()+"|"+getTieude();
    }
}
