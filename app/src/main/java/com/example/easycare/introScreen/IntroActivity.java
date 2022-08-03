package com.example.easycare.introScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycare.MainActivity;
import com.example.easycare.R;
import com.example.easycare.SignInActivity;
import com.example.easycare.SignUpActivity;
import com.google.android.material.tabs.TabLayout;
import com.spark.submitbutton.SubmitButton;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPager introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    SubmitButton btnGetStarted;
    Animation btnAnim;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);


        // ini views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);




        // when this activity is about to be launch we need to check if its openened before or not

        if (restorePrefData()) {
            Intent SignInActivity = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(SignInActivity);
            finish();
        }


        // fill list screen

        final List<ScreenItem> mList = new ArrayList<>();
        boolean fresh_food = mList.add(new ScreenItem(R.string.Check_Up_title, R.string.check_up_disc, R.drawable.ic_patient2));
        mList.add(new ScreenItem(R.string.hospital_finder_title, R.string.hospital_finder_disc, R.drawable.ic_hospital3));
        mList.add(new ScreenItem(R.string.pill_reminder_title, R.string.pill_reminder_disc, R.drawable.ic_pill_reminder2));
        mList.add(new ScreenItem(R.string.app_name, R.string.Easy_care_disc, R.drawable.ic_doctor));





        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPager(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager

        tabIndicator.setupWithViewPager(screenPager);

        // Get Started button click listener

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Thread background = new Thread() {
                    public void run() {

                        try {

                            sleep(4 * 1000); // غير في الرقم 5 لتغيير عدد الثواني .. ثواني الانتظار قبل الدخول للتطبيق

                            // بعد 5 ثواني نفذ التالي وهو الانتقال بنا الى الشاشة الرئيسية
                            Intent signInActivity = new Intent(getApplicationContext(), SignInActivity.class);
                            savePrefsData();
                            startActivity(signInActivity);



                            // اغلاق شاشة السبلاش بشكل كلشي بعد الانتقال
                            finish();

                        } catch (Exception e) {
                            Toast.makeText(IntroActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                background.start();



            }
        });



        // next button click Listner

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size() - 1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();
                }
            }
        });


        // skip button click listener
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });



        // tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {
                    loaddLastScreen();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.commit();
    }



    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);
    }

}