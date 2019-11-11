package com.example.topquiz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topquiz.R;
import com.example.topquiz.model.User;

public class MainActivity extends AppCompatActivity {

    //On donne un identifiant à une autre activity afin d'y faire référence dans certaines méthodes comme startActivityForResult()
    public static final int GAME_ACTIVITY_REQUEST_CODE = 99;

    /*Pour référencer les éléments graphiques, on commence par déclarer des variables qui leur seront associées :*/
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;

    /* On déclare qu'on aura une instance de la classe User qui permettra de lui affecter le mNameInput au clic sur le bouton*/
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*On assigne à nos variables les éléments de notre interface à l'aide de la méthode findViewById qui récupère chaque
         *élément via son id présent dans le fichier layout correspondant :*/
        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);

        /*On génère l'instance d'un user*/
        mUser = new User();

        /*Au lancement de l'appli, le bouton est désactivé par défaut*/
        mPlayButton.setEnabled(false);

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

                /*La classe utilitaire Android Intent permet d'indiquer notre intention de changer d'activity. Son constructeur
                 *nécessite de lui indiquer l'activity où on se trouve et l'activity sur laquelle on souhaite basculer
                 */
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    //C'est cette méthode qui finalement récupère dans la mémoire du système le score transmis depuis la GameActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
        }
    }

    
}
