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

import com.example.asmduanmau.adapter.AdapterUser;
import com.example.asmduanmau.DAOFireBase.DAOUser;
import com.example.asmduanmau.Dialog.Bottom_Sheft_Add_User;
import com.example.asmduanmau.Dialog.Bottom_Sheft_Update_User;
import com.example.asmduanmau.Model.USER;
import com.example.asmduanmau.R;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class framelayout_user extends Fragment {
    public static    RecyclerView recyuser;
    public static   ArrayList<USER> datauser;
    DAOUser daoUser;
  public static   AdapterUser adapterUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelayout_user,container,false);
        recyuser= view.findViewById(R.id.recyuser);
        datauser = new ArrayList<>();
        daoUser = new DAOUser(getActivity());
        datauser=daoUser.getAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyuser.setHasFixedSize(true);
        recyuser.setLayoutManager(layoutManager);
        adapterUser=new AdapterUser(getActivity(),datauser);
        recyuser.setAdapter(adapterUser);
        intswipe(view);
        return view;
    }
    @Override
    public void onResume() {

        adapterUser.notifyDataSetChanged();
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

                Bundle user = new Bundle();
                user.putString("username", datauser.get(position).getUsername() + "");
                user.putString("password", datauser.get(position).getPassword() + "");
                user.putString("hoten", datauser.get(position).getHoten() + "");
                user.putString("sdt", datauser.get(position).getPhone());

                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có chắc muốn xóa không");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String username = datauser.get(position).getUsername();
                                String pass = datauser.get(position).getPassword();
                                String hoten = datauser.get(position).getHoten();
                                String sdt = datauser.get(position).getPhone();
                                datauser = new ArrayList<>();
                                daoUser = new DAOUser(getContext());
                                USER user = new USER(username, pass, sdt, hoten);
                                daoUser.delete(user);
                                adapterUser = new AdapterUser(getActivity(), datauser);
                                recyuser.setAdapter(adapterUser);
                                adapterUser.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                datauser = new ArrayList<>();
                                daoUser = new DAOUser(getContext());
                                datauser = daoUser.getAll();
                                adapterUser = new AdapterUser(getActivity(), datauser);
                                recyuser.setAdapter(adapterUser);
                                adapterUser.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        onResume();
                        break;
                    case ItemTouchHelper.RIGHT:
                        datauser = new ArrayList<>();

                        Bottom_Sheft_Update_User bottom_sheft_update_user = new Bottom_Sheft_Update_User();
                        bottom_sheft_update_user.setArguments(user);
                        bottom_sheft_update_user.show(getActivity().getSupportFragmentManager(), bottom_sheft_update_user.getTag());

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
        itemTouchHelper.attachToRecyclerView(recyuser);
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
            Bottom_Sheft_Add_User add_user = new Bottom_Sheft_Add_User();
            add_user.show(getFragmentManager(),"TAG");
        }
        return super.onOptionsItemSelected(item);
    }

}
