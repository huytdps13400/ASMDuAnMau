package com.example.asmduanmau.DAOFireBase;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.asmduanmau.FrameLayout.framelayout_book;
import com.example.asmduanmau.Model.SACH;
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

import static com.example.asmduanmau.Dialog.Bottom_Sheft_Add_Sach.datasachs;
import static com.example.asmduanmau.FrameLayout.framelayout_book.adapterSach;



public class DAOSach {
    Context context;

    DatabaseReference mRef;
    String key;
    framelayout_book framelayout_book;

    public DAOSach(Context context) {
        this.context = context;

        this.mRef= FirebaseDatabase.getInstance().getReference("Sach");
    }

    public DAOSach(Context context, com.example.asmduanmau.FrameLayout.framelayout_book framelayout_book) {
        this.context = context;
        this.framelayout_book = framelayout_book;
        this.mRef= FirebaseDatabase.getInstance().getReference("Sach");
    }

    public ArrayList<SACH> getAll() {
        final ArrayList<SACH> datasach = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    datasach.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        SACH sach = next.getValue(SACH.class);
                        datasach.add(sach);
                    }
                   framelayout_book.adapterSach.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return datasach;

    }
//    boolean matrung = false;
//    public boolean xetmatrung(final String masach, final SACH item){
//
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                SACH sach = null;
//                if (snapshot.exists()) {
//
//                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
//                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
//
//                    while (iterator.hasNext()) {
//                        DataSnapshot next = (DataSnapshot) iterator.next();
//                        sach = next.getValue(SACH.class);
//
//
//                    }
//                    if (sach.getMasach().equalsIgnoreCase(masach)) {
//
//                        matrung=true;
//                        }
//
//
//                        if (matrung==true){
//
//                            Toast.makeText(context, "Mã đã tồn tại", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                      else  if (matrung==false){
//                            key = mRef.push().getKey();
//                            //insert theo child mã key setvalue theo item
//                            mRef.child(key).setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(context, "Insert Thành Công", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(context, "Insert Thất Bại", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return matrung;
//    }
    public void insert(SACH item){
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
public ArrayList<SACH> GetMaTrung() {

    mRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                datasachs.clear();
                Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    SACH sach = next.getValue(SACH.class);
                    datasachs.add(sach);
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    return datasachs;

}
    public boolean update(final SACH item){
       mRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   if(dataSnapshot.child("masach").getValue(String.class).equalsIgnoreCase(item.getMasach())){
                       key=dataSnapshot.getKey();
                       mRef.child(key).setValue(item);
                       Toast.makeText(context, "Update Thành Công", Toast.LENGTH_SHORT).show();
                       framelayout_book.adapterSach.notifyDataSetChanged();

                   }
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        framelayout_book.adapterSach.notifyDataSetChanged();
       return true;
    }
    public void delete(final SACH item){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("masach").getValue(String.class).equalsIgnoreCase(item.getMasach())){
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

}
