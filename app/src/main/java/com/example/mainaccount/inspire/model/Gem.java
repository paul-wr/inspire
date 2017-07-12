package com.example.mainaccount.inspire.model;

import java.util.ArrayList;
import java.util.Random;


public class Gem {
    private String category;
    private String gem;
    private String author;
    Random ran;
    public ArrayList <Gem> gemList;


    public void setCategory(String category) {
        this.category = category;
    }

    public void setGem(String gem) {
        this.gem = gem;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Gem(String category, String gem, String author) {
        this.category = category;
        this.gem = gem;
        this.author = author;
    }

    public Gem(){
        ran = new Random();

        gemList = new ArrayList<>();

    }

    public Gem(String category, String gem){
        this.category = category;
        this.gem = gem;
    }

    // method to populate phraseList
    public void createList(){
        gemList.add(new Gem("Love", "love text1", "author"));
        gemList.add(new Gem("Love", "love text2",  "author"));
        gemList.add(new Gem("Love", "love text3", "author"));
        gemList.add(new Gem("Wisdom", "wisdom text1", "author"));
        gemList.add(new Gem("Wisdom", "wisdom text2", "author"));
        gemList.add(new Gem("Wisdom", "wisdom text3", "author"));
        gemList.add(new Gem("Freedom", "freedom text1", "author"));
        gemList.add(new Gem("Freedom", "freedom text2", "author"));
        gemList.add(new Gem("Freedom", "freedom text3", "author"));

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
        Gem p = gemList.get(ran.nextInt(gemList.size()));
        String[] a = new String[3];
        // System.out.println(p.getCategory()+": "+p.getPhrase());

        a[0] = p.getCategory();
        a[1] = p.getGem();
        a[2] = p.getAuthor();

        return a;

    }

    public void getPhraseByCategory(String phraseCategory){
        for(Gem phrase : gemList){
            if(phrase.getCategory().equalsIgnoreCase(phraseCategory)){
                System.out.println(phrase.getCategory()+": "+phrase.getGem());
            }
        }

    }

}