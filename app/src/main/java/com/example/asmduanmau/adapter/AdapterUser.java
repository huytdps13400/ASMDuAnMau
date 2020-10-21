package com.example.asmduanmau.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.Model.USER;
import com.example.asmduanmau.R;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.TabLoaiSachHolder> {
    Activity context;
    ArrayList<USER> datauser;

    public AdapterUser(Activity context, ArrayList<USER> datauser) {
        this.context = context;
        this.datauser = datauser;
    }

    @NonNull
    @Override
    public TabLoaiSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.itemuser,parent,false);

        return new TabLoaiSachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabLoaiSachHolder holder, int position) {
        holder.txtmaloai.setText( datauser.get(position).getPhone());
        holder.txttenloaisach.setText(datauser.get(position).getHoten());
        ;
    }


    @Override
    public int getItemCount() {
        return datauser.size();
    }
    public static class TabLoaiSachHolder extends RecyclerView.ViewHolder{
        public TextView txtmaloai,txttenloaisach,txtmota,txtvitri;
        public ImageView imghinhdep;
        public TabLoaiSachHolder(@NonNull View itemView) {

            super(itemView);
            txtmaloai = itemView.findViewById(R.id.txthoten);
            txttenloaisach = itemView.findViewById(R.id.txtsdt);


        }
    }
}
