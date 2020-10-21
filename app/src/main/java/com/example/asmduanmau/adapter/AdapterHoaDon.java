package com.example.asmduanmau.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.asmduanmau.DAOFireBase.DAOHDCT;
import com.example.asmduanmau.DAOFireBase.DAOUser;

import com.example.asmduanmau.MainActivity;
import com.example.asmduanmau.Model.HOADON;
import com.example.asmduanmau.Model.HOADONCHITIET;
import com.example.asmduanmau.Model.USER;
import com.example.asmduanmau.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;


public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.TabLoaiSachHolder> {
    Activity context;
    ArrayList<HOADON> datahoadon;
    ArrayList<HOADONCHITIET> datahoadonchitiet = new ArrayList<>();
    ArrayList<USER> datauser = new ArrayList<>();
    DAOHDCT daohdct;
    DAOUser daoUser;
    AdapterShow adapterShow;

    public AdapterHoaDon(Activity context, ArrayList<HOADON> datahoadon) {
        this.context = context;
        this.datahoadon = datahoadon;
    }

    @NonNull
    @Override
    public TabLoaiSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.itemhoadon, parent, false);

        return new TabLoaiSachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabLoaiSachHolder holder, final int position) {
        holder.txtmahd.setText("Mã Hóa Đơn: \t" + datahoadon.get(position).getMahoadon());
        holder.txttenhoadon.setText("Ngày: \t" + datahoadon.get(position).getNgay());


        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.showhdct);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView dialog_tensach = (TextView) myDialog.findViewById(R.id.dialog_tensach);
                TextView dialog_maloai = (TextView) myDialog.findViewById(R.id.dialog_maloai);
                final TextView dialog_tacgia = (TextView) myDialog.findViewById(R.id.dialog_tacgia);
                final TextView dialog_ngayxuatban = (TextView) myDialog.findViewById(R.id.dialog_ngayxuatban);
                final ListView rcvshow = myDialog.findViewById(R.id.rcvshow);
                final TextView dialog_soluong = (TextView) myDialog.findViewById(R.id.dialog_soluong);
                dialog_tensach.setText(datahoadon.get(position).getNgay());
                dialog_maloai.setText(datahoadon.get(position).getMahoadon());
                final ArrayList<USER> listuser = new ArrayList<>();
                final String maKh = MainActivity.tenTk;

                FirebaseDatabase.getInstance().getReference("User").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listuser.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            USER hoadonchitiet = data.getValue(USER.class);
                            //Lọc theo mã hd
                            if (hoadonchitiet.getUsername().matches(maKh)) {
                                listuser.add(hoadonchitiet);
                                dialog_ngayxuatban.setText(hoadonchitiet.getHoten());
                                dialog_tacgia.setText(hoadonchitiet.getPhone());
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


//                for (int i = 0; i < datauser.size(); i++) {
//                    if (datauser.get(i).getUsername().matches(maKh)) {
//                        dialog_ngayxuatban.setText(datauser.get(i).getHoten());
//                        dialog_tacgia.setText(datauser.get(i).getPhone());
//                        break;
//                    }
//
//                }


//                daohdct= new DAOHDCT(context);
//                datahoadonchitiet = daohdct.getAllHDCT();
//                String maHD = datahoadon.get(position).getMahoadon();
//
//                int tong = 0;
//                for (int i = 0; i < datahoadonchitiet.size(); i++) {
//                    String ma = datahoadonchitiet.get(i).getMahoadon().getMahoadon();
//                    if (maHD.matches(ma)) {
//                        datahoadonchitiet.add(datahoadonchitiet.get(i));
//
//                    }
//                }


//                dialog_soluong.setText(tong + "");
//                datahoadonchitiet =   getAllHDCT();
                final ArrayList<HOADONCHITIET> list = new ArrayList<>();
                final String maHD = datahoadon.get(position).getMahoadon();
                final int tong = 0;
                FirebaseDatabase.getInstance().getReference("HDCT").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        int tong = 0;
                        for (DataSnapshot data : snapshot.getChildren()) {
                            HOADONCHITIET hoadonchitiet = data.getValue(HOADONCHITIET.class);
                            //Lọc theo mã hd
                            if (hoadonchitiet.getMahoadon().getMahoadon().matches(maHD)) {
                                list.add(hoadonchitiet);
                                tong += (hoadonchitiet.getSoluongmua()) * (hoadonchitiet.getMasach().getGiabia());
                            }
                        }
                        adapterShow = new AdapterShow(context, list);
                        rcvshow.setAdapter(adapterShow);
                        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        decimalFormat.applyPattern("#,###,###,###");
                        dialog_soluong.setText("Tổng Tiền: \t" + decimalFormat.format(tong));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                FirebaseDatabase.getInstance().getReference("HDCT")
//                        .child("mahoadon").orderByChild("mahoadon")
//                        .equalTo(maHD).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        list.clear();
//                        for(DataSnapshot data: snapshot.getChildren()){
//                            //parse data về class
//                            HOADONCHITIET hdct = data.getValue(HOADONCHITIET.class);
//                            list.add(hdct);
////                            Toast.makeText(context, list.get(0).getMahdct(), Toast.LENGTH_LONG).show();
//
//                        }
//                        adapterShow = new AdapterShow(context, list);
//                        rcvshow.setAdapter(adapterShow);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


//                dialog_tacgia.setText(data.get(position).getTacGia());
//                dialog_ngayxuatban.setText(data.get(position).getNXB());
//                dialog_giabia.setText(decimalFormat.format(data.get(position).getGiaBia()) + " $");
//                dialog_giabia.addTextChangedListener(onTextChangedListener());
//                dialog_soluong.setText(data.get(position).getSoLuong() + " quyển");

                myDialog.show();
            }

        });
    }


    @Override
    public int getItemCount() {
        return datahoadon.size();
    }

    public static class TabLoaiSachHolder extends RecyclerView.ViewHolder {
        public TextView txtmahd, txttenhoadon;
        public ImageView imghinhdep;
        public CardView card_view;


        public TabLoaiSachHolder(@NonNull View itemView) {

            super(itemView);
            txtmahd = itemView.findViewById(R.id.txtmahd);
            txttenhoadon = itemView.findViewById(R.id.txttenhoadon);
            imghinhdep = itemView.findViewById(R.id.imghinhdep);
            card_view = itemView.findViewById(R.id.card_view);

        }
    }

    public ArrayList<HOADONCHITIET> getAllHDCT() {
        datahoadonchitiet = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("HDCT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    datahoadonchitiet.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        HOADONCHITIET hdct = next.getValue(HOADONCHITIET.class);
                        datahoadonchitiet.add(hdct);


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return datahoadonchitiet;

    }

    public ArrayList<USER> getAll() {
        datauser = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    datauser.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        USER nd = next.getValue(USER.class);
                        datauser.add(nd);
                    }
                    adapterShow.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Lấy người dùng thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        return datauser;
    }

}
