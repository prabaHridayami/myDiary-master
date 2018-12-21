package com.example.praba.prakmob.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.praba.prakmob.Activity.EditDiaryActivity;
import com.example.praba.prakmob.R;
import com.example.praba.prakmob.model.DiaryShow;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<DiaryShow> mData;
    private OnClickListener onClickListener;

    public RecyclerViewAdapter(Context mContext, List<DiaryShow> diaryList) {
        this.mContext = mContext;
        this.mData = diaryList;
    }

    public interface OnClickListener{
        void onClick(int position);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final DiaryShow diary = mData.get(position);
        holder.id_diary = diary.getIdDiary();
        holder.tv_title.setText(diary.getTitle());
//        holder.iv_diary.setText(diary.getDiary());
        Glide.with(mContext)
                .load(diary.getImage())
                .into(holder.iv_diary);
        Log.wtf("getImage",diary.getImage());


        holder.iv_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), EditDiaryActivity.class);
                intent.putExtra("id_diary",holder.id_diary);
                Toast.makeText(mContext, ""+holder.id_diary+"  Title :"+diary.getTitle(), Toast.LENGTH_SHORT).show();

                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title,tv_diary;
        ImageView iv_diary;
        String id_diary;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.list_diary_title);
            iv_diary = itemView.findViewById(R.id.list_diary);

        }

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
