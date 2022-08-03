package com.example.easycare.tipsClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.easycare.R;
import com.example.easycare.TipsHome;
import com.example.easycare.tipsClasses.GetTips;
import com.example.easycare.tipsClasses.RecycleViewAdapter2;

import java.util.ArrayList;

public class TipActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        ArrayList<TipsHome> helpers=new ArrayList<>();


        final RecyclerView tips;
        tips=findViewById(R.id.rv_tips2);

        tips.setHasFixedSize(true);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        tips.setLayoutManager(layoutManager);
        RecycleViewAdapter2 mAdapter;



        TipsHome tipsHome=new TipsHome("kill ","Lorem Ipsum is simply dummy text of\n" +
                "the printing and typesetting industry.\n" +
                "Lorem Ipsum has been the industry's\n" +
                "standard dummy. . .");

        helpers.clear();
        helpers= GetTips.helpers;

            helpers.add(tipsHome);
            helpers.add(tipsHome);
            helpers.add(tipsHome);
            helpers.add(tipsHome);
            helpers.add(tipsHome);
            helpers.add(tipsHome);
            helpers.add(tipsHome);
            helpers.add(tipsHome);
            helpers.add(tipsHome);
            helpers.add(tipsHome);



        mAdapter = new RecycleViewAdapter2(helpers,this);
        mAdapter.notifyDataSetChanged();
        tips.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //tips.setPadding(130,100,130,100);

        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(tips);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolder = tips.findViewHolderForAdapterPosition(0);
                RelativeLayout rl1 = viewHolder.itemView.findViewById(R.id.rl1);
                rl1.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();
            }
        },100);

        tips.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View v = snapHelper.findSnapView(layoutManager);
                int pos = layoutManager.getPosition(v);

                RecyclerView.ViewHolder viewHolder = tips.findViewHolderForAdapterPosition(pos);
                RelativeLayout rl1 = viewHolder.itemView.findViewById(R.id.rl1);

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    rl1.animate().setDuration(350).scaleX(1).scaleY(1).setInterpolator(new AccelerateInterpolator()).start();
                }else{
                    rl1.animate().setDuration(350).scaleX(0.75f).scaleY(0.75f).setInterpolator(new AccelerateInterpolator()).start();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



    }
}