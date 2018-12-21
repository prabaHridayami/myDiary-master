package Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.praba.prakmob.R;
import com.example.praba.prakmob.Adapter.RecyclerViewAdapter;
import com.example.praba.prakmob.Activity.WritediaryActivity;
import com.example.praba.prakmob.api.ApiClient;
import com.example.praba.prakmob.api.ApiService;
import com.example.praba.prakmob.model.DiaryShow;

import java.util.ArrayList;
import java.util.List;

import Database.DbHelper;
import retrofit2.Call;
import retrofit2.Callback;

public class FragmentDiary extends android.support.v4.app.Fragment{
    ApiService service;
    SharedPreferences sharedPreferences;
    View v;
    FloatingActionButton addDiary;
    DiaryShow diaryShow;
    List<DiaryShow> diaryList1 = new ArrayList<>();
    List<DiaryShow> diaryList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.diary_fragment, container,false);
        recyclerView = v.findViewById(R.id.recycler_id);
        service = ApiClient.getService();

        addDiary = v.findViewById(R.id.addDiary);
        addDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),WritediaryActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        getDiary();
    }

    public void getDiary(){
        sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        final String id_user = sharedPreferences.getString("id","");
        service.viewbyuser(id_user).enqueue(new Callback<List<DiaryShow>>() {
            @Override
            public void onResponse(Call<List<DiaryShow>> call, retrofit2.Response<List<DiaryShow>> response) {
                Toast.makeText(getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                DbHelper dbHelper = new DbHelper(getActivity());
                dbHelper.deleteAllDiary(id_user);
                diaryList1 = response.body();
                for (DiaryShow diaryShow:diaryList1){
                    dbHelper.insertDiary(Integer.parseInt(diaryShow.getIdDiary()),diaryShow.getTitle(), diaryShow.getDiary(), diaryShow.getImage(),id_user);
                }

                diaryList.clear();
                diaryList.addAll(response.body());
                setGridLayout();
            }

            @Override
            public void onFailure(Call<List<DiaryShow>> call, Throwable t) {
                callContactLocal();

            }
        });
    }

    public void setGridLayout(){
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),diaryList);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(mGridLayoutManager);
    }

    private void callContactLocal(){
        String id_user = sharedPreferences.getString("id","");
        DbHelper dbHelper = new DbHelper(getActivity());
        diaryList = dbHelper.selectDiary(id_user);
        setGridLayout();
    }


}

