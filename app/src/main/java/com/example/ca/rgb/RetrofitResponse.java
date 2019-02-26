package com.example.ca.rgb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class RetrofitResponse {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("score")
    @Expose
    private Integer score;

    private ArrayList<HashMap<String, String>> tmpList=null;

    public String getName() {
        return name;
    }

    public void setStatus(String name) {
        System.out.println("ALOOOOOOOOOOOOOO");

        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    public ArrayList<HashMap<String, String>> getDatat() {
        return tmpList;
    }

    public void setData(ArrayList<HashMap<String, String>> data) {
        this.tmpList = data;
    }
}

