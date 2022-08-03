package com.example.easycare.mapClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycare.R;

import java.util.ArrayList;

public class HospitalShowRecycleViewAdapter extends RecyclerView.Adapter<HospitalShowRecycleViewAdapter.HospitalShowViewHolder> {

    ArrayList<HospitalLocations>hospitalLocations;
    ButtonMapListener listener;

    public HospitalShowRecycleViewAdapter(ArrayList<HospitalLocations> hospitalLocations, ButtonMapListener listener) {
        this.hospitalLocations = hospitalLocations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HospitalShowRecycleViewAdapter.HospitalShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HospitalShowRecycleViewAdapter.HospitalShowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_hospital_layout,parent,false),listener);

    }

    @Override
    public void onBindViewHolder(@NonNull HospitalShowRecycleViewAdapter.HospitalShowViewHolder holder, int position) {
        HospitalLocations locations=hospitalLocations.get(position);
        holder.name.setText(locations.getName());
        holder.distance.setText(locations.getDistance()+" km");

    }

    @Override
    public int getItemCount() {
        return hospitalLocations.size();
    }

    public class HospitalShowViewHolder extends RecyclerView.ViewHolder {
        TextView name,distance;
        ImageView loc;
        public HospitalShowViewHolder(@NonNull View itemView, final ButtonMapListener listener) {
            super(itemView);
            name=itemView.findViewById(R.id.tv_name);
            distance=itemView.findViewById(R.id.tv_distance);
            loc=itemView.findViewById(R.id.btn_map);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(hospitalLocations.get(getAdapterPosition()));
                }
            });

            loc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(hospitalLocations.get(getAdapterPosition()));
                }
            });

        }
    }
}
