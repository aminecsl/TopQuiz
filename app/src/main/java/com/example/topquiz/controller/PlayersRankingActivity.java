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
import java.util.Arrays;

public class PlayersRankingActivity extends AppCompatActivity {

    private TextView mTop1Text;
    private TextView mTop2Text;
    private TextView mTop3Text;
    private TextView mTop4Text;
    private TextView mTop5Text;

    /*On met les TextViews dans une liste pour itérer dedans à la méthode setTextOnView(). On pourra ainsi afficher nos joueurs même si on
    en a moins que de TextViews*/
    private ArrayList<TextView> mTopTextViews;

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

        //On met les TextViews dans une liste pour itérer dedans à la méthode setTextOnView()
        mTopTextViews = new ArrayList<>(Arrays.asList(mTop1Text, mTop2Text, mTop3Text, mTop4Text, mTop5Text));

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
        int mRankingListSize = mRankingList.size();
        for (int i=0; i<mRankingListSize; i++){
            mTopTextViews.get(i).setText(mRankingList.get(i).getFirstName() + " : " + mRankingList.get(i).getUserScore());
        }
    }

}
