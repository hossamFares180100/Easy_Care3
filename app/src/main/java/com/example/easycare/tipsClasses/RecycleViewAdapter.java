package com.example.easycare.tipsClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycare.R;
import com.example.easycare.TipsHome;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewHolder> {

    ArrayList<TipsHome>tips;
    Context context;
    RecycleViewAdapterListener listener;

    public RecycleViewAdapter(ArrayList<TipsHome> tips,RecycleViewAdapterListener listener) {

        this.tips = tips;
        this.listener=listener;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecycleViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

        //LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context,R.anim.layoutanim);
        TipsHome tipHelper= tips.get(position);
        holder.desc.setText(tipHelper.getDesc());




    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public interface ListItemClickListener {
        void onClick(int clickedItemIndex);
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder  {
        TextView desc;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);


            desc = itemView.findViewById(R.id.tip_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick();
                }
            });


        }

    }
}
