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

import com.example.asmduanmau.adapter.AdapterUser;

import com.example.asmduanmau.DAOFireBase.DAOUser;


import com.example.asmduanmau.Model.USER;
import com.example.asmduanmau.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class Bottom_Sheft_Add_User extends BottomSheetDialogFragment {
    EditText edt_maloai,edt_tenloai,edt_mota,edt_vitri;
    Button btn_add;
    ArrayList<USER> datauser;
    DAOUser daoUser;
    USER user;
    AdapterUser adapterUser;

    public Bottom_Sheft_Add_User() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_add_user,container,false);
        edt_maloai= view.findViewById(R.id.edt_maloai);
        edt_tenloai= view.findViewById(R.id.edt_tenloaisach);
        edt_mota= view.findViewById(R.id.edt_mota);
        edt_vitri= view.findViewById(R.id.edt_vitri);
        btn_add= view.findViewById(R.id.btnadd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maloai = edt_maloai.getText().toString();
                String tenloai = edt_tenloai.getText().toString();
                String mota = edt_mota.getText().toString();
                String vitri =edt_vitri.getText().toString();
                if (maloai.isEmpty() || tenloai.isEmpty() || mota.isEmpty() ||  vitri.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    if(tenloai.length()<5){
                        Toast.makeText(getActivity(), "Mật khẩu ít nhất 5 kí tự", Toast.LENGTH_SHORT).show();
                    }else if (edt_vitri.getText().toString().length() < 10 || edt_vitri.getText().toString().length() > 12) {
                        Toast.makeText(getActivity(), "Vui lòng nhập đúng số điện thoại!", Toast.LENGTH_SHORT).show();
                    } else if (!mota.matches("[a-zẠ-ỹA-Z\\s]{4,}")) {
                        Toast.makeText(getActivity(), "Họ tên phải có ít nhất 4 ký tự",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        datauser= new ArrayList<>();
                        daoUser = new DAOUser(getContext());
                        user = new USER(maloai,tenloai,mota,vitri);
                        daoUser.insert(user);
                        dismiss();
                    }

                }


            }
        });
        return view;
    }
}
