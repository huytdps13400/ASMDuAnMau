package com.example.asmduanmau.FrameLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.asmduanmau.R;
import com.example.asmduanmau.SliderViewPager.ModelItem;
import com.example.asmduanmau.SliderViewPager.ViewPagerAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


import java.util.ArrayList;
import java.util.List;

import static com.example.asmduanmau.MainActivity.navigationView;
import static com.example.asmduanmau.MainActivity.toolbar;


public class framelayout_home extends Fragment {
RelativeLayout layoutbook,layoutcategory,layoutbill,layoutstatistical;
    SliderView sliderView;
    private ViewPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelayout_home, container,false);
        layoutbook = view.findViewById(R.id.layoutbook);
        layoutcategory=view.findViewById(R.id.layoutcategory);
        layoutbill=view.findViewById(R.id.layoutbill);
        layoutstatistical=view.findViewById(R.id.layoutstatistical);
        sliderView = view.findViewById(R.id.imageSlider);


        adapter = new ViewPagerAdapter(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        layoutbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_book()).commit();
                navigationView.setCheckedItem(R.id.sach);
                toolbar.setTitle("BOOK");
            }
        });
        layoutcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_theloai()).commit();
                navigationView.setCheckedItem(R.id.theloai);
                toolbar.setTitle("CATEGORY");
            }
        });
        layoutbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_hoadon()).commit();
                navigationView.setCheckedItem(R.id.hoadon);
                toolbar.setTitle("BiLL");
            }
        });
        layoutstatistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fr_ly,new framelayout_statistical()).commit();
                navigationView.setCheckedItem(R.id.statistical);
                toolbar.setTitle("STATISTICAL");
            }
        });
        renewItems(view);
        removeLastItem(view);
        addNewItem(view);
        return view;
    }

    public void renewItems(View view) {
        List<ModelItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            ModelItem sliderItem = new ModelItem();
//            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageurl("https://tienganh.tv/wp-content/uploads/2018/05/dung-bo-phi-cuon-sach-ma-ban-thich-1.jpg");
            } else {
                sliderItem.setImageurl("https://kenh14cdn.com/thumb_w/640/2019/11/22/facebook-avatar-copy-6-157444100202999947852-crop-1574441153660153639946.jpg");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.ViewPagerAdapter(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteitem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        ModelItem sliderItem = new ModelItem();
//        sliderItem.setDescription("Re");
        sliderItem.setImageurl("https://tranhuyhoang.edu.vn/wp-content/uploads/2019/09/s%C3%A1ch-l%E1%BA%B7ng-nh%C3%ACn-cu%E1%BB%99c-s%E1%BB%91ng-review-s%C3%A1ch-1024x536-1024x585.jpg");
        adapter.addItem(sliderItem);
    }

    }

