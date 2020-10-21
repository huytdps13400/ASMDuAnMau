package com.example.asmduanmau.FrameLayout;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.adapter.AdapterLoaiSach;
import com.example.asmduanmau.DAOFireBase.DAOLoaiSach;
import com.example.asmduanmau.Dialog.Bottom_Sheft_Add_TheLoaiSach;
import com.example.asmduanmau.Dialog.Bottom_Sheft_Update_TheLoaiSach;
import com.example.asmduanmau.Model.THELOAISACH;
import com.example.asmduanmau.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class framelayout_theloai extends Fragment {
    public static AdapterLoaiSach adapterLoaiSach;
    public static ArrayList<THELOAISACH> datatheloai;
    DAOLoaiSach daoLoaiSach;
    public static RecyclerView recytheloai;
    public static DatabaseReference mRef;
    EditText tvsearch;
    ArrayList<THELOAISACH> datatheloais;
    public framelayout_theloai() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelayout_theloai, container, false);
        recytheloai = view.findViewById(R.id.recytheloai);
        tvsearch = view.findViewById(R.id.tvsearch);

        mRef = FirebaseDatabase.getInstance().getReference().child("TheLoaiSach");
        datatheloai = new ArrayList<>();
        daoLoaiSach = new DAOLoaiSach(getActivity());
        datatheloai = daoLoaiSach.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recytheloai.setHasFixedSize(true);
        recytheloai.setLayoutManager(layoutManager);
        adapterLoaiSach = new AdapterLoaiSach(getActivity(), datatheloai);
        recytheloai.setAdapter(adapterLoaiSach);
        intswipe(view);
        tvsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;

    }
    public void filter(String text){
        datatheloais = new ArrayList<>();
        daoLoaiSach =new DAOLoaiSach(getActivity());
        datatheloai=daoLoaiSach.getAll();
        for(THELOAISACH item: datatheloai){
            if(item.getMatheloai().toLowerCase().contains(text.toLowerCase())){
                datatheloais.add(item);
            }
        }
        adapterLoaiSach.search(datatheloais);
        adapterLoaiSach.notifyDataSetChanged();
    }
    @Override
    public void onResume() {

        adapterLoaiSach.notifyDataSetChanged();
        super.onResume();
    }

    public void intswipe(final View v) {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                Bundle args = new Bundle();
                args.putString("MaTL", datatheloai.get(position).getMatheloai() + "");
                args.putString("TenTL", datatheloai.get(position).getTentheloai() + "");
                args.putString("MoTa", datatheloai.get(position).getMota() + "");
                args.putInt("ViTri", datatheloai.get(position).getVitri());

                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có chắc muốn xóa không");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String maloai = datatheloai.get(position).getMatheloai();
                                String tenloai = datatheloai.get(position).getTentheloai();
                                String mota = datatheloai.get(position).getMota();
                                int vitri = datatheloai.get(position).getVitri();
                                datatheloai = new ArrayList<>();
                                daoLoaiSach = new DAOLoaiSach(getContext());
                                THELOAISACH theloaisach = new THELOAISACH(maloai, tenloai, mota, vitri);
                                daoLoaiSach.delete(theloaisach);
                                adapterLoaiSach = new AdapterLoaiSach(getActivity(), datatheloai);
                                recytheloai.setAdapter(adapterLoaiSach);
                                adapterLoaiSach.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datatheloai = new ArrayList<>();
                                daoLoaiSach = new DAOLoaiSach(getContext());
                                datatheloai = daoLoaiSach.getAll();
                                adapterLoaiSach = new AdapterLoaiSach(getActivity(), datatheloai);
                                recytheloai.setAdapter(adapterLoaiSach);
                                adapterLoaiSach.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        onResume();
                        break;
                    case ItemTouchHelper.RIGHT:
                        datatheloai = new ArrayList<>();

                        Bottom_Sheft_Update_TheLoaiSach bottom_sheft_update_theLoaiSach = new Bottom_Sheft_Update_TheLoaiSach();
                        bottom_sheft_update_theLoaiSach.setArguments(args);
                        bottom_sheft_update_theLoaiSach.show(getActivity().getSupportFragmentManager(), bottom_sheft_update_theLoaiSach.getTag());

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

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.Xanh))
                        .addSwipeRightActionIcon(R.drawable.ic_sua)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recytheloai);
    }


//    @Override
//    public void onResume() {
//
//        adapterLoaiSach.notifyDataSetChanged();
//        super.onResume();
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.icontoolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.add) {
            Bottom_Sheft_Add_TheLoaiSach add_theLoaiSach = new Bottom_Sheft_Add_TheLoaiSach();
            add_theLoaiSach.show(getFragmentManager(), "TAG");
        }
        return super.onOptionsItemSelected(item);
    }


    private void removeView(View view) {

        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }


}
