package com.example.asmduanmau.Model;

public class HOADONCHITIET {
    private String Mahdct;
    private HOADON Mahoadon;
    private SACH Masach;
    private int Soluongmua;

    public HOADONCHITIET() {
    }

    public HOADONCHITIET(String mahdct, HOADON mahoadon, SACH masach, int soluongmua) {
        Mahdct = mahdct;
        Mahoadon = mahoadon;
        Masach = masach;
        Soluongmua = soluongmua;
    }

    public String getMahdct() {
        return Mahdct;
    }

    public void setMahdct(String mahdct) {
        Mahdct = mahdct;
    }

    public HOADON getMahoadon() {
        return Mahoadon;
    }

    public void setMahoadon(HOADON mahoadon) {
        Mahoadon = mahoadon;
    }

    public SACH getMasach() {
        return Masach;
    }

    public void setMasach(SACH masach) {
        Masach = masach;
    }

    public int getSoluongmua() {
        return Soluongmua;
    }

    public void setSoluongmua(int soluongmua) {
        Soluongmua = soluongmua;
    }
}
