package com.example.praba.prakmob;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.praba.prakmob.model.Diary;
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
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DiaryShow diary = mData.get(position);
        holder.tv_title.setText(diary.getTitle());
        holder.tv_diary.setText(diary.getDiary());
        holder.bind(diary);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private OnClickListener onClickListener;
        TextView tv_title, tv_diary;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.list_diary_title);
            tv_diary = itemView.findViewById(R.id.list_diary);
            if (onClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        public void bind(DiaryShow diary){
            tv_title.setText(diary.getTitle());
            tv_diary.setText(diary.getDiary());
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
