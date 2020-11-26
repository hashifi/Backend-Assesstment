package com.example.assestment.service;

import com.example.assestment.entity.MovieEntity;
import com.example.assestment.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    MovieEntity createOrUpdateMovie(MovieEntity movieEntity) throws RecordNotFoundException;

    List<MovieEntity> getAllMovies();

    MovieEntity getMovieById(Long id) throws RecordNotFoundException;

    void deleteMovieById(Long id) throws RecordNotFoundException;
}
