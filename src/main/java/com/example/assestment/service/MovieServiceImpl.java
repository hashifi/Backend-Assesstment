package com.example.assestment.service;

import com.example.assestment.entity.MovieEntity;
import com.example.assestment.exception.RecordNotFoundException;
import com.example.assestment.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    private MovieRepository movieRepository;

    public MovieEntity createOrUpdateMovie(MovieEntity movieEntity) throws RecordNotFoundException {
        log.debug("createOrUpdateMovie called");

        Long id = movieEntity.getId();

        Optional<MovieEntity> movie = movieRepository.findById(id);
        if (id != null) {
            if (movie.isPresent()) {
                log.debug("Updating movie id: ", movieEntity.getId());
                MovieEntity entity = movie.get();
                entity.setTitle(movieEntity.getTitle());
                entity.setCategory(movieEntity.getCategory());
                entity.setRating(movieEntity.getRating());

                return movieRepository.save(entity);
            } else {
                throw new RecordNotFoundException("Cannot find movie with Id: "+ id);
            }
        } else{
            log.debug("Creating new movie");
            return movieRepository.save(movieEntity);
        }
    }

    public List<MovieEntity> getAllMovies(){
        log.debug("getAllMovies called");
        try {
            List<MovieEntity> movies = movieRepository.findAll();

            if(movies.isEmpty()){
                return null;
            } else {
                return movies;
            }

        } catch (Exception e) {
            log.error("Error retrieving all movies:", e);
            return null;
        }
    }

    public MovieEntity getMovieById(Long id) throws RecordNotFoundException {
        log.debug("getMovieById called");

        Optional<MovieEntity> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new RecordNotFoundException("Cannot find movie with Id: "+ id);
        }
    }

    public void deleteMovieById(Long id) throws RecordNotFoundException {
        log.debug("deleteMovieById called");
        Optional<MovieEntity> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            movieRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Movie Id not exists");
        }
    }

}
