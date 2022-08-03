package com.example.easycare.tipsClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycare.R;
import com.example.easycare.TipsHome;

import java.util.ArrayList;

public class RecycleViewAdapter2 extends RecyclerView.Adapter<RecycleViewAdapter2.RecycleViewHolder2> {


    ArrayList<TipsHome> list;
    Context context;

    public RecycleViewAdapter2(ArrayList<TipsHome> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_tips, viewGroup, false);
        return new RecycleViewHolder2(v);
    }

    @Override
    public void onBindViewHolder( RecycleViewHolder2 holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvDesc.setText(list.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecycleViewHolder2 extends RecyclerView.ViewHolder {
        public ImageView imgArticle;
        public TextView tvTitle,tvDesc;
        public Button btReadMore;

        public RecycleViewHolder2(@NonNull View itemView) {
            super(itemView);

            imgArticle  = itemView.findViewById(R.id.imgArticle);
            tvTitle     = itemView.findViewById(R.id.tvTitle);
            tvDesc      = itemView.findViewById(R.id.tvDesc);
        }
    }
}
