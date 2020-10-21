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


import com.example.asmduanmau.adapter.AdapterUser;
import com.example.asmduanmau.DAOFireBase.DAOUser;

import com.example.asmduanmau.Model.USER;
import com.example.asmduanmau.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.ArrayList;


import static com.example.asmduanmau.FrameLayout.framelayout_user.adapterUser;
import static com.example.asmduanmau.FrameLayout.framelayout_user.recyuser;


public class Bottom_Sheft_Update_User extends BottomSheetDialogFragment {
    EditText edt_maloai,edt_tenloai,edt_mota,edt_vitri;
    Button btnupdate;
    ArrayList<USER> datauser;
    DAOUser daoUser;



    public Bottom_Sheft_Update_User() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_update_user,container,false);
        edt_maloai= view.findViewById(R.id.edt_maloai);
        edt_tenloai= view.findViewById(R.id.edt_tenloaisach);
        edt_mota= view.findViewById(R.id.edt_mota);
        edt_vitri= view.findViewById(R.id.edt_vitri);
        btnupdate= view.findViewById(R.id.btnupdate);
        final Bundle getdata = getArguments();
        String matl = getdata.getString("username");
        String tentl = getdata.getString("password");
        String mota = getdata.getString("sdt");
        String viTri = getdata.getString("hoten");
        edt_maloai.setText(matl);
        edt_tenloai.setText(tentl);
        edt_mota.setText(mota);
        edt_vitri.setText(viTri);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maloai = edt_maloai.getText().toString();
                String tenloai = edt_tenloai.getText().toString();
                String mota = edt_mota.getText().toString();
                String vitri = edt_vitri.getText().toString();
                if (maloai.isEmpty() || tenloai.isEmpty() || mota.isEmpty() ||  vitri.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    datauser= new ArrayList<>();
                    daoUser = new DAOUser(getContext());
                    USER user = new USER(maloai,tenloai,mota,vitri);
                    daoUser.update(user);
                    datauser=daoUser.getAll();
                    adapterUser=new AdapterUser(getActivity(),datauser);
                    recyuser.setAdapter(adapterUser);
                    adapterUser.notifyDataSetChanged();
                    dismiss();
                }


            }
        });
        return view;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        datauser= new ArrayList<>();
        daoUser = new DAOUser(getContext());
        datauser=daoUser.getAll();
        adapterUser=new AdapterUser(getActivity(),datauser);
        recyuser.setAdapter(adapterUser);

    }

}
