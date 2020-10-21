package com.example.asmduanmau.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.asmduanmau.adapter.AdapterSach;
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
import java.util.Locale;

import static com.example.asmduanmau.FrameLayout.framelayout_book.adapterSach;
import static com.example.asmduanmau.FrameLayout.framelayout_book.recysach;
import static com.example.asmduanmau.FrameLayout.framelayout_theloai.mRef;

public class Bottom_Sheft_Update_Sach extends BottomSheetDialogFragment {
    EditText edt_masach, edt_tensach, edt_tacgia, edt_nxb, edt_giabia, edt_soluong;
    Button btnupdatesach;
    Spinner sp_theloai;
    ArrayList<SACH> datasach = new ArrayList<>();
    DAOSach daoSach;
    ArrayList<THELOAISACH> dataloai = new ArrayList<>();
    DAOLoaiSach daoLoaiSach;
    ArrayAdapter<THELOAISACH> arrayAdapterTL;
    String matloai = "";

    String show = "";
    int i;
    int sl;
    double gia;

    public Bottom_Sheft_Update_Sach() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_update_sach, container, false);
        edt_masach = view.findViewById(R.id.edt_masach);
        edt_tensach = view.findViewById(R.id.edt_tensach);
        edt_tacgia = view.findViewById(R.id.edt_tacgia);
        edt_nxb = view.findViewById(R.id.edt_nxb);
        edt_giabia = view.findViewById(R.id.edt_giabia);
        edt_soluong = view.findViewById(R.id.edt_soluong);
        btnupdatesach = view.findViewById(R.id.btnupdatesach);
        sp_theloai = view.findViewById(R.id.sp_theloai);
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        daoLoaiSach = new DAOLoaiSach(getContext());

        Bundle getdatasach = getArguments();
        String masach = getdatasach.getString("MaSACH");
        matloai = getdatasach.getString("MATL");
        String tieude = getdatasach.getString("TIEUDE");
        String tacgia = getdatasach.getString("TACGIA");
        String nxb = getdatasach.getString("NXB");
        gia = getdatasach.getDouble("GIA");
        sl = getdatasach.getInt("SOLUONG");
        edt_masach.setText(masach);
        edt_tensach.setText(tieude);
        edt_tacgia.setText(tacgia);
        edt_nxb.setText(nxb);
        edt_giabia.setText(String.valueOf(decimalFormat.format(gia)));
        edt_soluong.setText(String.valueOf(sl));
        edt_giabia.addTextChangedListener(onTextChangedListener());
        getAllTL();
//        ShowAdapterTL();

        sp_theloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show = dataloai.get(sp_theloai.getSelectedItemPosition()).getTentheloai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        adapterSpinnerSach = new AdapterSpinnerSach(getActivity(),datatheloai);
//        sp_theloai.setAdapter(adapterSpinnerSach);
        btnupdatesach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masach = edt_masach.getText().toString();
                String tensach = edt_tensach.getText().toString();
                String tacgia = edt_tacgia.getText().toString();
                String nxb = edt_nxb.getText().toString();
                String gia = edt_giabia.getText().toString();
                String soluong = edt_soluong.getText().toString();


                String maloai = show;

                if (masach.isEmpty() || tensach.isEmpty() || tacgia.isEmpty() || nxb.isEmpty() || gia.isEmpty() || soluong.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ các dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    dataloai = new ArrayList<>();

                    datasach = new ArrayList<>();
                    daoSach = new DAOSach(getContext());
                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.setParseBigDecimal(true);
                    BigDecimal number = null;
                    try {
                        number = (BigDecimal) format.parse(gia);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SACH sach = new SACH(masach, maloai, tensach, tacgia, nxb, Double.parseDouble(number+""), Integer.parseInt(soluong));
                    daoSach.update(sach);
                    datasach = daoSach.getAll();
                    adapterSach = new AdapterSach(getActivity(), datasach);
                    recysach.setAdapter(adapterSach);
                    dismiss();
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
    public ArrayList<THELOAISACH> getAllTL() {
        dataloai = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("TheLoaiSach");
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
                    arrayAdapterTL = new ArrayAdapter<THELOAISACH>(getActivity(), android.R.layout.simple_spinner_item, dataloai);
                    arrayAdapterTL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_theloai.setAdapter(arrayAdapterTL);
                    for (int i = 0; i < dataloai.size(); i++) {
                        if (dataloai.get(i).getTentheloai().equals(matloai)) {
                            sp_theloai.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dataloai;
    }

    //public void ShowAdapterTL(){
//    daoLoaiSach = new DAOLoaiSach(getActivity());
//    dataloai = new ArrayList<>();
//    dataloai.clear();
//  dataloai.addAll(getAllTL());
////    Toast.makeText(getActivity(), "dta"+dataloai.size(), Toast.LENGTH_SHORT).show();
//    arrayAdapterTL=new ArrayAdapter<THELOAISACH>(getActivity(),android.R.layout.simple_spinner_item,dataloai);
//    arrayAdapterTL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//sp_theloai.setAdapter(arrayAdapterTL);
//
//
//}
//public int checkpositionspinenr(String str){
//
//        for (int i =0; i<dataloai.size();i++){
//        if (str.equals(dataloai.get(i).getTentheloai())){
//        return i;
//}
//        }
//        return 0;
//}
    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        adapterSach.notifyDataSetChanged();

    }
//    private int getIndex(Spinner spinner, String myString){
//        for (int i=0;i<spinner.getCount();i++){
//            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
//                return i;
//            }
//        }
//
//        return 0;
//    }
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
