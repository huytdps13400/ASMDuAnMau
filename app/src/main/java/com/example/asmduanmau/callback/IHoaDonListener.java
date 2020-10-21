package com.example.asmduanmau.callback;

import com.example.asmduanmau.Model.HOADONCHITIET;

import java.util.ArrayList;

public interface IHoaDonListener {

    void onSuccess(ArrayList<HOADONCHITIET> lists);
    void onError(String message);

}
