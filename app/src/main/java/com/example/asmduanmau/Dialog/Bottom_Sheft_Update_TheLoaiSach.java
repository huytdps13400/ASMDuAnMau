package com.example.asmduanmau.Dialog;

import android.content.DialogInterface;
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
import com.example.asmduanmau.Model.THELOAISACH;
import com.example.asmduanmau.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import static com.example.asmduanmau.FrameLayout.framelayout_theloai.adapterLoaiSach;
import static com.example.asmduanmau.FrameLayout.framelayout_theloai.datatheloai;
import static com.example.asmduanmau.FrameLayout.framelayout_theloai.recytheloai;

public class Bottom_Sheft_Update_TheLoaiSach extends BottomSheetDialogFragment {
    EditText edt_maloai, edt_tenloai, edt_mota, edt_vitri;
    Button btnupdate;
    ArrayList<THELOAISACH> dataloaisach;
    DAOLoaiSach daoLoaiSach;
    THELOAISACH theloaisach;
    int vitri;

    public Bottom_Sheft_Update_TheLoaiSach() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_update_theloaisach, container, false);
        edt_maloai = view.findViewById(R.id.edt_maloai);
        edt_tenloai = view.findViewById(R.id.edt_tenloaisach);
        edt_mota = view.findViewById(R.id.edt_mota);
        edt_vitri = view.findViewById(R.id.edt_vitri);
        btnupdate = view.findViewById(R.id.btnupdate);
        final Bundle getdata = getArguments();
        String matl = getdata.getString("MaTL");
        String tentl = getdata.getString("TenTL");
        String mota = getdata.getString("MoTa");
        vitri = getdata.getInt("ViTri");
        edt_maloai.setText(matl);
        edt_tenloai.setText(tentl);
        edt_mota.setText(mota);
        edt_vitri.setText(String.valueOf(vitri));

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maloai = edt_maloai.getText().toString();
                String tenloai = edt_tenloai.getText().toString();
                String mota = edt_mota.getText().toString();
                String vitri = edt_vitri.getText().toString();

//                dataloaisach.clear();
                if (maloai.isEmpty() || tenloai.isEmpty() || mota.isEmpty() || vitri.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    dataloaisach = new ArrayList<>();
                    daoLoaiSach = new DAOLoaiSach(getContext());
                    theloaisach = new THELOAISACH(maloai, tenloai, mota, Integer.parseInt(vitri));
                    daoLoaiSach.update(theloaisach);
                    dataloaisach = daoLoaiSach.getAll();
                    adapterLoaiSach = new AdapterLoaiSach(getActivity(), datatheloai);
                    recytheloai.setAdapter(adapterLoaiSach);
                    adapterLoaiSach.notifyDataSetChanged();
                    dismiss();
                }


            }
        });
        return view;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        dataloaisach = new ArrayList<>();
        daoLoaiSach = new DAOLoaiSach(getContext());
        dataloaisach = daoLoaiSach.getAll();
        adapterLoaiSach = new AdapterLoaiSach(getActivity(), datatheloai);
        recytheloai.setAdapter(adapterLoaiSach);

    }
}
