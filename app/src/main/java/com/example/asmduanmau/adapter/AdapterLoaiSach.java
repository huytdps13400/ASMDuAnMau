package com.example.asmduanmau.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.Model.THELOAISACH;
import com.example.asmduanmau.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterLoaiSach extends RecyclerView.Adapter<AdapterLoaiSach.TabLoaiSachHolder>  {
    Activity context;
    ArrayList<THELOAISACH> dataloaisach;
    public AdapterLoaiSach(Activity context, ArrayList<THELOAISACH> dataloaisach) {
        this.context = context;
        this.dataloaisach = dataloaisach;
    }

    @NonNull
    @Override
    public TabLoaiSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.itemloaisach,parent,false);

        return new TabLoaiSachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabLoaiSachHolder holder, int position) {
        holder.txtmaloai.setText("Mã Thể Loại: \t"+dataloaisach.get(position).getMatheloai());
        holder.txttenloaisach.setText("Thể Loại: \t"+dataloaisach.get(position).getTentheloai());
        holder.txtmota.setText("Mô Tả: \t"+dataloaisach.get(position).getMota());
        holder.txtvitri.setText("Vị Trí: \t"+String.valueOf(dataloaisach.get(position).getVitri()));
    }


    @Override
    public int getItemCount() {
        return dataloaisach.size();

    }

    public void search(ArrayList<THELOAISACH> filllist){
        dataloaisach = filllist;
        notifyDataSetChanged();

    }







    public static class TabLoaiSachHolder extends RecyclerView.ViewHolder{
        public TextView txtmaloai,txttenloaisach,txtmota,txtvitri;
        public ImageView imghinhdep;
        public TabLoaiSachHolder(@NonNull View itemView) {

            super(itemView);
            txtmaloai = itemView.findViewById(R.id.txtmaloai);
            txttenloaisach = itemView.findViewById(R.id.txttenloaisach);
            txtmota = itemView.findViewById(R.id.txtmota);
            txtvitri = itemView.findViewById(R.id.txtvitri);
            imghinhdep = itemView.findViewById(R.id.imghinhdep);

        }
    }
}
