package com.example.asmduanmau.FrameLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.DAOFireBase.DAOHDCT;
import com.example.asmduanmau.Model.HOADONCHITIET;
import com.example.asmduanmau.Model.SACH;
import com.example.asmduanmau.R;
import com.example.asmduanmau.adapter.AdapterHDCT;
import com.example.asmduanmau.adapter.AdapterSach;
import com.example.asmduanmau.adapter.AdapterShowTop;
import com.example.asmduanmau.callback.IHoaDonListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class framelayout_bestselling extends Fragment {
    EditText tvsearch;
    Button btntim;
    ArrayList<HOADONCHITIET> datahdct ;
    DAOHDCT daohdct;
     String ngay, thang, nam;
    ListView rcvbestbook;
    AdapterSach adapterSach;

    AdapterShowTop adapterHDCT;
    final ArrayList<HOADONCHITIET> listthang = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelayout_bestselling,container,false);
        tvsearch = view.findViewById(R.id.tvsearch);
        btntim = view.findViewById(R.id.btntim);
        rcvbestbook= view.findViewById(R.id.rcvbestbook);

        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        ngay = currentDate.substring(0, 2);
        thang = currentDate.substring(3, 5);
        nam = currentDate.substring(6, currentDate.length());

        btntim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child("HDCT").orderByChild("soluongmua").limitToFirst(10).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            HOADONCHITIET hoadonchitiet = data.getValue(HOADONCHITIET.class);
                            String ngaymua = hoadonchitiet.getMahoadon().getNgay();
                            String thang = ngaymua.substring(3,5);
                            if (thang.equals(tvsearch.getText().toString())) {
                                listthang.add(hoadonchitiet);
                            } else {

                            }
                        }
                     if (listthang.size() ==0){
                         Toast.makeText(getActivity(), "Không Có Thông Tin ", Toast.LENGTH_SHORT).show();
                     }
                        Collections.reverse(listthang);
                        adapterHDCT = new AdapterShowTop(getActivity(),listthang);
                        rcvbestbook.setAdapter(adapterHDCT);
                        adapterHDCT.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return view;
    }
//    public void top10(){
//        datahdct = new ArrayList<>();
//        daohdct = new DAOHDCT(getContext());
//
//        daohdct.getAll(new IHoaDonListener() {
//            @Override
//            public void onSuccess(ArrayList<HOADONCHITIET> lists) {
//                //Bước 1 add vào danh sách lấy ra được tháng mà trùng với edt mình nhập add vào list
//                // data clear rồi add lại danh sách vì firebase đa luồng
//                datahdct.clear();
//                datahdct.addAll(lists);
//                // dùng vòng lặp for đếm danh sách sau khi add lấy ra được
//                // date trong danh sách hóa đơn chi tiết
//                // từ date đã lấy ra sẽ dùng substring chuỗi ngày tháng
//                for (int i =0;i<datahdct.size();i++){
//                    String date = datahdct.get(i).getMahoadon().getNgay();
//                    String serthang = date.substring(3,5);
//                    // So sánh tháng sau khi cắt mà trùng với edt mà mình nhập vào
//                    // tạo 1 array khác sẽ add vào vị trí danh sách mà mình đã so sánh được
//                    if (serthang.matches(tvsearch.getText().toString())){
//                        listthang.add(datahdct.get(i));
//                    }
//                }
//                // Bước 2 lấy danh sách đã add được tháng đã so sánh từ đó lấy ra được số lượng mua
//                // dùng vòng lặp so sánh số lượng để lấy ra count số lượng lớn nhất
//                // vòng lặp for đếm số lượng mà đã
//                if (listthang.size() != 0){
//                    if(listthang.size()>10){
//                        for (int i =0;i<listthang.size();i++){
//                            int sl1 = listthang.get(i).getSoluongmua();
//                            for (int j = i+1; j< listthang.size()-1;j++){
//                                int sl2 = listthang.get(j).getSoluongmua();
//                                if(sl1<sl2){
//                                    HOADONCHITIET hoadonchitiet = listthang.get(i);
//                                    listthang.set(i,listthang.get(j));
//                                    listthang.set(j,hoadonchitiet);
//                                }
//                            }
//                        }
//                    }
//                    convertolistsach();
//                }else {
//                    Toast.makeText(getActivity(), "Không Có Thông Tin Tháng Đó", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onError(String message) {
//
//            }
//        });
//    }

}
