package com.example.asmduanmau.Model;

public class HOADON {
    private String Mahoadon;
    private String Ngay;

    public HOADON() {
    }

    public HOADON(String mahoadon, String ngay) {
        Mahoadon = mahoadon;
        Ngay = ngay;
    }

    public String getMahoadon() {
        return Mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        Mahoadon = mahoadon;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }
}
