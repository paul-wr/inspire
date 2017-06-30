package com.example.mainaccount.inspire.model;

import java.util.ArrayList;
import java.util.Random;


public class Gems {
    private String category;
    private String gem;
    Random ran;
    public ArrayList <Gems> gemList;


    public Gems(){
        ran = new Random();
        gemList = new ArrayList<>();
    }

    public Gems(String category, String gem){
        this.category = category;
        this.gem = gem;
    }

    // method to populate phraseList
    public void createList(){
        gemList.add(new Gems("Love", "love text1"));
        gemList.add(new Gems("Love", "love text2"));
        gemList.add(new Gems("Love", "love text3"));
        gemList.add(new Gems("Wisdom", "wisdom text1"));
        gemList.add(new Gems("Wisdom", "wisdom text2"));
        gemList.add(new Gems("Wisdom", "wisdom text3"));
        gemList.add(new Gems("Freedom", "freedom text1"));
        gemList.add(new Gems("Freedom", "freedom text2"));
        gemList.add(new Gems("Freedom", "freedom text3"));

    }

    public String getCategory(){
        return category;
    }

    public String getGem(){
        return gem;
    }

    public ArrayList getList(){
        return gemList;
    }

    public String[] getRandomGem(){
        Gems p = gemList.get(ran.nextInt(gemList.size()));
        String[] a = new String[2];
        // System.out.println(p.getCategory()+": "+p.getPhrase());

        a[0] = p.getCategory();
        a[1] = p.getGem();

        return a;

    }

    public void getPhraseByCategory(String phraseCategory){
        for(Gems phrase : gemList){
            if(phrase.getCategory().equalsIgnoreCase(phraseCategory)){
                System.out.println(phrase.getCategory()+": "+phrase.getGem());
            }
        }

    }

}