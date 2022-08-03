package com.example.easycare;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easycare.introScreen.IntroActivity;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView ivTop,ivHeart,ivBottom;
    GifImageView ivBeat;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay=300;
    Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivTop=findViewById(R.id.iv_top);
        ivHeart=findViewById(R.id.iv_heart);
        ivBeat=findViewById(R.id.iv_beat);
        ivBottom=findViewById(R.id.iv_bottom);
        textView=findViewById(R.id.text_view);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animation1= AnimationUtils.loadAnimation(this,R.anim.top_wave);

        ivTop.setAnimation(animation1);

        ObjectAnimator objectAnimator=ObjectAnimator.ofPropertyValuesHolder(ivHeart, PropertyValuesHolder.ofFloat("scaleX",1.2f),PropertyValuesHolder.ofFloat("scaleY",1.2f));

        objectAnimator.setDuration(500);

        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);

        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        objectAnimator.start();

        animatText("Easy Care");


        Animation animation2= AnimationUtils.loadAnimation(this,R.anim.bottom_wave);

        ivBottom.setAnimation(animation2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this, IntroActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        },6000);


    }


    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            textView.setText(charSequence.subSequence(0,index++));
            if(index<=charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };


    public void animatText(CharSequence cs){

        charSequence=cs;
        index=0;
        textView.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);

    }

}