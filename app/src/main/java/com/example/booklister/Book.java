package com.example.booklister;

public class Book {
    private String mTitle;
    private String mAuthor;
    private int mNoOfPages;
    private double mAverageRating;

    public Book(String title, String author, int noOfPages, double averageRating){
        mTitle = title;
        mAuthor = author;
        mNoOfPages = noOfPages;
        mAverageRating = averageRating;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public int getNoOfPages(){
        return mNoOfPages;
    }

    public double getAverageRating(){ return mAverageRating;}
}
