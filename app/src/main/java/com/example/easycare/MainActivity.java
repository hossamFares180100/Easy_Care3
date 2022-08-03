package com.example.easycare;

import android.Manifest;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.easycare.mapClasses.NearbyHospitalMapActivity;
import com.example.easycare.notificationService.User;
import com.example.easycare.tipsClasses.GetTips;
import com.example.easycare.tipsClasses.RecycleViewAdapter;
import com.example.easycare.tipsClasses.RecycleViewAdapterListener;
import com.example.easycare.tipsClasses.TipActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecycleViewAdapterListener {

    Button map;
    private FirebaseAuth mAuth;
    public static String NODE_USER = "users";

    ImageView btFavorite,btAccount;
    ImageButton btHome;
    ArrayList<TipsHome>helpers=null;
    RecycleViewAdapter mAdapter;
    private Handler slidehandler=new Handler();
    RecyclerView tips;
    CardView hospitalFinder,CheckUp;
    private ViewPager2 viewPager2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2=findViewById(R.id.viewPagerImageSlider);
        List<SliderItem>sliderItems=new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.tips1));
        sliderItems.add(new SliderItem(R.drawable.tips2));
        sliderItems.add(new SliderItem(R.drawable.tips3));
        sliderItems.add(new SliderItem(R.drawable.tips4));
        sliderItems.add(new SliderItem(R.drawable.tips5));
        sliderItems.add(new SliderItem(R.drawable.tips6));
        sliderItems.add(new SliderItem(R.drawable.tips7));
        sliderItems.add(new SliderItem(R.drawable.tips8));
        sliderItems.add(new SliderItem(R.drawable.tips9));
        sliderItems.add(new SliderItem(R.drawable.tips10));
        sliderItems.add(new SliderItem(R.drawable.tips11));
        sliderItems.add(new SliderItem(R.drawable.tips12));
        sliderItems.add(new SliderItem(R.drawable.tips13));
        sliderItems.add(new SliderItem(R.drawable.tips14));
        sliderItems.add(new SliderItem(R.drawable.tips15));
        sliderItems.add(new SliderItem(R.drawable.tips16));
        sliderItems.add(new SliderItem(R.drawable.tips17));
        sliderItems.add(new SliderItem(R.drawable.tips18));
        sliderItems.add(new SliderItem(R.drawable.tips19));
        sliderItems.add(new SliderItem(R.drawable.tips20));
        sliderItems.add(new SliderItem(R.drawable.tips21));
        sliderItems.add(new SliderItem(R.drawable.tips22));
        sliderItems.add(new SliderItem(R.drawable.tips23));
        sliderItems.add(new SliderItem(R.drawable.tips24));
        sliderItems.add(new SliderItem(R.drawable.tips25));
        sliderItems.add(new SliderItem(R.drawable.tips26));
        sliderItems.add(new SliderItem(R.drawable.tips27));
        sliderItems.add(new SliderItem(R.drawable.tips28));
        sliderItems.add(new SliderItem(R.drawable.tips29));
        sliderItems.add(new SliderItem(R.drawable.tips30));
        sliderItems.add(new SliderItem(R.drawable.tips31));
        sliderItems.add(new SliderItem(R.drawable.tips32));
        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slidehandler.removeCallbacks(sliderRunnable);
                slidehandler.postDelayed(sliderRunnable,5000);
            }
        });



         FirebaseDatabase myTips;
         DatabaseReference reference=null;

         final ArrayList<TipsHome> helpers;
         GetTips.getTips();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

         hospitalFinder=findViewById(R.id.cardHome);
        CheckUp=findViewById(R.id.cardProfile);
        CheckUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n=new Intent(MainActivity.this,CheckUpActivity.class);
                startActivity(n);
            }
        });
         hospitalFinder.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent n=new Intent(MainActivity.this, NearbyHospitalMapActivity.class);
                 startActivity(n);

             }
         });

        /*helpers=new ArrayList<>();
        myTips= FirebaseDatabase.getInstance();

        reference=myTips.getReference("tips");
        tips=findViewById(R.id.rv_tips);
        tips.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        tips.setLayoutManager(layoutManager);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    TipsHome tipsHome=snapshot1.getValue(TipsHome.class);
                    helpers.add(tipsHome);
                }

                mAdapter=new RecycleViewAdapter(helpers,MainActivity.this);
                mAdapter.notifyDataSetChanged();
                tips.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });*/



      /*  map=findViewById(R.id.btn_map);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n =new Intent(MainActivity.this,NearbyHospitalMapActivity.class);
                startActivity(n);
            }
        });*/

            /*TipsHome tipHelper = new TipsHome("Hossam", "not kill kill kill kill");
            helpers.add(tipHelper);
            helpers.add(tipHelper);
            helpers.add(tipHelper);
            helpers.add(tipHelper);
            helpers.add(tipHelper);
            helpers.add(tipHelper);
            helpers.add(tipHelper);
            helpers.add(tipHelper);
            helpers.add(tipHelper);
            helpers.add(tipHelper);
        mAdapter = new RecycleViewAdapter(helpers,this);
        tips.setAdapter(mAdapter);
        //tips.setPadding(130,100,130,100);

        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(tips);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolder = tips.findViewHolderForAdapterPosition(0);
                RelativeLayout rl1 = viewHolder.itemView.findViewById(R.id.rl);
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
                RelativeLayout rl1 = viewHolder.itemView.findViewById(R.id.rl);

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
*/



        mAuth = FirebaseAuth.getInstance();


        FirebaseMessaging.getInstance().subscribeToTopic("updates").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                }else{
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if(task.isSuccessful()){
                            String token = task.getResult().getToken();
                            saveToken(token);
                        }else{

                        }

                    }
                });



    }


    private void saveToken(String token) {
        String email = mAuth.getCurrentUser().getEmail();
        User user = new User(email,token);
        DatabaseReference dpRef = FirebaseDatabase.getInstance().getReference(NODE_USER);
        dpRef.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
                else{
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()==null){
            Intent n = new Intent(MainActivity.this,SignUpActivity.class);
            n.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(n);
        }


    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClick() {
        Intent n=new Intent(this, TipActivity.class);
        startActivity(n);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private class MyTask extends AsyncTaskLoader<Object>
    {

        public MyTask(Context context) {
            super(context);
        }

        @Override
        public Object loadInBackground() {
             FirebaseDatabase myTips;
             DatabaseReference reference=null;
             final ArrayList<TipsHome> helpers;
            helpers=new ArrayList<>();
            helpers.clear();
            myTips= FirebaseDatabase.getInstance();

            reference=myTips.getReference("tips");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        TipsHome tipsHome=snapshot1.getValue(TipsHome.class);
                        helpers.add(tipsHome);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {


                }
            });


            return helpers;
        }
    }

    private Runnable sliderRunnable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slidehandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slidehandler.postDelayed(sliderRunnable,3000);

    }
}