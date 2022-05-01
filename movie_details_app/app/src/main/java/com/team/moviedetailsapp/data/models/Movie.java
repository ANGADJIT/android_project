package com.team.moviedetailsapp.data.models;

public class Movie {
    public final String posterUrl;
    public final String movieName;
    public final String country;
    public final String released;
    public final String reference;
    public final String description;
    public final String cast;
    public final String production;

    public Movie(String posterUrl, String movieName, String country, String released, String reference, String description, String cast, String production) {
        this.posterUrl = posterUrl;
        this.movieName = movieName;
        this.country = country;
        this.released = released;
        this.reference = reference;
        this.description = description;
        this.cast = cast;
        this.production = production;
    }
}
