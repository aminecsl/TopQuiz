package com.example.topquiz.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by Amine K. on 06/11/19.
 */
public class QuestionBank {

    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {

        mQuestionList = questionList;
        // On veut que chaque partie nous propose une banque de questions affichées dans un ordre différent
        Collections.shuffle(mQuestionList);

        mNextQuestionIndex = 0;

    }

    public Question getQuestion() {
        // Loop over the questions and return a new one at each call
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }

        // Please note the post-incrementation
        return mQuestionList.get(mNextQuestionIndex++);
    }
}
