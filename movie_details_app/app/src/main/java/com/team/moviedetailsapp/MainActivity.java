package com.team.moviedetailsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.team.moviedetailsapp.logic.MoviesListAdapter;

public class MainActivity extends AppCompatActivity {

    MoviesListAdapter moviesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing RecyclerView
        RecyclerView movies = findViewById(R.id.moviesList);
        moviesListAdapter = new MoviesListAdapter(this);
        movies.setLayoutManager(new LinearLayoutManager(this));
        movies.setAdapter(moviesListAdapter);

        moviesListAdapter.getAllMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // adding search ui
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search For Movie");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                moviesListAdapter.searchForMovies(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    moviesListAdapter.getAllMovies();
                }

                return true;
            }
        });

        // adding method to about and cache option
        MenuItem about = menu.findItem(R.id.about);
        about.setOnMenuItemClickListener((v) -> {
            Toast.makeText(this, "Clicked...", Toast.LENGTH_SHORT).show();

            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }
}