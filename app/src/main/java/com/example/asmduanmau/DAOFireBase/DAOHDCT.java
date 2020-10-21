package com.example.asmduanmau.DAOFireBase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.asmduanmau.FrameLayout.framelayout_hoadon;
import com.example.asmduanmau.Model.HOADONCHITIET;
import com.example.asmduanmau.Model.SACH;
import com.example.asmduanmau.callback.IHoaDonListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;



public class DAOHDCT {
    Context context;

    DatabaseReference mRef;
    String key;

    public DAOHDCT(Context context) {
        this.context = context;

        this.mRef= FirebaseDatabase.getInstance().getReference("HDCT");
    }

    // function này luôn luôn trả về size = 0
    // Code như này là sai rồi ý
    public void getAll(final IHoaDonListener callback) {
        final ArrayList<HOADONCHITIET> datahdct = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    datahdct.clear();

                    for (DataSnapshot data : snapshot.getChildren()){
                        HOADONCHITIET hdct = data.getValue(HOADONCHITIET.class);
                        datahdct.add(hdct);
                    }
                    callback.onSuccess(datahdct);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toString());
            }
        });
    }

    public void insert(HOADONCHITIET item){
        // push cây theo mã tự tạo
        // string key lấy mã push
    key = mRef.push().getKey();
    //insert theo child mã key setvalue theo item
    mRef.child(key).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(context, "Insert Thành Công", Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(context, "Insert Thất Bại", Toast.LENGTH_SHORT).show();
        }
    });
    }

    public boolean update(final HOADONCHITIET item){
       mRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   if(dataSnapshot.child("mahdct").getValue(String.class).equalsIgnoreCase(item.getMahdct())){
                       key=dataSnapshot.getKey();
                       mRef.child(key).setValue(item);
                       Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                       framelayout_hoadon.adapterHoaDon.notifyDataSetChanged();

                   }
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        framelayout_hoadon.adapterHoaDon.notifyDataSetChanged();
       return true;
    }
    public void delete(final String mhoadon){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("mahoadon").child("mahoadon").getValue(String.class).equalsIgnoreCase(mhoadon)){
                        key=dataSnapshot.getKey();
                        mRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Delete Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {


                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
