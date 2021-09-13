package com.example.intendi;

public class User {

    private String username;
    private int image;

    public User(String userName, int imageSource){
        this.username = userName;
        this.image = imageSource;
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
