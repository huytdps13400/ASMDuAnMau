package com.example.asmduanmau.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQlite extends SQLiteOpenHelper {

    public  SQlite(Context context) {
        super(context,"Quanlisach.db",null,1);

        createTable();
    }

    private void createTable() {
        String queryCreateTableTheLoaiSach = "CREATE TABLE IF NOT EXISTS THELOAISACH(MATHELOAI TEXT primary key ,"+"TENTHELOAI text," +
                "MOTA TEXT,"+ "VITRI INTEGER)";
        exc(queryCreateTableTheLoaiSach);

        String queryCreateTableSach = "CREATE TABLE IF NOT EXISTS SACH(MASACH TEXT PRIMARY KEY,"+"MATHELOAI TEXT REFERENCES THELOAISACH(MATHELOAI),"+"TIEUDE TEXT,"+"TACGIA TEXT,"+"NXB TEXT,"+"" +
                "GIABIA FLOAT,"+"SOLUONG INTEGER)";
        exc(queryCreateTableSach);

        String queryCreateTableHoaDon = "CREATE TABLE IF NOT EXISTS HOADON(MAHOADON TEXT primary key ,"+"NGAYMUA DATE)";
        exc(queryCreateTableHoaDon);

        String queryCreateTableHoaDonChiTiet = "CREATE TABLE IF NOT EXISTS HOADONCHITIET(MAHDCT INTEGER  primary key autoincrement ,"+"MAHOADON Text REFERENCES HOADON(MAHOADON),"+"MASACH Text REFERENCES SACH(MASACH),"+"SOLUONGMUA INTEGER)";
        exc(queryCreateTableHoaDonChiTiet);

        String queryCreateTableUser = "CREATE TABLE IF NOT EXISTS USER(USERNAME TEXT primary key ,"+"PASSWORD text," +
                "PHONE TEXT,"+ "HOTEN TEXT)";
        exc(queryCreateTableUser);
    }

    private void exc(String sql){
        getWritableDatabase().execSQL(sql);
    }

    private Cursor getCursor(String sql){
        return getReadableDatabase().rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
