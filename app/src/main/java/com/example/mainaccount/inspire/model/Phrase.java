package com.example.mainaccount.inspire.model;

import java.util.ArrayList;
import java.util.Random;


public class Phrase{
    private String category;
    private String phrase;
    Random ran;
    public ArrayList <Phrase> phraseList;


    public Phrase(){
        ran = new Random();
        phraseList = new ArrayList<Phrase>();
    }

    public Phrase(String category, String phrase){
        this.category = category;
        this.phrase = phrase;
    }

    // method to populate phraseList
    public void createList(){
        phraseList.add(new Phrase("Love", "love text1"));
        phraseList.add(new Phrase("Love", "love text2"));
        phraseList.add(new Phrase("Love", "love text3"));
        phraseList.add(new Phrase("Wisdom", "wisdom text1"));
        phraseList.add(new Phrase("Wisdom", "wisdom text2"));
        phraseList.add(new Phrase("Wisdom", "wisdom text3"));
        phraseList.add(new Phrase("Freedom", "freedom text1"));
        phraseList.add(new Phrase("Freedom", "freedom text2"));
        phraseList.add(new Phrase("Freedom", "freedom text3"));

    }

    public String getCategory(){
        return category;
    }

    public String getPhrase(){
        return phrase;
    }

    public ArrayList getList(){
        return phraseList;
    }

    public String[] getRandomPhrase(){
        Phrase p = phraseList.get(ran.nextInt(phraseList.size()));
        String[] a = new String[2];
        // System.out.println(p.getCategory()+": "+p.getPhrase());

        a[0] = p.getCategory();
        a[1] = p.getPhrase();

        return a;

    }

    public void getPhraseByCategory(String phraseCategory){
        for(Phrase phrase : phraseList){
            if(phrase.getCategory().equalsIgnoreCase(phraseCategory)){
                System.out.println(phrase.getCategory()+": "+phrase.getPhrase());
            }
        }

    }

}