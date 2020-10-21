package com.example.asmduanmau.FrameLayout;

import android.app.AlertDialog;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmduanmau.adapter.AdapterSach;
import com.example.asmduanmau.DAOFireBase.DAOSach;
import com.example.asmduanmau.Dialog.Bottom_Sheft_Add_Sach;
import com.example.asmduanmau.Dialog.Bottom_Sheft_Update_Sach;
import com.example.asmduanmau.Model.SACH;
import com.example.asmduanmau.Model.THELOAISACH;
import com.example.asmduanmau.R;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class framelayout_book extends Fragment {

public static RecyclerView recysach;
public static AdapterSach adapterSach;
public static ArrayList<SACH> datasach;
ArrayList<THELOAISACH> datatheloaisach;
public static DAOSach daoSach;

    public framelayout_book() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelayout_book, container,false);
        recysach=view.findViewById(R.id.recysach);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recysach.setHasFixedSize(true);
        recysach.setLayoutManager(layoutManager);
        datasach=new ArrayList<>();
        daoSach = new DAOSach(getActivity());
        datasach=daoSach.getAll();
        adapterSach=new AdapterSach(getActivity(),datasach);
        recysach.setAdapter(adapterSach);
        intswipe(view);

        return view;
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

                Bundle argss = new Bundle();
                argss.putString("MaSACH", datasach.get(position).getMasach() + "");
                argss.putString("MATL", datasach.get(position).getMatheloai());
                argss.putString("TIEUDE", datasach.get(position).getTieude() + "");
                argss.putString("TACGIA", datasach.get(position).getTacgia());
                argss.putString("NXB", datasach.get(position).getNxb() + "");
                argss.putDouble("GIA", Double.parseDouble(datasach.get(position).getGiabia() + ""));
                argss.putInt("SOLUONG", datasach.get(position).getSoluong());

                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có chắc muốn xóa không");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String masach = datasach.get(position).getMasach();
                                String maloai = datasach.get(position).getMatheloai();
                                String tieude = datasach.get(position).getTieude();
                                String tacgia = datasach.get(position).getTacgia();
                                String nxb = datasach.get(position).getNxb();
                                double gia = datasach.get(position).getGiabia();
                                int soluong = datasach.get(position).getSoluong();

                                datasach = new ArrayList<>();
                                daoSach = new DAOSach(getContext());
                                SACH sach = new SACH(masach, maloai, tieude, tacgia,nxb,gia,soluong);
                                daoSach.delete(sach);
                                datasach = daoSach.getAll();
                                adapterSach = new AdapterSach(getActivity(), datasach);
                                recysach.setAdapter(adapterSach);
                                adapterSach.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datasach = new ArrayList<>();
                                daoSach = new DAOSach(getContext());
                                datasach = daoSach.getAll();
                                adapterSach = new AdapterSach(getActivity(), datasach);
                                recysach.setAdapter(adapterSach);
                                adapterSach.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        adapterSach.notifyDataSetChanged();
                        break;
                    case ItemTouchHelper.RIGHT:


                        Bottom_Sheft_Update_Sach bottom_sheft_update_sach = new Bottom_Sheft_Update_Sach();
                        bottom_sheft_update_sach.setArguments(argss);
                        bottom_sheft_update_sach.show(getActivity().getSupportFragmentManager(), bottom_sheft_update_sach.getTag());

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
        itemTouchHelper.attachToRecyclerView(recysach);
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
            Bottom_Sheft_Add_Sach sheft_add_sach = new Bottom_Sheft_Add_Sach();
            sheft_add_sach.show(getFragmentManager(),"TAG");
        }
        return super.onOptionsItemSelected(item);
    }
}
