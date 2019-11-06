package com.example.topquiz.model;

/**
 * Created by Amine K. on 06/11/19.
 */
public class User {

    private String mFirstName;

    /*Le getter qui permet d'accéder depuis une autre classe aux attributs d'un user*/
    public String getFirstName() {
        return mFirstName;
    }

    /*Le setter qui permet depuis une autre classe de modifier la valeur d'un attribut*/
    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    /*Cette méthode permet de retourner et lire textuellement les valeurs des attributs d'un objet User instancié*/
    @Override
    public String toString() {
        return "User{" +
                "mFirstName='" + mFirstName + '\'' +
                '}';
    }
}
