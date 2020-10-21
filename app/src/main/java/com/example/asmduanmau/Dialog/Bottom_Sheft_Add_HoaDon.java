package com.example.asmduanmau.Dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.asmduanmau.adapter.AdapterHoaDon;
import com.example.asmduanmau.DAOFireBase.DAOHoaDon;

import com.example.asmduanmau.Model.HOADON;

import com.example.asmduanmau.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Bottom_Sheft_Add_HoaDon extends BottomSheetDialogFragment {
    EditText edt_mahoadon;
    TextView edt_tenhoadon;
    Button btn_add;
    ArrayList<HOADON> datahoadon;
    DAOHoaDon daoHoaDon;

    AdapterHoaDon adapterHoaDon;
    public Bottom_Sheft_Add_HoaDon() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_add_hoadon,container,false);
        edt_mahoadon= view.findViewById(R.id.edt_mahoadon);
        edt_tenhoadon= view.findViewById(R.id.tv_date);

        btn_add= view.findViewById(R.id.btnadd);
        edt_tenhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datapicker();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahd = edt_mahoadon.getText().toString();
                String tenhd = edt_tenhoadon.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("MAHD",mahd);
                bundle.putString("NGAY",tenhd);
                if (mahd.isEmpty()|| tenhd.isEmpty()){
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    datahoadon= new ArrayList<>();
                    daoHoaDon = new DAOHoaDon(getContext());
                    HOADON hoadon = new HOADON(mahd,tenhd);
                    daoHoaDon.insert(hoadon);
                    dismiss();
//                    startActivity(new Intent(getActivity(),Bottom_Sheft_Add_HDCT.class));
                    Bottom_Sheft_Add_HDCT bottom_sheft_add_hdct = new Bottom_Sheft_Add_HDCT();
                    bottom_sheft_add_hdct.setArguments(bundle);
                    bottom_sheft_add_hdct.show(getActivity().getSupportFragmentManager(), bottom_sheft_add_hdct.getTag());
                }


            }
        });
        return view;
    }
    private void datapicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(year2, month2, dayOfMonth2);
                String date = dateFormat.format(calendar.getTime());
                edt_tenhoadon.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
