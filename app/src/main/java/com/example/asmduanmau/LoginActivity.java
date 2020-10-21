package com.example.asmduanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.asmduanmau.Model.USER;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtusername, edtpassword;
    Button login;
    MaterialCheckBox checkbox;
    private FirebaseAuth mAuth;
    ArrayList<USER> listuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        login = findViewById(R.id.login);
        checkbox = findViewById(R.id.checkbox);
        mAuth = FirebaseAuth.getInstance();
        showdulieu();
        getAll();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
//                }, 3000);
                login();

            }
        });
    }
    public void login(){
        final String ussername = edtusername.getText().toString().trim();
        final String pass = edtpassword.getText().toString().trim();
        if (ussername.isEmpty() || pass.isEmpty()){
            Toast.makeText(LoginActivity.this, "Vui Lòng Nhập Đầy Đủ 2 Trường", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(ussername,pass).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //task.isSuccessfull là hàm thực thi bắt buộc của login Firebase
                            // Vì firebase phải đăng kí mới login được. Đây mình dùng nó cũng sẽ kiểm tra isSuccessfull bth trả về thành công
                            // nếu mà nó thất bại thì mình sẽ kiểm tra vòng lặp so sánh các giá trị username pass trong danh sách user
                            // so sánh xong chính sách trả về true nếu nó true thì Intent Toast Thành Công Ngược lại không thành công.
                            if(task.isSuccessful()){
                                RememberUser(ussername,pass,checkbox.isChecked());
                                Toast.makeText(LoginActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }else {
                                Boolean sure = false;
                                for (int i=0;i<listuser.size();i++){
                                    String tk = listuser.get(i).getUsername();
                                    String mk = listuser.get(i).getPassword();
                                    if(tk.matches(ussername) && mk.matches(pass)){
                                        sure = true;
                                        break;
                                    }
                                }
                                if (sure == true){
                                    RememberUser(ussername,pass,checkbox.isChecked());
                                    Toast.makeText(LoginActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                    i.putExtra("user",ussername);
                                    startActivity(i);

                                }else {
                                    Toast.makeText(LoginActivity.this, "Username hoặc Password không chính sách", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }
    private void showdulieu() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        // gọi phương thức sharepreferences ra với tên file USER_FILE
        boolean check = sharedPreferences.getBoolean("REMEMBER",false);
        if(check){
            String tennguoidung =sharedPreferences.getString("USERNAME","");
            String matkhau =sharedPreferences.getString("PASSWORD","");
            edtusername.setText(tennguoidung);
            edtpassword.setText(matkhau);
        }else {
            edtpassword.setText("");
            edtusername.setText("");
        }
        checkbox.setChecked(check);
    }
    public void RememberUser(String user,String pass,boolean checkk){
            SharedPreferences preferences=getSharedPreferences("USER_FILE",MODE_PRIVATE);
SharedPreferences.Editor editor=preferences.edit();
    if (checkk){
        editor.putString("USERNAME",user);
        editor.putString("PASSWORD",pass);
        editor.putBoolean("REMEMBER",checkk);
    }else {
        editor.clear();
    }
    editor.commit();
    }
    public ArrayList<USER> getAll() {
        listuser = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listuser.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        USER nd = next.getValue(USER.class);
                        listuser.add(nd);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Lấy người dùng thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
        return listuser;
    }
}
