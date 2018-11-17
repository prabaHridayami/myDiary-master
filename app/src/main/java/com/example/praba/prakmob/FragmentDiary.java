package com.example.praba.prakmob;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.example.praba.prakmob.model.DiaryList;

import java.util.ArrayList;
import java.util.List;

public class FragmentDiary extends android.support.v4.app.Fragment{

    View v;

    List<DiaryList> diaryLists;
    public FragmentDiary() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.diary_fragment, container,false);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_id);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        RecyclerViewAdapter recyclerViewAdapter  = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(mGridLayoutManager);


        return v;
    }


}
