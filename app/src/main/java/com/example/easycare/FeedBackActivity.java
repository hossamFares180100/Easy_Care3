package com.example.easycare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.hsalf.smileyrating.SmileyRating;

public class FeedBackActivity extends AppCompatActivity {

    SmileyRating smiley;
    TextInputLayout feed;
    MaterialRippleLayout send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        smiley = (SmileyRating) findViewById(R.id.smile_rating);
        feed = findViewById(R.id.feed);
        send = findViewById(R.id.send_feedback);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(!feed.getEditText().getText().toString().equals("")){
                        SmileyRating.Type type = smiley.getSelectedSmiley();
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"hodashimo708@gmail.com"});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "\n \n" +"feedback rating = "+type+"\n\n\n"+"user feedback : \n"+feed.getEditText().getText().toString());
                        try{
                            startActivity(Intent.createChooser(emailIntent,"Send mail..."));
                            finish();
                        }catch (Exception e){
                            Toast.makeText(FeedBackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
            }
        });




    }
}