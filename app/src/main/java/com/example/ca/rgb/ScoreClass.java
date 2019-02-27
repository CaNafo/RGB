package com.example.ca.rgb;

public class ScoreClass {
    String name;
    String score;

    public ScoreClass(String name, String score){
        setName(name);
        setScore(score);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }


}
