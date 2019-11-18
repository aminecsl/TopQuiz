package com.example.topquiz.controller;

import com.example.topquiz.TinyDB;
import com.example.topquiz.model.User;

import java.util.ArrayList;

/**
 * Created by Amine K. on 17/11/19.
 */

/*On met en place une classe qui va gérer nos actions sur l'historique de joueurs*/
public class UserRepository {

    /*Elle comporte en attribut une instance de TinyDB et une clé String pour identifier notre liste de joueurs dans la mémoire*/
    private TinyDB mTinydb;
    private static final String TINYDB_KEY_PLAYERS_LIST = "TINYDB_KEY_PLAYERS_LIST";


    public UserRepository(TinyDB mtinyDb) {

        mTinydb =  mtinyDb;
    }

    /*Notre méthode pour récupérer notre liste de joueurs dans la mémoire*/
    public ArrayList<User> getPlayersList(){
        ArrayList<Object> playerObjects = mTinydb.getListObject(TINYDB_KEY_PLAYERS_LIST, User.class);
        ArrayList<User> players = new ArrayList<User>();

        if (playerObjects == null){
            return players;
        }

        for(Object objs : playerObjects){
            players.add((User)objs);
        }

        return players;
    }

    /*Notre méthode pour mettre à jour notre liste*/
    public void setPlayersList(ArrayList<User> players){

        ArrayList<Object> playerObjects = new ArrayList<Object>();
        if(players != null && !players.isEmpty()){

            for(User player : players){
                playerObjects.add((Object) player);

            }
        }

        mTinydb.putListObject(TINYDB_KEY_PLAYERS_LIST, playerObjects);
    }

}
