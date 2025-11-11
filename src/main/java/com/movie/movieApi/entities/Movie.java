package com.movie.movieApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Set;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;


    private String title;

    private String director;

    private String studio;

    private Set<String> movieCast;

    private Integer releaseYear;

    private String poster;

}
