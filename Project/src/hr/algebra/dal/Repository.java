/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Petra
 */
public interface Repository {
   
    int createUser(User user) throws Exception;
    Optional<User> verifyUser(User user) throws Exception;
    
    int getOrCreatePersonId(Person person) throws Exception;
    Optional<Person> selectPerson (int id) throws Exception;
    Set<Person> selectPeople () throws Exception;
    void updatePerson (int id, Person data) throws Exception;
    void deletePerson (int id) throws Exception;
    
    void createMovieActors(int movieId, int personId) throws Exception;
    Set<Person> selectMovieActors (int id) throws Exception;
    void deleteMovieActors (int id) throws Exception;

    
    void createMovieDirectors(int movieId, int personId) throws Exception;
    Set<Person> selectMovieDirectors (int id) throws Exception;
    void deleteMovieDirectors (int id) throws Exception;
    
    int getOrCreateGenreId(Genre genre) throws Exception;
    void createMovieGenres(int movieId, int genreId) throws Exception;
    Optional<Genre> selectGenre (int id) throws Exception;
    Set<Genre> selectGenres () throws Exception;
    Set<Genre> selectMovieGenres (int id) throws Exception;    
    void updateGenre (int id, Genre data) throws Exception;    
    void deleteMovieGenres (int id) throws Exception;
    
    int createMovie(Movie movie) throws Exception;
    void createMovies( List<Movie> movies) throws Exception;
    void updateMovie(int id, Movie data) throws Exception;
    void deleteMovie(int id) throws Exception;
    Optional<Movie> selectMovie(int id) throws Exception;
    List<Movie> selectMovies() throws Exception;

    void deleteAll() throws Exception;
    
}
