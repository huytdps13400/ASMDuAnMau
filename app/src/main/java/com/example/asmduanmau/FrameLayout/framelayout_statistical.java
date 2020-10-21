package com.example.asmduanmau.FrameLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asmduanmau.DAOFireBase.DAOHDCT;
import com.example.asmduanmau.Model.HOADONCHITIET;
import com.example.asmduanmau.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class framelayout_statistical extends Fragment {
    public static TextView tvNgay, tvThang, tvNam;
    public static ArrayList<HOADONCHITIET> datahdcts = new ArrayList<>();
    DAOHDCT daohdct;
    public static String ngay, thang, nam;
    public static int tongThang = 0, tongNam = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelayout_statistical, container, false);

        tongThang = 0;
        tongNam = 0;

        //Lấy toàn bộ list HDCT
        daohdct = new DAOHDCT(getContext());


        tvNgay = (TextView) view.findViewById(R.id.tvThongKeNgay);
        tvThang = (TextView) view.findViewById(R.id.tvThongKeThang);
        tvNam = (TextView) view.findViewById(R.id.tvThongKeNam);

        tvNgay.setText(0 + " đ");
        tvThang.setText(tongThang + " đ");
        tvNam.setText(tongNam + " đ");


        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        ngay = currentDate.substring(0, 2);
        thang = currentDate.substring(3, 5);
        nam = currentDate.substring(6, currentDate.length());


//        for (int i = 0; i < datahdcts.size(); i++) {
//            String ngayTK = datahdcts.get(i).getMahoadon().getNgay().substring(0, 2);
//            String thangTK = datahdcts.get(i).getMahoadon().getNgay().substring(3, 5);
//            String namTK = datahdcts.get(i).getMahoadon().getNgay().substring(6, datahdcts.get(i).getMahoadon().getNgay().length());
//            if (ngay.matches(ngayTK)) {
//                tongNgay += datahdcts.get(i).getSoluongmua() * datahdcts.get(i).getMasach().getGiabia();
//            }
//            if (thang.matches(thangTK)) {
//                tongThang += datahdcts.get(i).getSoluongmua() * datahdcts.get(i).getMasach().getGiabia();
//            }
//            if (nam.matches(namTK)) {
//                tongNam += datahdcts.get(i).getSoluongmua() * datahdcts.get(i).getMasach().getGiabia();
//            }
//
//        }
        final ArrayList<HOADONCHITIET> list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("HDCT").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                int tongNgay = 0;
                for (DataSnapshot data : snapshot.getChildren()) {
                    HOADONCHITIET hoadonchitiet = data.getValue(HOADONCHITIET.class);
                    //Lọc theo mã hd
                    list.add(hoadonchitiet);
                    String ngayTK = hoadonchitiet.getMahoadon().getNgay().substring(0, 2);
                    String thangTK = hoadonchitiet.getMahoadon().getNgay().substring(3, 5);
                    String namTK = hoadonchitiet.getMahoadon().getNgay().substring(6, hoadonchitiet.getMahoadon().getNgay().length());
                    if (ngay.matches(ngayTK)) {

                        tongNgay += hoadonchitiet.getSoluongmua() * hoadonchitiet.getMasach().getGiabia();
                    }
                    if (thang.matches(thangTK)) {
                        tongThang += hoadonchitiet.getSoluongmua() *hoadonchitiet.getMasach().getGiabia();
                    }
                    if (nam.matches(namTK)) {
                        tongNam += hoadonchitiet.getSoluongmua() * hoadonchitiet.getMasach().getGiabia();
                    }
                }
                final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                decimalFormat.applyPattern("#,###,###,###");
                tvNgay.setText(decimalFormat.format(tongNgay) + " VNĐ");
                tvThang.setText(decimalFormat.format(tongThang) + " VNĐ");
                tvNam.setText(decimalFormat.format(tongNam) + " VNĐ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


}
