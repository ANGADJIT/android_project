package com.team.moviedetailsapp.presentation.screens;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.team.moviedetailsapp.R;
import com.team.moviedetailsapp.data.models.Movie;

import java.util.Objects;

public class DetailsPage extends AppCompatActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Intent intent = getIntent();
        movie = (Movie) intent.getExtras().get("object");

        // changing color of action bar
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9f29e3")));

        // setting action bar title as movie name
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setTitle(movie.movieName);

        // ui attributes
        ShapeableImageView poster = findViewById(R.id.detailPoster);
        TextView movieTitle = findViewById(R.id.detailTitle);
        TextView movieRelDate = findViewById(R.id.detailRelease);
        TextView description = findViewById(R.id.detailDescription);
        TextView cast = findViewById(R.id.detailCast);
        TextView production = findViewById(R.id.detailProduction);

        // set content to ui
        movieRelDate.setText(movie.released);
        description.setText(movie.description);
        cast.setText(movie.cast);
        production.setText(movie.production);
        Glide.with(getApplicationContext()).load(movie.posterUrl).into(poster);
        movieTitle.setText(movie.movieName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ref_menu, menu);

        MenuItem refBtn = menu.findItem(R.id.ref);
        refBtn.setOnMenuItemClickListener((v) -> {
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(movie.reference));
            startActivity(intent);

            return true;
        });
        return true;
    }
}