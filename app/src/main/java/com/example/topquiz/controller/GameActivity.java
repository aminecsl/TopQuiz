package com.example.topquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquiz.R;
import com.example.topquiz.model.Question;
import com.example.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestionText;
    private Button mAnswerBtn1;
    private Button mAnswerBtn2;
    private Button mAnswerBtn3;
    private Button mAnswerBtn4;

    /*Notre activity va afficher coup après coup les questions stockées dans notre banque de question*/
    private QuestionBank mQuestionBank;
    //Variable que va stocker la question en cours d'affichage
    private Question mCurrentQuestion;

    //On va stocker le score de l'utilisateur et le nombre de questions qu'on souhaite poser
    private int mScore;
    private int mNumberOfQuestions;

    /*On prépare un identifiant pour le paramètre de la méthode intent.putExtra() qui permettra de transférer le score à une
      une autre activity*/
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionText = (TextView) findViewById(R.id.activity_game_question_txt);
        mAnswerBtn1 = (Button) findViewById(R.id.activity_game_answer_btn_1);
        mAnswerBtn2 = (Button) findViewById(R.id.activity_game_answer_btn_2);
        mAnswerBtn3 = (Button) findViewById(R.id.activity_game_answer_btn_3);
        mAnswerBtn4 = (Button) findViewById(R.id.activity_game_answer_btn_4);

        //On affecte des tag pour signifier que chaque bouton correspond à la réponse 0, 1, 2 et 3 (les index de nos réponses)
        mAnswerBtn1.setTag(0);
        mAnswerBtn2.setTag(1);
        mAnswerBtn3.setTag(2);
        mAnswerBtn4.setTag(3);

        mAnswerBtn1.setOnClickListener(this);
        mAnswerBtn2.setOnClickListener(this);
        mAnswerBtn3.setOnClickListener(this);
        mAnswerBtn4.setOnClickListener(this);

        //Au début de la partie, Le score est nul et le nombre de questions restant à poser est à 4 (par exemple)
        mScore = 0;
        mNumberOfQuestions = 4;

        /*Au lancement de l'activity on initialise notre banque de questions à partir de la méthode generateQuestions()*/
        mQuestionBank = this.generateQuestions();

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

        }

    @Override
    public void onClick(View v) {
        //Au clic on stock dans une variable le tag correspondant au bouton cliqué
        int responseIndex = (int) v.getTag();
        //On compare ensuite la valeur de ce tag avec celui attendu pour la réponse correcte
        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            Toast.makeText(this, "Correct :)", Toast.LENGTH_SHORT).show();
            //On incrémente le score si la réponse est correcte
            mScore++;
        }
        else {Toast.makeText(this, "Wrong :(", Toast.LENGTH_SHORT).show();}

        //On décrémente mNumberOfQuestions puis on vérifie si sa valeur est nulle. Auquel cas on arrête le jeu sinon on continue
        if (--mNumberOfQuestions == 0) {
            // Termine la partie et affiche une boîte de dialogue...
            endGame();
        } else {
            mCurrentQuestion = mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        }
    }

    private void displayQuestion(final Question question) {
        mQuestionText.setText(question.getQuestion());
        mAnswerBtn1.setText(question.getChoiceList().get(0));
        mAnswerBtn2.setText(question.getChoiceList().get(1));
        mAnswerBtn3.setText(question.getChoiceList().get(2));
        mAnswerBtn4.setText(question.getChoiceList().get(3));
    }

    public QuestionBank generateQuestions () {

        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        Question question4 = new Question("Comment s'appelle la femme du président français ?",
                Arrays.asList("Julie",
                        "Sophie",
                        "Brigitte",
                        "Nicolas"),
                2);

        Question question5 = new Question("What is the capital of Romania?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Who did the Mona Lisa paint?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("In which city is the composer Frédéric Chopin buried?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("What is the country top-level domain of Belgium?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));

    }

    private void endGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ferme l'activité en indiquant au système Android de conserver en mémoire la valeur de mScore
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();

    }
}
