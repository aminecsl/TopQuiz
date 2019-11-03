package com.example.topquiz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.topquiz.R;

public class GameActivity extends AppCompatActivity {

    private TextView mQuestionText;
    private Button mAnswerBtn1;
    private Button mAnswerBtn2;
    private Button mAnswerBtn3;
    private Button mAnswerBtn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mAnswerBtn1 = (Button) findViewById(R.id.activity_game_answer_btn_1);
        mAnswerBtn1 = (Button) findViewById(R.id.activity_game_answer_btn_2);
        mAnswerBtn1 = (Button) findViewById(R.id.activity_game_answer_btn_3);
        mAnswerBtn1 = (Button) findViewById(R.id.activity_game_answer_btn_4);

    }
}
