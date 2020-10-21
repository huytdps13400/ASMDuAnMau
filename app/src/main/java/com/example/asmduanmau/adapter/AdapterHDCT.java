package com.example.asmduanmau.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.Model.HOADONCHITIET;
import com.example.asmduanmau.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterHDCT extends RecyclerView.Adapter<AdapterHDCT.TabLoaiSachHolder> {

    private ArrayList<HOADONCHITIET> datahdct;

    public AdapterHDCT(ArrayList<HOADONCHITIET> datahdct) {
        this.datahdct = datahdct;
    }

    @NonNull
    @Override
    public TabLoaiSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhdct,parent,false);

        return new TabLoaiSachHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TabLoaiSachHolder holder, int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        HOADONCHITIET hoadon = datahdct.get(position);

        holder.txtMaSach.setText("Mã sách: " + hoadon.getMasach().getMasach());
        holder.txtTenSach.setText("Số lượng: " + hoadon.getSoluongmua());
        holder.txtMaTheLoai.setText("Giá bìa: " + hoadon.getMasach().getGiabia() + " VND");
        holder.txtGiaBia.setText("Thành tiền: " + decimalFormat.format(hoadon.getSoluongmua() * hoadon.getMasach().getGiabia()) + " VND");
    }

    @Override
    public int getItemCount() {
        return datahdct.size();
    }

    static class TabLoaiSachHolder extends RecyclerView.ViewHolder{

        TextView txtMaSach,txtTenSach,txtMaTheLoai,txtTacGia,txtNXB,txtGiaBia,txtSoLuong;
        ImageView imgHinhSach;

        TabLoaiSachHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach       = itemView.findViewById(R.id.txtmasach);
            txtTenSach      = itemView.findViewById(R.id.txttensach);
            txtMaTheLoai    = itemView.findViewById(R.id.txtmatheloai);
//            txtTacGia       = itemView.findViewById(R.id.txttacgia);
//            txtNXB          = itemView.findViewById(R.id.txtnxb);
            txtGiaBia       = itemView.findViewById(R.id.txtgiabia);
//            txtSoLuong      = itemView.findViewById(R.id.txtsoluong);
            imgHinhSach     = itemView.findViewById(R.id.imghinhsach);
        }
    }
}
