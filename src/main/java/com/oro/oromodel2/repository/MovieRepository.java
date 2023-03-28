package com.oro.oromodel2.repository;

import com.oro.oromodel2.model.Movie;
import com.oro.oromodel2.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT s FROM Screening s WHERE s.movie.id = ?1")
    List<Screening> findAllScreeningsForMovieById(long movieId);

    @Query("SELECT s FROM Screening s WHERE s.movie.title = ?1")
    List<Screening> findAllScreeningsForMovieByName(String movieName);

    @Query("SELECT COUNT(DISTINCT s.hall.id) FROM Screening s WHERE s.movie.id = ?1")
    int countHallsForMovie(long movieId);

}
