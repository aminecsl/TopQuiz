package com.example.topquiz.controller;

import com.example.topquiz.TinyDB;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topquiz.R;
import com.example.topquiz.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    //On donne un identifiant à une autre activity afin d'y faire référence dans certaines méthodes comme startActivityForResult()
    public static final int GAME_ACTIVITY_REQUEST_CODE = 99;

    /*Pour référencer les éléments graphiques, on commence par déclarer des variables qui leur seront associées :*/
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mRankingButton;

    /* On déclare qu'on aura une instance de la classe User qui permettra de lui affecter le mNameInput au clic sur le bouton*/
    private User mUser;

    /*La variable d'instance de la SharedPreferences API (sauvegarder des données dans la mémoire du device) et les clés des
      données qui m'intéressent*/
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    //La variable d'instance de TinyDB et La clé pour identifier notre liste de joueurs dans l'objet TinyDB
    public TinyDB mTinydb;
    //public static final String TINYDB_KEY_PLAYERS_LIST = "TINYDB_KEY_PLAYERS_LIST";

    private UserRepository mUserRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Trace pour monitorer l'état Created du cycle de vie de l'activité
        System.out.println("MainActivity::onCreate()");

        /*On assigne à nos variables les éléments de notre interface à l'aide de la méthode findViewById qui récupère chaque
         *élément via son id présent dans le fichier layout correspondant :*/
        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mRankingButton = (Button) findViewById(R.id.activity_main_ranking_btn);

        /*On génère l'instance d'un user*/
        mUser = new User();

        /*On initialise l'instance de la SharedPreferences API*/
        mPreferences = getPreferences(MODE_PRIVATE);

        //On initialise l'instance de notre TinyDB
        mTinydb = new TinyDB(this);

        mUserRepository = new UserRepository(mTinydb);


        /*Au lancement de l'appli, le bouton est désactivé par défaut*/
        mPlayButton.setEnabled(false);


        //Par contre si on a déjà joué, on va récupérer et afficher le prénom, le score, pré-remplir l'input et activer le bouton
        greetUser();

        /*Pour être notifié lorsque l'utilisateur commence à saisir du texte dans le champ EditText. on place un listener*/
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*C'est là qu'on écoute la saisie de caractères dans l'élément EditText et qu'on active le Button*/
                mPlayButton.setEnabled(s.toString().length() != 0);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*Pour détecter que l'utilisateur a cliqué sur le bouton, il est nécessaire d'implémenter un autre listener.*/
        mPlayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // The user just clicked

                /*On affecte à l'attribut mFirstName du user la valeur récupérée dans le nNameInput*/
                String firstName = mNameInput.getText().toString();
                mUser.setFirstName(firstName);

                //On enregistre dans la mémoire du device, grâce à la SharePreferences API, le prénom du joueur
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstName()).apply();

                /*La classe utilitaire Android Intent permet d'indiquer notre intention de changer d'activity. Son constructeur
                 *nécessite de lui indiquer l'activity où on se trouve et l'activity sur laquelle on souhaite basculer
                 */
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });

        mRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playersRankingActivity = new Intent(MainActivity.this, PlayersRankingActivity.class);
                startActivity(playersRankingActivity);
            }
        });

    }

    //C'est cette méthode qui finalement récupère dans la mémoire du système le score transmis depuis la GameActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            //On enregistre dans la mémoire du device, grâce à la SharePreferences API, le dernier score du joueur
            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            //Après la partie, si on n'a pas encore d'historique de joueurs, on initialise et sauvegarde notre mPlayersList dans TinyDB
            //mPlayersListOnTiny = (ArrayList) mTinydb.getListObject(TINYDB_KEY_PLAYERS_LIST, User.class);
            mUser.setUserScore(score);
            ArrayList<User> players = mUserRepository.getPlayersList();
            System.out.println(players);

            boolean founded = false;
            for (User player : players){
                if (player.getFirstName().equals(mUser.getFirstName())){
                    founded = true;
                    if (player.getUserScore() < mUser.getUserScore()){
                        player.setUserScore(mUser.getUserScore());
                    }
                }
            }

            if (founded == false) {
                players.add(mUser);
            }


            players.sort((a, b) -> a.getUserScore() > b.getUserScore()?-1:1);

            if (players.size() >= 5) {
                players = new ArrayList<User>(players.subList(0, 5));
            }

            System.out.println(players);
            mUserRepository.setPlayersList(players);
            System.out.println(mUserRepository.getPlayersList());


            greetUser();
        }
    }

    private void greetUser() {
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if (null != firstname) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Welcome back, " + firstname
                    + "!\nYour last score was " + score
                    + ", will you do better this time?";
            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }

    //Traces pour monitorer les différents états du cycle de vie de l'activité
    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("MainActivity::onDestroy()");
    }

}