package com.nayan.doggle;

public class User {
    private String username;
    private String dogname;
    private String dogbreed;

    public User() {
    }

    public User(String username, String dogname, String dogbreed) {
        this.username = username;
        this.dogname = dogname;
        this.dogbreed = dogbreed;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getDogName() {
        return dogname;
    }

    public void setDogName(String dogName) {
        this.dogname = dogname;}

    public String getDogBreed() {
        return dogbreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogbreed = dogBreed;
    }
}
