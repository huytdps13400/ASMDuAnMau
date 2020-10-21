package com.example.asmduanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.asmduanmau.Model.HOADONCHITIET;
import com.example.asmduanmau.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterShow extends BaseAdapter {
    Context context;
    ArrayList<HOADONCHITIET> list;

    public AdapterShow(Context context, ArrayList<HOADONCHITIET> list) {
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        TextView ten, sl, gia;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        ViewHolder holder;
        HOADONCHITIET hd = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.one_sach_hdct, null);
            holder.ten = convertView.findViewById(R.id.lvTenSachHDCT);
            holder.sl = convertView.findViewById(R.id.lvSLSachHDCT);
            holder.gia = convertView.findViewById(R.id.lvGiaSachHDCT);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        holder.ten.setText(hd.getMasach().getTieude());
        holder.gia.setText(decimalFormat.format(hd.getMasach().getGiabia()));
        holder.sl.setText(hd.getSoluongmua()+"");
        return convertView;
    }
}
