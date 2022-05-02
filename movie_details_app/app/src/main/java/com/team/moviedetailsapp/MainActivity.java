package com.team.moviedetailsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.team.moviedetailsapp.logic.MoviesListAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView movies = findViewById(R.id.moviesList);
        MoviesListAdapter moviesListAdapter = new MoviesListAdapter(getApplicationContext());
        movies.setLayoutManager(new LinearLayoutManager(this));
        movies.setAdapter(moviesListAdapter);
    }
}