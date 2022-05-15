package com.team.moviedetailsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.team.moviedetailsapp.logic.MoviesListAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    MoviesListAdapter moviesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // changing color of action bar
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9f29e3")));

        // Initializing RecyclerView
        RecyclerView movies = findViewById(R.id.moviesList);
        moviesListAdapter = new MoviesListAdapter(this);
        movies.setLayoutManager(new LinearLayoutManager(this));
        movies.setAdapter(moviesListAdapter);

        if (moviesListAdapter.checkForInternet()) {
            moviesListAdapter.getAllMovies();
        } else {
            Toast.makeText(this, "No internet 🙄", Toast.LENGTH_SHORT).show();
        }
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
                if (moviesListAdapter.checkForInternet()) {
                    moviesListAdapter.searchForMovies(s);
                } else {
                    Toast.makeText(getApplicationContext(), "No internet 🙄", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    if(moviesListAdapter.checkForInternet()){
                        moviesListAdapter.getAllMovies();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No internet 🙄", Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }
        });

        // adding method to about and cache option
        MenuItem about = menu.findItem(R.id.about);
        about.setOnMenuItemClickListener((v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This is movie details app as well as refer to third party websites where you can watch free movies 🎥");

            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        });

        return super.onCreateOptionsMenu(menu);
    }
}