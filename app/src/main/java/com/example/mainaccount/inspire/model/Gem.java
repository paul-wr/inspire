package com.example.mainaccount.inspire.model;

/**
 *  Classname: Gem.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @author Paul Wrenn, x15020029
 */

import java.util.ArrayList;
import java.util.Random;


public class Gem {
    private String category;
    private String gem;
    private String author;
    Random ran;
    private ArrayList <Gem> gemList;


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
        gemList.add(new Gem("Music", "Music text1", "author"));
        gemList.add(new Gem("Music", "Music text2",  "author"));
        gemList.add(new Gem("Music", "Music text3", "author"));
        gemList.add(new Gem("Education", "Education text1", "author"));
        gemList.add(new Gem("Education", "Education text2", "author"));
        gemList.add(new Gem("Education", "Education text3", "author"));
        gemList.add(new Gem("Health", "Health text1", "author"));
        gemList.add(new Gem("Health", "Health text2", "author"));
        gemList.add(new Gem("Health", "Health text3", "author"));

    }

    public String getCategory(){
        return category;
    }


    public String getGem(){
        return gem;
    }

    public ArrayList<Gem> getList(){
        return gemList;
    }

    public String[] getRandomGem(){
        Gem g = gemList.get(ran.nextInt(gemList.size()));
        String[] a = new String[3];
        // System.out.println(p.getCategory()+": "+p.getPhrase());

        a[0] = g.getCategory();
        a[1] = g.getGem();
        a[2] = g.getAuthor();

        return a;

    }

    public ArrayList<Gem> getGemByCategpry(String category){
        ArrayList<Gem> g = new ArrayList<>();
        for(Gem gem : gemList){
            if(gem.getCategory().equalsIgnoreCase(category)){
                g.add(gem);
            }
        }
        return g;
    }

}