package com.example.intendi;

public class User {

    private String username, dateBirth;
    private int image, user_id;
    //Map<String, List<Resultado>> donde los atributos del objetos Resultado -> score (int), fecha (date o string)
    public User(int user_id, String userName, int imageSource, String dateBirth){
        this.username = userName;
        this.image = imageSource;
        this.dateBirth = dateBirth;
    }

    public String getUsername() {
        return username;
    }

    public int getImageSource() {
        return image;
    }

    public void setImageSource(int imageSource) {
        this.image = imageSource;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
