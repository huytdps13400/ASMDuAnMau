package com.example.asmduanmau.DAOFireBase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.asmduanmau.FrameLayout.framelayout_user;
import com.example.asmduanmau.Model.USER;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.asmduanmau.FrameLayout.framelayout_user.datauser;


public class DAOUser {
    Context context;

    DatabaseReference mRef;
    String key;
    framelayout_user framelayout_user;
    public DAOUser(Context context) {
        this.context = context;

        this.mRef= FirebaseDatabase.getInstance().getReference("User");
    }

    public DAOUser(Context context, com.example.asmduanmau.FrameLayout.framelayout_user framelayout_user) {
        this.context = context;
        this.framelayout_user = framelayout_user;
        this.mRef= FirebaseDatabase.getInstance().getReference("User");
    }
    public ArrayList<USER> getAll() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    datauser.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        USER user = next.getValue(USER.class);
                        datauser.add(user);
                    }
                    framelayout_user.adapterUser.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return datauser;
    }
    public void insert(USER item){
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
    public boolean update(final USER item){
       mRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   if(dataSnapshot.child("username").getValue(String.class).equalsIgnoreCase(item.getUsername())){
                       key=dataSnapshot.getKey();
                       mRef.child(key).setValue(item);
                       Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                       framelayout_user.adapterUser.notifyDataSetChanged();

                   }
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        framelayout_user.adapterUser.notifyDataSetChanged();
       return true;
    }
    public void delete(final USER item){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("username").getValue(String.class).equalsIgnoreCase(item.getUsername())){
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
