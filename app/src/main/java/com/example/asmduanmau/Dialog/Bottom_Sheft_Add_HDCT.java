package com.example.asmduanmau.Dialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.adapter.AdapterHDCT;
import com.example.asmduanmau.DAOFireBase.DAOHDCT;
import com.example.asmduanmau.DAOFireBase.DAOSach;
import com.example.asmduanmau.Model.HOADON;
import com.example.asmduanmau.Model.HOADONCHITIET;
import com.example.asmduanmau.Model.SACH;
import com.example.asmduanmau.R;
import com.example.asmduanmau.callback.IHoaDonListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.asmduanmau.FrameLayout.framelayout_theloai.mRef;

public class Bottom_Sheft_Add_HDCT extends BottomSheetDialogFragment {
    EditText edt_mahoadon, edt_mahdct, edt_slm;
    Button btnaddsach, btnaddlist;
    Spinner sp_theloai;
    TextView txtthanhtien;
    public static ArrayList<HOADONCHITIET> datahdtct = new ArrayList<>();
    DAOSach daoSach;
    ArrayList<SACH> datasach = new ArrayList<>();
    ArrayAdapter<SACH> arrayAdapterTL;
    String show = "";
    int i;
    DAOHDCT daohdct;
    AdapterHDCT adapterHDCT =null;
    RecyclerView rcvhdct;
    double thanhtien=0;
    public Bottom_Sheft_Add_HDCT() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomm_sheft_add_hdct, container, false);
        edt_mahoadon = view.findViewById(R.id.edt_mahoadon);
        edt_mahdct = view.findViewById(R.id.edt_mahdct);
        edt_slm = view.findViewById(R.id.edt_slm);
        btnaddsach = view.findViewById(R.id.btnaddhdct);
        sp_theloai = view.findViewById(R.id.sp_theloai);
        btnaddlist = view.findViewById(R.id.btnaddlist);
        rcvhdct = view.findViewById(R.id.rcvhdct);
        txtthanhtien = view.findViewById(R.id.txtthanhtien);
        daoSach = new DAOSach(getContext());
        getAllTL();

        rcvhdct.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rcvhdct.setLayoutManager(linearLayoutManager);
        adapterHDCT=new AdapterHDCT(datahdtct);
        rcvhdct.setAdapter(adapterHDCT);

        daohdct=new DAOHDCT(getActivity());
        daohdct.getAll(new IHoaDonListener() {
            @Override
            public void onSuccess(ArrayList<HOADONCHITIET> lists) {
                datahdtct.clear();
                datahdtct.addAll(lists);
                adapterHDCT.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {

            }
        });


        ShowAdapterTL();
        sp_theloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                show = datasach.get(sp_theloai.getSelectedItemPosition()).getMasach();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_theloai.setSelection(checkpositionspinenr(show));

        Bundle ass = getArguments();
        final String mahd = ass.getString("MAHD");
        final String ngay = ass.getString("NGAY");
        edt_mahoadon.setText(mahd);
        edt_mahdct.setText(mahd);
//        adapterSpinnerSach = new AdapterSpinnerSach(getActivity(),datatheloai);
//        sp_theloai.setAdapter(adapterSpinnerSach);
        btnaddlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//    HOADONCHITIET hoadonchitiet = new HOADONCHITIET(mahd,mahd,show,Integer.parseInt(edt_slm.getText().toString()));
//    datahdtct.add(hoadonchitiet);
            }
        });

        btnaddlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daohdct = new DAOHDCT(getActivity());
                daoSach = new DAOSach(getActivity());
                try {
                    int sl = 0;
                    SACH sach = null;

                    // lay vi tri theo ma sach spinner
                    // vòng lặp for tìm ra mã sách trong data so sách với spinner mã sách từ đó lấy ra được vị trí của sách đó
                    for (int i = 0; i < datasach.size(); i++) {
                        if (datasach.get(i).getMasach().matches(show)) {
                            sach = datasach.get(i);
                            break;
                        }
                    }
                    //check  xem có dữ liệu nào không, có vị trí nào không?
                    int check =checkListMaSach(datahdtct,show);

                    HOADON hoadon = new HOADON(mahd,ngay);
                    HOADONCHITIET hoadonchitiet = new HOADONCHITIET(edt_mahdct.getText().toString(),hoadon,sach,
                            Integer.parseInt(edt_slm.getText().toString()));
                    // nếu có vị trí từ check trong datahdct với mã sách lấy từ spinner thì sẽ
                    // nếu đã add vào với vị trí đó thì sẽ sau khi add lại với vị trí đó thì sẽ set lại số lượng và số tiền
                    if (check>=0){
                        // soluong sẽ lấy được giá trị từ vị trí hdct trong check masach và lấy ra soluongmua mà người dùng nhập vào
                    int soluong = datahdtct.get(check).getSoluongmua();
                    // sau đó sẽ setSoluongmua = soluong tính được số lượng mà người dụng nhâp vào
                    hoadonchitiet.setSoluongmua(soluong+Integer.parseInt(edt_slm.getText().toString()));
                    // sau đó datahdct sẽ set lại từ vị trí check và với Model Hóa Đơn chi tiết
                    datahdtct.set(check,hoadonchitiet);
                    // cập nhật lại adapter

                    adapterHDCT.notifyDataSetChanged();
                    }
                    // ngược lại nếu mà vị trí đó chưa có sẽ add vào list
                    else {

                        datahdtct.add(hoadonchitiet);
                        adapterHDCT.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
//
            }


        });
        btnaddsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daohdct = new DAOHDCT(getActivity());
                // vòng lặp for cải tiến mỗi lần lặp sẽ lưu dũ liệu đầu đến cuối vào hoadonchitiet
                try {
                    for (HOADONCHITIET hoadonchitiet : datahdtct){
                        daohdct.insert(hoadonchitiet);
                        thanhtien = thanhtien+hoadonchitiet.getSoluongmua()*hoadonchitiet.getMasach().getGiabia();
                    }
                    txtthanhtien.setText("Tổng Tiền:"+thanhtien +"VNĐ");
                }catch (Exception e){
                    Log.e("Error", e.toString());
                }

             dismiss();


            }

        });
        intswipe(view);
        return view;
    }
    public void intswipe(final View v) {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();



                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có chắc muốn xóa không");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               datahdtct.remove(datahdtct.get(position));
                               adapterHDCT.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        adapterHDCT.notifyDataSetChanged();
                        break;


                }


            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.Do))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                        .create()
                        .decorate();



                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcvhdct);
    }

    // check ma sach lay duoc vi tri sach do
    //lấy dataHDCT vòng lặp for đếm số lượng trong danh sách nếu mà mã sách trùng với spinner mà mình nhấp vào
   // sẽ lấy được vị trí của đối tượng đó
    public int checkListMaSach(ArrayList<HOADONCHITIET> lsHD, String maSach) {
        int pos = -1;
        for (int i = 0; i < lsHD.size(); i++) {
            HOADONCHITIET hd = lsHD.get(i);
            if (hd.getMasach().getMasach().equalsIgnoreCase(maSach)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public ArrayList<SACH> getAllTL() {
        datasach = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("Sach");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    datasach.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        SACH sach = next.getValue(SACH.class);
                        datasach.add(sach);
                    }
                    arrayAdapterTL.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return datasach;
    }

    public void ShowAdapterTL() {
        daoSach = new DAOSach(getActivity());
        datasach = new ArrayList<>();
        datasach.clear();
        datasach.addAll(getAllTL());
        arrayAdapterTL = new ArrayAdapter<SACH>(getActivity(), android.R.layout.simple_spinner_item, datasach);
        arrayAdapterTL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_theloai.setAdapter(arrayAdapterTL);


    }


    public int checkpositionspinenr(String str) {

        for (int i = 0; i < datasach.size(); i++) {
            if (str.equals(datasach.get(i).getMasach())) {
                return i;
            }
        }
        return 0;
    }


}
