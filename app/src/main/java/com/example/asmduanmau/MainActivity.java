package com.example.asmduanmau;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.asmduanmau.FrameLayout.framelayout_bestselling;
import com.example.asmduanmau.FrameLayout.framelayout_book;
import com.example.asmduanmau.FrameLayout.framelayout_hoadon;
import com.example.asmduanmau.FrameLayout.framelayout_home;
import com.example.asmduanmau.FrameLayout.framelayout_statistical;
import com.example.asmduanmau.FrameLayout.framelayout_theloai;
import com.example.asmduanmau.FrameLayout.framelayout_user;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
   public static Toolbar toolbar;
    FrameLayout fr_ly;
   public static NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    public static String tenTk = "";


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.Black));
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = setupdrawertoggle();
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black);
        Intent i = getIntent();
        tenTk = i.getStringExtra("user");
       if(savedInstanceState==null){
           navigationView.setCheckedItem(R.id.home);
           getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_home()).commit();
       }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_home()).commit();
                        break;
                    case R.id.sach:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_book()).commit();
                        break;
                    case R.id.theloai:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly, new framelayout_theloai()).commit();
                        break;
                    case R.id.hoadon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_hoadon()).commit();
                        break;
                    case R.id.topbook:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_bestselling()).commit();
                        break;
                    case R.id.statistical:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_statistical()).commit();
                        break;
                    case R.id.user:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_user()).commit();
                        break;
                    case R.id.changepassword:
                        Toast.makeText(MainActivity.this, "ChangePassword", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.exit:
                        Intent startmain = new Intent(Intent.ACTION_MAIN);
                        startmain.addCategory(Intent.CATEGORY_HOME);
                        startActivity(startmain);
                        finish();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_home()).commit();

                }
                item.setCheckable(true);
                setTitle(item.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
    public void init(){
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        fr_ly = findViewById(R.id.fr_ly);
        navigationView = findViewById(R.id.navigation_view);

    }
    private ActionBarDrawerToggle setupdrawertoggle(){
        return new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.Open,R.string.Close);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
