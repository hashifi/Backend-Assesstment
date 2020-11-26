package com.example.assestment.controller;

import com.example.assestment.entity.MovieEntity;
import com.example.assestment.exception.RecordNotFoundException;
import com.example.assestment.model.MovieRequest;
import com.example.assestment.service.MovieService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/movie")
public class MovieController{
    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieService movieService;

    @PostMapping(produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<MovieEntity> createOrUpdateNewMovie(@RequestBody MovieRequest request) throws RecordNotFoundException{
        log.info("addNewMovie started");
        log.debug(new Gson().toJson(request));

        double rating = request.getRating();
        if (rating < 0.0 || rating > 5.0){
            log.debug("Rating value invalid: ", rating);
            return new ResponseEntity(new Json("Rating value must between 0 and 5"), HttpStatus.BAD_REQUEST);
        }

        MovieEntity movieEntity = new MovieEntity();

        if (request.getId() != null){
            movieEntity.setId(request.getId());
        }
        movieEntity.setTitle(request.getMovieTitle());
        movieEntity.setCategory(request.getMovieCategory());
        movieEntity.setRating(request.getRating());

        movieEntity = movieService.createOrUpdateMovie(movieEntity);

        return new ResponseEntity<>(movieEntity, HttpStatus.CREATED);
    }

    @GetMapping(produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<MovieEntity>> getAllMovies() {
        log.info("getAllMovies started");
        List<MovieEntity> moviesList = movieService.getAllMovies();

        return new ResponseEntity<>(moviesList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<MovieEntity> getMovieById(@PathVariable("id") Long id) throws RecordNotFoundException {
        log.info("getMovieId started");
        log.debug("Get movie by Id: ", id);
        MovieEntity movie = movieService.getMovieById(id);

        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json;charset=UTF-8"})
    public HttpStatus deleteMovieeById(@PathVariable("id") Long id) throws RecordNotFoundException {
        log.info("deleteMovieId started");
        log.debug("Delete movie by Id");
        movieService.deleteMovieById(id);
        return HttpStatus.OK;
    }
}
