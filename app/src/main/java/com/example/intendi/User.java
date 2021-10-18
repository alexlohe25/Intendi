package com.example.intendi;

import java.io.Serializable;
/*
  Class that serves to store user data for screen display.
  It stores the username, the date of birth, an image, and a user id.
*/
public class User implements Serializable {

    private String username, dateBirth;
    private int image, user_id;
    //Map<String, List<Resultado>> donde los atributos del objetos Resultado -> score (int), fecha (date o string)

    //Necessary constructor, receives an id (int), a name (string), an image (int), and date of birth (string)
    public User(int user_id, String userName, int imageSource, String dateBirth){
        this.user_id = user_id;
        this.username = userName;
        this.image = imageSource;
        this.dateBirth = dateBirth;
    }

    //Returns user id
    public int getUser_id(){ return user_id; }

    //Returns username
    public String getUsername() {
        return username;
    }

    //Returns Image resource
    public int getImageSource() {
        return image;
    }

    //Returns date of birth
    public String getDateBirth(){ return dateBirth;}

    //Sets Image resource
    public void setImageSource(int imageSource) {
        this.image = imageSource;
    }

    //Sets username
    public void setUsername(String username) {
        this.username = username;
    }

}
