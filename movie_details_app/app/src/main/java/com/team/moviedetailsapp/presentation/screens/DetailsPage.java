package com.team.moviedetailsapp.presentation.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.team.moviedetailsapp.R;
import com.team.moviedetailsapp.data.models.Movie;

public class DetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getExtras().get("object");

        // attributes
        ShapeableImageView poster = findViewById(R.id.detailPoster);
        TextView movieTitle = findViewById(R.id.detailTitle);

        Glide.with(getApplicationContext()).load(movie.posterUrl).into(poster);
        movieTitle.setText(movie.movieName);
    }
}