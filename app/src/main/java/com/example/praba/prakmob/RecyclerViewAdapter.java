package com.example.praba.prakmob;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.praba.prakmob.model.DiaryList;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
//    private List<DiaryList> mData;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return OurData.title.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title, tv_diary;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.list_diary_title);
            tv_diary = itemView.findViewById(R.id.list_diary);
        }

        public void bindView(int position){
            tv_diary.setText(OurData.diary[position]);
            tv_title.setText(OurData.title[position]);
        }
    }
}
