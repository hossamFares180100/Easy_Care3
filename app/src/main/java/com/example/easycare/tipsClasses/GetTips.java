package com.example.easycare.tipsClasses;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.easycare.TipsHome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetTips {

    public static FirebaseDatabase myTips;
    public static DatabaseReference reference=null;
    static ArrayList<TipsHome> helpers;



    public static void getTips()
    {

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


    }


}
