package com.example.movierest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@RestController
public class MovieController {

    @Autowired
    private MovieDAO movieDAO = new MovieDAO();

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public List<Movie> getMovies() {
        List<Movie> movies = movieDAO.getAll();
        return movies;
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.GET)
    public Movie getMovieById(@PathVariable int id) {
        Movie movie = movieDAO.getById(id);
        if (movie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + id + " was not found.");
        }
        return movie;
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    public Movie createMovie(@RequestBody Movie movie) {
        movieDAO.create(movie);
        return movie;
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    public Movie updateNameById(@RequestBody Movie movie, @PathVariable int id) {
        Movie newMovie = movieDAO.getById(id);
        System.out.println(id);
        if (movie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + id + " was not found ");
        }
        newMovie.setTitle(movie.getTitle());
        newMovie.setGenre(movie.getGenre());
        newMovie.setRate(movie.getRate());
        newMovie.setDescription(movie.getDescription());
        newMovie.setRateNum(movie.getRateNum());
        movieDAO.updateMovie(newMovie);
        return movie;
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    public String deleteMovie(@PathVariable int id) {
        getMovieById(id);
        movieDAO.deleteById(id);
        return "ID " + id + " was deleted.";
    }
}
