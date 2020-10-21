package com.example.asmduanmau.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.Model.SACH;

import com.example.asmduanmau.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterSach extends RecyclerView.Adapter<AdapterSach.TabLoaiSachHolder> {
    Activity context;
    ArrayList<SACH> datasach;

    public AdapterSach(Activity context, ArrayList<SACH> datasach) {
        this.context = context;
        this.datasach = datasach;
    }

    @NonNull
    @Override
    public TabLoaiSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.itemsach,parent,false);

        return new TabLoaiSachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabLoaiSachHolder holder, int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
//        holder.txtmasach.setText("Mã Sách: \t"+datasach.get(position).getMasach());
        final SACH sach = datasach.get(position);
        holder.txttensach.setText(sach.getTieude());
        holder.txtmatheloai.setText("Thể Loại: \t"+sach.getMatheloai());
        holder.txttacgia.setText("Tác Giả: \t"+sach.getTacgia());
//        holder.txtnxb.setText("NXB: \t"+datasach.get(position).getNxb());
        holder.txtgiabia.setText("Giá Bìa: \t"+String.valueOf(decimalFormat.format(sach.getGiabia())));
//        holder.txtsoluong.setText("Số Lượng: \t"+String.valueOf(datasach.get(position).getSoluong()));
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.dulieusach);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView dialog_tensach = (TextView) myDialog.findViewById(R.id.dialog_tensach);
                TextView dialog_maloai = (TextView) myDialog.findViewById(R.id.dialog_maloai);
                final TextView dialog_tacgia = (TextView) myDialog.findViewById(R.id.dialog_tacgia);
                final TextView txtsoluong = (TextView) myDialog.findViewById(R.id.txtsoluong);
                final TextView txtmasach = (TextView) myDialog.findViewById(R.id.txtmasach);
                final TextView dialog1_ngayxuatban = (TextView) myDialog.findViewById(R.id.dialog_ngayxuatban);
                final TextView txtnxb = (TextView) myDialog.findViewById(R.id.txtnxb);
                dialog_tensach.setText("Mã Sách: \t"+sach.getMasach());
                dialog_maloai.setText("Thể Loại: \t"+sach.getMatheloai());
                dialog_tacgia.setText("Tên Sách: \t"+sach.getTieude());
                dialog1_ngayxuatban.setText("Tác Giả: \t"+sach.getTacgia());
                txtmasach.setText("NXB:\t"+sach.getNxb());
                txtnxb.setText("Giá:\t"+String.valueOf(decimalFormat.format(sach.getGiabia())));
                txtsoluong.setText("Số Lượng: \t"+String.valueOf(sach.getSoluong()));
                myDialog.show();
            }

        });

    }


    @Override
    public int getItemCount() {
        return datasach.size();
    }
    public static class TabLoaiSachHolder extends RecyclerView.ViewHolder{
        public TextView txtmasach,txttensach,txtmatheloai,txttacgia,txtnxb,txtgiabia,txtsoluong;
        public ImageView imghinhsach;
        CardView card_view;
        public TabLoaiSachHolder(@NonNull View itemView) {

            super(itemView);
            txtmasach = itemView.findViewById(R.id.txtmasach);
            txttensach = itemView.findViewById(R.id.txttensach);
            txtmatheloai = itemView.findViewById(R.id.txtmatheloai);
            txttacgia = itemView.findViewById(R.id.txttacgia);
//            txtnxb = itemView.findViewById(R.id.txtnxb);
            txtgiabia = itemView.findViewById(R.id.txtgiabia);
//            txtsoluong = itemView.findViewById(R.id.txtsoluong);
            imghinhsach = itemView.findViewById(R.id.imghinhsach);
            card_view= itemView.findViewById(R.id.card_view);

        }
    }
}
