package com.example.asmduanmau.DAOFireBase;

import android.content.Context;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.asmduanmau.FrameLayout.framelayout_theloai;
import com.example.asmduanmau.Model.THELOAISACH;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.asmduanmau.FrameLayout.framelayout_theloai.datatheloai;

public class DAOLoaiSach {
    Context context;

    DatabaseReference mRef;
    String key;
    framelayout_theloai framelayout_theloai;
    public DAOLoaiSach(Context context) {
        this.context = context;

        this.mRef= FirebaseDatabase.getInstance().getReference("TheLoaiSach");
    }

    public DAOLoaiSach(Context context, com.example.asmduanmau.FrameLayout.framelayout_theloai framelayout_theloai) {
        this.context = context;
        this.framelayout_theloai = framelayout_theloai;
        this.mRef= FirebaseDatabase.getInstance().getReference("TheLoaiSach");
    }
    public ArrayList<THELOAISACH> getAll() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    datatheloai.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        THELOAISACH theloaisach = next.getValue(THELOAISACH.class);
                        datatheloai.add(theloaisach);
                    }
                    framelayout_theloai.adapterLoaiSach.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return datatheloai;
    }
    public void insert(THELOAISACH item){
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
    public boolean update(final THELOAISACH item){
       mRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   if(dataSnapshot.child("matheloai").getValue(String.class).equalsIgnoreCase(item.getMatheloai())){
                       key=dataSnapshot.getKey();
                       mRef.child(key).setValue(item);
                       Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                       framelayout_theloai.adapterLoaiSach.notifyDataSetChanged();

                   }
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        framelayout_theloai.adapterLoaiSach.notifyDataSetChanged();
       return true;
    }
    public void delete(final THELOAISACH item){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("matheloai").getValue(String.class).equalsIgnoreCase(item.getMatheloai())){
                        key=dataSnapshot.getKey();
                        mRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Delete Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Delete Thất Bại", Toast.LENGTH_SHORT).show();

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
//    public ArrayList<String> getSpinner() {
//        final ArrayList<String> listspinner = new ArrayList<>();
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    listspinner.clear();
//                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
//                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
//                    while (iterator.hasNext()) {
//                        DataSnapshot next = (DataSnapshot) iterator.next();
//                        THELOAISACH theloaisach = next.getValue(THELOAISACH.class);
//                        String spinner = theloaisach.getTentheloai();
//                        listspinner.add(spinner);
//                    }
//                    adapterSach.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return listspinner;
//    }
}
