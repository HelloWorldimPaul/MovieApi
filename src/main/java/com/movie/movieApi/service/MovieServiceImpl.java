package com.movie.movieApi.service;

import com.movie.movieApi.dto.MovieDto;
import com.movie.movieApi.entities.Movie;
import com.movie.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {


    private final MovieRepository movieRepository;

    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // upload the file
        String uploadedFileName = fileService.uploadFile(path, file);

        // set the value of field 'poster' as filename
        // chatgpt bug fix movieDto.setPoster(uploadedFileName);
        movieDto.setPosterUrl(uploadedFileName);


        // chatgpt bug fix Set<String> cast = movieDto.getMovieCast() != null ? movieDto.getMovieCast() : new HashSet<>();

        // map dto to Movie object
        Movie movie = new Movie(
                movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        // save the movie object -> saved Movie object
        Movie savedMovie = movieRepository.save(movie);

        // generate the posterUrl
        String postUrl = baseUrl + "/file/" + uploadedFileName;


        // map Movie object to DTO object and return it
        MovieDto responseDto = new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                postUrl
        );


        return responseDto;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        return null;
    }

    @Override
    public List<MovieDto> getMovies() {
        return List.of();
    }
}
