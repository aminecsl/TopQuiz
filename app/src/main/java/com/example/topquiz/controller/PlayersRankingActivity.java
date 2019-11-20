package com.example.topquiz.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.topquiz.R;
import com.example.topquiz.TinyDB;
import com.example.topquiz.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayersRankingActivity extends AppCompatActivity {

    private Button mSortByScoreBtn;
    private Button mSortByNameBtn;

    private ArrayList<User> mRankingList;
    private UserRepository mUserRepository;

    // FOR DESIGN
    @BindView(R.id.activity_players_ranking_recycler_view) RecyclerView recyclerView; // 1 - Declare RecyclerView

    //FOR DATA
    //private Disposable disposable;
    // 2 - Declare list of users (GithubUser) & Adapter
    private PlayerAdapter adapter;


    @Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_ranking);
        //View view = inflater.inflate(R.layout.activity_players_ranking, container, false);
        ButterKnife.bind(this);



        mSortByScoreBtn = (Button) findViewById(R.id.activity_players_ranking_sortByScore_btn);
        mSortByNameBtn = (Button) findViewById(R.id.activity_players_ranking_sortByName_btn);

        TinyDB mTinyDB = new TinyDB(this);
        mUserRepository = new UserRepository(mTinyDB);
        mRankingList = mUserRepository.getPlayersList();

        this.configureRecyclerView(); // - 4 Call during UI creation

        //setTextOnView();
        mSortByScoreBtn.setEnabled(false);

        mSortByScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayByScore();
                mSortByScoreBtn.setEnabled(false);
                mSortByNameBtn.setEnabled(true);
            }
        });

        mSortByNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayByName();
                mSortByNameBtn.setEnabled(false);
                mSortByScoreBtn.setEnabled(true);
            }
        });

    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // 3.2 - Create adapter passing the list of users
        this.adapter = new PlayerAdapter(this.mRankingList);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void displayByScore(){
        mRankingList.sort((a, b) -> a.getUserScore() > b.getUserScore()?-1:1);
        this.adapter.refreshData(mRankingList);

    }

    private void displayByName(){
        mRankingList.sort((a, b) -> a.getFirstName().compareTo(b.getFirstName()));
        this.adapter.refreshData(mRankingList);

    }

}
