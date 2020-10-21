package com.example.asmduanmau.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asmduanmau.adapter.AdapterLoaiSach;
import com.example.asmduanmau.DAOFireBase.DAOLoaiSach;
import com.example.asmduanmau.FrameLayout.framelayout_theloai;
import com.example.asmduanmau.Model.THELOAISACH;
import com.example.asmduanmau.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import static com.example.asmduanmau.FrameLayout.framelayout_theloai.adapterLoaiSach;
import static com.example.asmduanmau.FrameLayout.framelayout_theloai.recytheloai;

public class Bottom_Sheft_Add_TheLoaiSach extends BottomSheetDialogFragment {
    EditText edt_maloai, edt_tenloai, edt_mota, edt_vitri;
    Button btn_add;
    ArrayList<THELOAISACH> dataloaisach = new ArrayList<>();
    DAOLoaiSach daoLoaiSach;
    THELOAISACH theloaisach;
    framelayout_theloai framelayout_theloai;

    public Bottom_Sheft_Add_TheLoaiSach() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_add_theloaisach, container, false);
        edt_maloai = view.findViewById(R.id.edt_maloai);
        edt_tenloai = view.findViewById(R.id.edt_tenloaisach);
        edt_mota = view.findViewById(R.id.edt_mota);
        edt_vitri = view.findViewById(R.id.edt_vitri);
        btn_add = view.findViewById(R.id.btnadd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maloai = edt_maloai.getText().toString();
                String tenloai = edt_tenloai.getText().toString();
                String mota = edt_mota.getText().toString();
                String vitri = edt_vitri.getText().toString();

                if (maloai.isEmpty() || tenloai.isEmpty() || mota.isEmpty() || vitri.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    if (xetTrung(maloai)) {
                        Toast.makeText(getActivity(), "Mã không được trùng!", Toast.LENGTH_SHORT).show();

                    } else {
                        dataloaisach = new ArrayList<>();
                        daoLoaiSach = new DAOLoaiSach(getContext());
                        theloaisach = new THELOAISACH(maloai, tenloai, mota, Integer.parseInt(vitri));
                        daoLoaiSach.insert(theloaisach);
                        dataloaisach = daoLoaiSach.getAll();
                        adapterLoaiSach = new AdapterLoaiSach(getActivity(), dataloaisach);
                        recytheloai.setAdapter(adapterLoaiSach);
                        adapterLoaiSach.notifyDataSetChanged();
                        dismiss();
                    }
                }


            }
        });
        return view;
    }

    public boolean xetTrung(String maTheLoai) {
        Boolean xet = false;
        daoLoaiSach = new DAOLoaiSach(getContext());
        dataloaisach = daoLoaiSach.getAll();
        for (int i = 0; i < dataloaisach.size(); i++) {
            String ma = dataloaisach.get(i).getMatheloai();
            if (ma.equalsIgnoreCase(maTheLoai)) {
                xet = true;
                break;
            }
        }
        return xet;
    }
}
