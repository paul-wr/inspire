package com.example.mainaccount.inspire.model;

/**
 *  Classname: Gem.java
 *  Version 1
 *  Date: 25 Jun 2017
 *  @author Paul Wrenn, x15020029
 */

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;


public class Gem extends AppCompatActivity {
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
        // music gems
        gemList.add(new Gem("Music", "Music produces a kind of pleasure which human nature cannot do without.", "Confucius"));
        gemList.add(new Gem("Music", "Music is … A higher revelation than all Wisdom & Philosophy.",  " Ludwig van Beethoven"));
        gemList.add(new Gem("Music", "Music is what tell us that the human race is greater than we realize.", "Napoléon Bonaparte"));
        gemList.add(new Gem("Music", "I would rather write 10,000 notes than a single letter of the alphabet.", "Ludwig van Beethoven"));
        gemList.add(new Gem("Music", "Music in the soul can be heard by the universe.", "Lao Tzu"));

        // education gems
        gemList.add(new Gem("Education", "Live as if you were to die tomorrow. Learn as if you were to live forever.", "Mahatma Gandhi"));
        gemList.add(new Gem("Education", "Education is the most powerful weapon which you can use to change the world.", "Nelson Mandela"));
        gemList.add(new Gem("Education", "You educate a man; you educate a man. You educate a woman; you educate a generation.", "Brigham Young"));
        gemList.add(new Gem("Education", "Live as if you were to die tomorrow. Learn as if you were to live forever.", "Mahatma Gandhi"));
        gemList.add(new Gem("Education", "Children must be taught how to think, not what to think.", "Margaret Mead"));
        gemList.add(new Gem("Education", "Intelligence plus character-that is the goal of true education.", "Martin Luther King Jr"));
        gemList.add(new Gem("Education", "Educating the mind without educating the heart is no education at all.", "Aristotle"));
        gemList.add(new Gem("Education", "The mind is not a vessel to be filled, but a fire to be kindled.", "Plutarch"));
        gemList.add(new Gem("Education", "Self-education is, I firmly believe, the only kind of education there is.", "Isaac Asimov"));
        gemList.add(new Gem("Education", "In learning you will teach, and in teaching you will learn.", "Phil Collins"));
        gemList.add(new Gem("Education", "The man who reads nothing at all is better educated than the man who reads nothing but newspapers.", "Thomas Jefferson"));

        // health gems
        gemList.add(new Gem("Health", "After dinner rest a while, after supper walk a mile.", " T.Cogan"));
        gemList.add(new Gem("Health", "An over-indulgence of anything, even something as pure as water, can intoxicate.", "Criss Jami"));
        gemList.add(new Gem("Health", "The First wealth is health.", "Ralph Waldo Emerson"));
        gemList.add(new Gem("Health", "By choosing healthy over skinny you are choosing self-love over self-judgment. You are beautiful!", "Steve Maraboli"));
        gemList.add(new Gem("Health", "Eat healthily, sleep well, breathe deeply, move harmoniously.","Jean-Pierre Barral"));
        gemList.add(new Gem("Health", "No disease that can be treated by diet should be treated with any other means.", "Maimonides"));
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