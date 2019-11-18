package com.example.topquiz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.topquiz.R;
import com.example.topquiz.TinyDB;
import com.example.topquiz.model.User;

import java.util.ArrayList;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_ranking);

        mTop1Text = (TextView) findViewById(R.id.activity_players_ranking_top1_txt);
        mTop2Text = (TextView) findViewById(R.id.activity_players_ranking_top2_txt);
        mTop3Text = (TextView) findViewById(R.id.activity_players_ranking_top3_txt);
        mTop4Text = (TextView) findViewById(R.id.activity_players_ranking_top4_txt);
        mTop5Text = (TextView) findViewById(R.id.activity_players_ranking_top5_txt);
        mSortByScoreBtn = (Button) findViewById(R.id.activity_players_ranking_sortByScore_btn);
        mSortByNameBtn = (Button) findViewById(R.id.activity_players_ranking_sortByName_btn);

        TinyDB mTinyDB = new TinyDB(this);
        mUserRepository = new UserRepository(mTinyDB);
        mRankingList = mUserRepository.getPlayersList();

        setTextOnView();
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

    private void displayByScore(){
        mRankingList.sort((a, b) -> a.getUserScore() > b.getUserScore()?-1:1);
        setTextOnView();
    }

    private void displayByName(){
        mRankingList.sort((a, b) -> a.getFirstName().compareTo(b.getFirstName()));
        setTextOnView();
    }

    private void setTextOnView(){
        mTop1Text.setText(mRankingList.get(0).getFirstName() + " : " + mRankingList.get(0).getUserScore());
        mTop2Text.setText(mRankingList.get(1).getFirstName() + " : " + mRankingList.get(1).getUserScore());
        mTop3Text.setText(mRankingList.get(2).getFirstName() + " : " + mRankingList.get(2).getUserScore());
        mTop4Text.setText(mRankingList.get(3).getFirstName() + " : " + mRankingList.get(3).getUserScore());
        mTop5Text.setText(mRankingList.get(4).getFirstName() + " : " + mRankingList.get(4).getUserScore());
    }
}
