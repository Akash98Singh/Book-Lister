package com.example.booklister;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView averageRatingTextView = (TextView)listItemView.findViewById(R.id.average_rating);
        averageRatingTextView.setText(Double.toString(currentBook.getAverageRating()));
        GradientDrawable gradientDrawable = (GradientDrawable)averageRatingTextView.getBackground();
        int backgroundColor = ContextCompat.getColor(getContext(),getRatingColor(currentBook.getAverageRating()));
        gradientDrawable.setColor(backgroundColor);

        TextView titleTextView = (TextView)listItemView.findViewById(R.id.title);
        titleTextView.setText(currentBook.getTitle());

        TextView authorTextView = (TextView)listItemView.findViewById(R.id.author);
        String author = "by " + currentBook.getAuthor();
        authorTextView.setText(author);

        TextView pageCountTextView = (TextView)listItemView.findViewById(R.id.page_count);
        pageCountTextView.setText(Integer.toString(currentBook.getNoOfPages()));

        return listItemView;
    }

    private int getRatingColor(double rating) {
        if (rating > 4) return R.color.rating5;
        else if (rating <= 4 && rating > 3) return R.color.rating4;
        else if (rating <= 3 && rating > 2) return R.color.rating3;
        else if (rating <= 2 && rating > 1) return R.color.rating2;
        else if (rating <= 1 && rating > 0) return R.color.rating1;
        else return R.color.rating1;
    }
}
