package com.team.moviedetailsapp.logic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.team.moviedetailsapp.R;
import com.team.moviedetailsapp.data.models.Movie;
import com.team.moviedetailsapp.presentation.screens.DetailsPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieTileViewHolder> {
    private final ArrayList<Movie> movies = new ArrayList<Movie>();
    final Context context;
    final String ALL_MOVIES = "https://moviesdetail-app.herokuapp.com/get_movies";
    final String SEARCH_FOR_MOVIES = "https://moviesdetail-app.herokuapp.com/search?sr=";

    public MoviesListAdapter(Context context) {
        this.context = context;
    }

    public void searchForMovies(String keyword) {

        // clear before
        movies.clear();

        ProgressDialog dialog = new ProgressDialog(context);
        final RequestQueue queue = Volley.newRequestQueue(context);
        dialog.setMessage("searching for '" + keyword + "'");
        dialog.show();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, SEARCH_FOR_MOVIES + keyword, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                final int resLength = response.length();

                if (resLength == 0) {
                    Toast.makeText(context, "No movie found with keyword '" + keyword + "'", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        final String movieName = object.getString("movie_name");
                        final String posterUrl = object.getString("poster_url");
                        final String production = object.getString("production");
                        final String reference = object.getString("reference");
                        final String released = object.getString("Released");
                        final String cast = object.getString("cast");
                        final String country = object.getString("country");
                        final String rating = object.getString("rating");
                        final String description = object.getString("description");

                        final Movie movie = new Movie(posterUrl, movieName, country, released, reference, description, cast, production, rating);

                        // add to list
                        movies.add(movie);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // deleting duplicate data
                movies.subList(resLength, movies.size()).clear();

                // updating data and close progress dialog
                notifyDataSetChanged();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error while fetching data... ", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void getAllMovies() {
        ProgressDialog dialog = new ProgressDialog(context);
        final RequestQueue queue = Volley.newRequestQueue(context);
        dialog.setMessage("getting movies.. 🎥🎬");
        dialog.show();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ALL_MOVIES, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);

                        final String movieName = object.getString("movie_name");
                        final String posterUrl = object.getString("poster_url");
                        final String production = object.getString("production");
                        final String reference = object.getString("reference");
                        final String released = object.getString("Released");
                        final String cast = object.getString("cast");
                        final String country = object.getString("country");
                        final String rating = object.getString("rating");
                        final String description = object.getString("description");

                        final Movie movie = new Movie(posterUrl, movieName, country, released, reference, description, cast, production, rating);

                        // add to list
                        movies.add(movie);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                notifyDataSetChanged();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error while fetching data... ", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public boolean checkForInternet() {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @NonNull
    @Override
    public MovieTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movieTile = inflater.inflate(R.layout.movie_tile, parent, false);
        final MovieTileViewHolder movieTileViewHolder = new MovieTileViewHolder(movieTile);

        return movieTileViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTileViewHolder holder, int position) {
        holder.title.setText(movies.get(position).movieName);
        holder.country.setText(movies.get(position).country);
        holder.rating.setText(String.format("⭐%s", movies.get(position).rating));
        Glide.with(context).load(movies.get(position).posterUrl).into(holder.poster);

        holder.movieCard.setOnClickListener((v) -> {
            Intent intent = new Intent(context, DetailsPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Movie movie = movies.get(position);
            intent.putExtra("object", movie);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieTileViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView country;
        final ShapeableImageView poster;
        final TextView rating;
        final CardView movieCard;

        public MovieTileViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            country = itemView.findViewById(R.id.country);
            movieCard = itemView.findViewById(R.id.movieCard);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}
