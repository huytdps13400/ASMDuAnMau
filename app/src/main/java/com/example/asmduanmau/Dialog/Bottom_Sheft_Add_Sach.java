package com.example.asmduanmau.Dialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.asmduanmau.DAOFireBase.DAOLoaiSach;
import com.example.asmduanmau.DAOFireBase.DAOSach;

import com.example.asmduanmau.Model.SACH;
import com.example.asmduanmau.Model.THELOAISACH;
import com.example.asmduanmau.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static com.example.asmduanmau.FrameLayout.framelayout_theloai.mRef;

public class Bottom_Sheft_Add_Sach extends BottomSheetDialogFragment {
    EditText edt_masach,edt_tensach,edt_tacgia,edt_nxb,edt_giabia,edt_soluong;
    Button btnaddsach;
    Spinner sp_theloai;
 public static    ArrayList<SACH> datasachs =new ArrayList<>();
    DAOSach daoSach;
    ArrayList<THELOAISACH> dataloai = new ArrayList<>();
    DAOLoaiSach daoLoaiSach;
    ArrayAdapter<THELOAISACH> arrayAdapterTL;
   String show="";
   int i;
    public Bottom_Sheft_Add_Sach() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_add_sach,container,false);
        edt_masach= view.findViewById(R.id.edt_masach);
        edt_tensach= view.findViewById(R.id.edt_tensach);
        edt_tacgia= view.findViewById(R.id.edt_tacgia);
        edt_nxb= view.findViewById(R.id.edt_nxb);
        edt_giabia= view.findViewById(R.id.edt_giabia);
        edt_soluong= view.findViewById(R.id.edt_soluong);
        btnaddsach= view.findViewById(R.id.btnaddsach);
        sp_theloai= view.findViewById(R.id.sp_theloai);
        edt_giabia.addTextChangedListener(onTextChangedListener());
        daoLoaiSach = new DAOLoaiSach(getContext());
        getAllTL();

        ShowAdapterTL();
        sp_theloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show=dataloai.get(sp_theloai.getSelectedItemPosition()).getTentheloai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_theloai.setSelection(checkpositionspinenr(show));



//        adapterSpinnerSach = new AdapterSpinnerSach(getActivity(),datatheloai);
//        sp_theloai.setAdapter(adapterSpinnerSach);
        btnaddsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masach = edt_masach.getText().toString();
                String tensach = edt_tensach.getText().toString();
                String tacgia = edt_tacgia.getText().toString();
                String nxb = edt_nxb.getText().toString();
                String gia =edt_giabia.getText().toString();
                String soluong = edt_soluong.getText().toString();


                String maloai =show;

                if(masach.isEmpty()){
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                }else {
                    if(dataloai.size()==0){
                        Toast.makeText(getActivity(), "Vui lòng thêm thể loại", Toast.LENGTH_SHORT).show();
                    }else {
                        if (tensach.isEmpty() || tacgia.isEmpty() || nxb.isEmpty() || gia.isEmpty() || soluong.isEmpty()){
                            Toast.makeText(getActivity(), "Vui lòng điền đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                        }else {
                            if (xetTrung(masach)){
                                Toast.makeText(getActivity(), "Mã đã tồn tại", Toast.LENGTH_SHORT).show();
                            }else {
                                dataloai = new ArrayList<>();
                                datasachs= new ArrayList<>();
                                daoSach = new DAOSach(getContext());
                                DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                                format.setParseBigDecimal(true);
                                BigDecimal number = null;
                                try {
                                    number = (BigDecimal) format.parse(gia);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                SACH  sach = new SACH(masach,maloai,tensach,tacgia,nxb,Double.parseDouble(number+""),Integer.parseInt(soluong));
                                daoSach.insert(sach);
                                dismiss();
                            }




                        }
                    }
                }


            }
        });
        return view;
    }
//    public void onItemSelected(AdapterView<?> adapter,
//                               View view, int position, long id) {
//        int portIndex = mProtocolAdapter
//                .getPortIndexByProtocol(position);
//        if (portIndex != -1)
//            mPortSpinner.setSelection(portIndex);
//    }

public ArrayList<THELOAISACH> getAllTL(){
        dataloai = new ArrayList<>();
    mRef=FirebaseDatabase.getInstance().getReference("TheLoaiSach");
    mRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                dataloai.clear();
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    THELOAISACH theloaisach = next.getValue(THELOAISACH.class);
                    dataloai.add(theloaisach);
                }
                arrayAdapterTL.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    return dataloai;
}

public void ShowAdapterTL(){
    daoLoaiSach = new DAOLoaiSach(getActivity());
    dataloai = new ArrayList<>();
    dataloai.clear();
  dataloai.addAll(getAllTL());
//    Toast.makeText(getActivity(), "dta"+dataloai.size(), Toast.LENGTH_SHORT).show();
    arrayAdapterTL=new ArrayAdapter<THELOAISACH>(getActivity(),android.R.layout.simple_spinner_item,dataloai);
    arrayAdapterTL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
sp_theloai.setAdapter(arrayAdapterTL);


}
    public boolean xetTrung(String maTheLoai) {

       datasachs=new ArrayList<>();
        daoSach = new DAOSach(getActivity());
        datasachs=daoSach.GetMaTrung();


        Boolean xet = false;

        for (int i = 0; i < datasachs.size(); i++) {
            String ma = datasachs.get(i).getMasach();
            if (ma.equalsIgnoreCase(maTheLoai)) {
                xet = true;
                break;
            }
        }
        return xet;

    }

public int checkpositionspinenr(String str){

        for (int i =0; i<dataloai.size();i++){
        if (str.equals(dataloai.get(i).getTentheloai())){
        return i;
}
        }
        return 0;
}
    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edt_giabia.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    edt_giabia.setText(formattedString);
                    edt_giabia.setSelection(edt_giabia.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edt_giabia.addTextChangedListener(this);
            }
        };

    }
}
