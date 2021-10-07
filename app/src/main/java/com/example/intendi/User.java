package com.example.intendi;

import java.io.Serializable;

public class User implements Serializable {

    private String username, dateBirth;
    private int image, user_id;
    //Map<String, List<Resultado>> donde los atributos del objetos Resultado -> score (int), fecha (date o string)
    public User(int user_id, String userName, int imageSource, String dateBirth){
        this.user_id = user_id;
        this.username = userName;
        this.image = imageSource;
        this.dateBirth = dateBirth;
    }

    public int getUser_id(){ return user_id; }

    public String getUsername() {
        return username;
    }

    public int getImageSource() {
        return image;
    }

    public String getDateBirth(){ return dateBirth;}

    public void setImageSource(int imageSource) {
        this.image = imageSource;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
