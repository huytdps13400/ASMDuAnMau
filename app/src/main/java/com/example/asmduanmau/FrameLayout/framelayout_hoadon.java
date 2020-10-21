package com.example.asmduanmau.FrameLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.adapter.AdapterHoaDon;
import com.example.asmduanmau.DAOFireBase.DAOHDCT;
import com.example.asmduanmau.DAOFireBase.DAOHoaDon;
import com.example.asmduanmau.Dialog.Bottom_Sheft_Add_HoaDon;
import com.example.asmduanmau.Model.HOADON;
import com.example.asmduanmau.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class framelayout_hoadon extends Fragment  {
    public static AdapterHoaDon adapterHoaDon;
    public static    ArrayList<HOADON> datahoadon;
    DAOHoaDon daoHoaDon;
    public static RecyclerView recyhoadon;
     DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelayout_hoadon,container,false);
        recyhoadon = view.findViewById(R.id.recyhoadon);



        mRef = FirebaseDatabase.getInstance().getReference().child("HoaDon");
        datahoadon = new ArrayList<>();
        daoHoaDon = new DAOHoaDon(getActivity());
        datahoadon = daoHoaDon.getAll();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyhoadon.setHasFixedSize(true);
        recyhoadon.setLayoutManager(layoutManager);
        adapterHoaDon=new AdapterHoaDon(getActivity(),datahoadon);
        recyhoadon.setAdapter(adapterHoaDon);
        intswipe(view);



        return view;

    }

    //    @Override
//    public void onResume() {
//
//
//        adapterHoaDon.notifyDataSetChanged();
//
//        super.onResume();
//    }
    public void intswipe(final View v) {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putString("Mahd",datahoadon.get(position).getMahoadon()+"");
                bundle.putString("ngay",datahoadon.get(position).getNgay()+"");


                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có chắc muốn xóa không ? Xóa Cả Hóa Đơn Chi Tiết");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String mahd = datahoadon.get(position).getMahoadon();
                                String ngay = datahoadon.get(position).getNgay();
                                DAOHDCT daohdct = new DAOHDCT(getActivity());
                                HOADON hoadon = new HOADON(mahd,ngay);
                                daoHoaDon.delete(hoadon);
                                daohdct.delete(hoadon.getMahoadon());
                                datahoadon.clear();
                                datahoadon.addAll(daoHoaDon.getAll());
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datahoadon = new ArrayList<>();
                                daoHoaDon = new DAOHoaDon(getContext());
                                datahoadon = daoHoaDon.getAll();
                                adapterHoaDon = new AdapterHoaDon(getActivity(), datahoadon);
                                recyhoadon.setAdapter(adapterHoaDon);
                                adapterHoaDon.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        adapterHoaDon.notifyDataSetChanged();
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
        itemTouchHelper.attachToRecyclerView(recyhoadon);
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.icontoolbar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.search){
            Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.add){
            Bottom_Sheft_Add_HoaDon add_hoaDon = new Bottom_Sheft_Add_HoaDon();
            add_hoaDon.show(getFragmentManager(),"TAG");
        }
        return super.onOptionsItemSelected(item);
    }

}
