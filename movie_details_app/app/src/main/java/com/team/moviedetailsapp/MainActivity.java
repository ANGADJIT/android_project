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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing RecyclerView
        RecyclerView movies = findViewById(R.id.moviesList);
        MoviesListAdapter moviesListAdapter = new MoviesListAdapter(getApplicationContext());
        movies.setLayoutManager(new LinearLayoutManager(this));
        movies.setAdapter(moviesListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search For Movie");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}