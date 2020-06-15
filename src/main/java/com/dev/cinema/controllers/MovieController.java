package com.dev.cinema.controllers;

import com.dev.cinema.model.Movie;
import com.dev.cinema.model.dto.MovieDto;
import com.dev.cinema.service.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public String addMovie(@RequestBody MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movieService.add(movie);
        return "movie was added";
    }

    @GetMapping
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieService.getAll();
        return movies.stream().map(this::getMovieDto).collect(Collectors.toList());
    }

    private MovieDto getMovieDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setDescription(movie.getDescription());
        movieDto.setTitle(movie.getTitle());
        return movieDto;
    }
}
