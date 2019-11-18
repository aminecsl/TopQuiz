package com.example.topquiz.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.topquiz.R;
import com.example.topquiz.TinyDB;
import com.example.topquiz.model.User;
import com.example.topquiz.view.PlayerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayersRankingActivity extends AppCompatActivity {

    private TextView mTop1Text;
    private TextView mTop2Text;
    private TextView mTop3Text;
    private TextView mTop4Text;
    private TextView mTop5Text;
    private Button mSortByScoreBtn;
    private Button mSortByNameBtn;

    private ArrayList<User> mRankingList;
    private UserRepository mUserRepository;

    // FOR DESIGN
    @BindView(R.id.activity_players_ranking_recycler_view) RecyclerView recyclerView; // 1 - Declare RecyclerView

    //FOR DATA
    //private Disposable disposable;
    // 2 - Declare list of users (GithubUser) & Adapter
    private List<User> mPlayers;
    private PlayerAdapter adapter;

    public PlayersRankingActivity() { }


    @Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_ranking);
        //View view = inflater.inflate(R.layout.activity_players_ranking, container, false);
        //ButterKnife.bind(this, view);
        this.configureRecyclerView(); // - 4 Call during UI creation
        this.executeHttpRequestWithRetrofit(); // 5 - Execute stream after UI creation
        //return view;


        /*mTop1Text = (TextView) findViewById(R.id.activity_players_ranking_top1_txt);
        mTop2Text = (TextView) findViewById(R.id.activity_players_ranking_top2_txt);
        mTop3Text = (TextView) findViewById(R.id.activity_players_ranking_top3_txt);
        mTop4Text = (TextView) findViewById(R.id.activity_players_ranking_top4_txt);
        mTop5Text = (TextView) findViewById(R.id.activity_players_ranking_top5_txt);*/
        mSortByScoreBtn = (Button) findViewById(R.id.activity_players_ranking_sortByScore_btn);
        mSortByNameBtn = (Button) findViewById(R.id.activity_players_ranking_sortByName_btn);

        TinyDB mTinyDB = new TinyDB(this);
        mUserRepository = new UserRepository(mTinyDB);
        mRankingList = mUserRepository.getPlayersList();

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
        // 3.1 - Reset list
        this.mPlayers = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.adapter = new PlayerAdapter(this.mPlayers);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.mRankingList = mUserRepository.getPlayersList();
        updateUI(mRankingList);
            /*@Override
            public void onNext(List<User> users) {
                // 6 - Update RecyclerView after getting results from Github API
                updateUI(users);
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() { }
        });*/
    }

    /*private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        //this.disposeWhenDestroy();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<User> users){
        mPlayers.addAll(users);
        adapter.notifyDataSetChanged();
    }



    private void displayByScore(){
        mRankingList.sort((a, b) -> a.getUserScore() > b.getUserScore()?-1:1);
        //setTextOnView();
    }

    private void displayByName(){
        mRankingList.sort((a, b) -> a.getFirstName().compareTo(b.getFirstName()));
        //setTextOnView();
    }

    /*private void setTextOnView(){
        mTop1Text.setText(mRankingList.get(0).getFirstName() + " : " + mRankingList.get(0).getUserScore());
        mTop2Text.setText(mRankingList.get(1).getFirstName() + " : " + mRankingList.get(1).getUserScore());
        mTop3Text.setText(mRankingList.get(2).getFirstName() + " : " + mRankingList.get(2).getUserScore());
        mTop4Text.setText(mRankingList.get(3).getFirstName() + " : " + mRankingList.get(3).getUserScore());
        mTop5Text.setText(mRankingList.get(4).getFirstName() + " : " + mRankingList.get(4).getUserScore());
    }*/
}
