/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import hr.algebra.model.User;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import javax.sql.DataSource;

public class SqlRepository implements Repository {

    private static final String USERNAME = "Username";
    private static final String PASSWORD = "UserPassword";
    private static final String IS_ADMIN = "IsAdmin";

    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?) }";
    private static final String VERIFY_USER = "{ CALL verifyUser (?,?) }";

    private static final String ID_PERSON = "IDPerson";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";

    private static final String GET_OR_CREATE_PERSON_ID = "{ CALL getOrCreatePersonId (?,?,?) }";
    private static final String SELECT_PERSON = "{ CALL selectPerson (?) }";
    private static final String SELECT_PEOPLE = "{ CALL selectPeople }";
    private static final String UPDATE_PERSON = "{ CALL updatePerson (?,?,?) }";
    private static final String DELETE_PERSON = "{ CALL deletePerson (?) }";

    private static final String CREATE_MOVIE_ACTORS = "{ CALL createMovieActors (?,?)}";
    private static final String SELECT_MOVIE_ACTORS = "{ CALL selectMovieActors (?)}";
    private static final String DELETE_MOVIE_ACTORS = "{ CALL deleteMovieActors (?)}";

    private static final String CREATE_MOVIE_DIRECTORS = "{ CALL createMovieDirectors (?,?)}";
    private static final String SELECT_MOVIE_DIRECTORS = "{ CALL selectMovieDirectors (?)}";
    private static final String DELETE_MOVIE_DIRECTORS = "{ CALL deleteMovieDirectors (?)}";

    private static final String ID_GENRE = "IDGenre";
    private static final String GENRE_NAME = "GenreName";

    private static final String GET_OR_CREATE_GENRE_ID = "{ CALL getOrCreateGenreId (?,?) }";
    private static final String CREATE_MOVIE_GENRES = "{ CALL createMovieGenres (?,?)}";
    private static final String SELECT_GENRE = "{ CALL selectGenre (?) }";
    private static final String SELECT_GENRES = "{ CALL selectGenres }";
    private static final String UPDATE_GENRE = "{ CALL updateGenre (?,?)}";
    private static final String SELECT_MOVIE_GENRES = "{ CALL selectMovieGenres (?)}";
    private static final String DELETE_MOVIE_GENRES = "{ CALL deleteMovieGenres (?)}";

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED_DATE = "PublishedDate";
    private static final String MOVIE_DESCRIPTION = "MovieDescription";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String MOVIE_LENGTH = "MovieLength";
    private static final String PICTURE_PATH = "PicturePath";
    private static final String LINK = "Link";
    private static final String RELEASE_DATE = "ReleaseDate";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    private static final String DELETE_ALL = "{ CALL deleteAll }";

    @Override
    public int createUser(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.isIsAdmin() ? 1 : 0);

            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.executeUpdate();

            return stmt.getInt(4);
        }
    }

    @Override
    public Optional<User> verifyUser(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(VERIFY_USER)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD),
                            rs.getBoolean(IS_ADMIN)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int getOrCreatePersonId(Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_OR_CREATE_PERSON_ID)) {
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());

            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();

            return stmt.getInt(3);
        }
    }

    @Override
    public Optional<Person> selectPerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PERSON)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Set<Person> selectPeople() throws Exception {
        Set<Person> people = new TreeSet<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PEOPLE);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                people.add(new Person(
                        rs.getInt(ID_PERSON),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)
                ));
            }
        }
        return people;
    }

    @Override
    public void updatePerson(int id, Person data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_PERSON)) {
            stmt.setString(1, data.getFirstName());
            stmt.setString(2, data.getLastName());
            stmt.setInt(3, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePerson(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_PERSON)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void createMovieActors(int movieId, int personId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_ACTORS)) {
            stmt.setInt(1, movieId);
            stmt.setInt(2, personId);

            stmt.executeUpdate();
        }
    }

    @Override
    public Set<Person> selectMovieActors(int id) throws Exception {
        Set<Person> people = new TreeSet<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE_ACTORS)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    people.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME)
                    ));
                }
            }
        }
        return people;
    }

    @Override
    public void deleteMovieActors(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE_ACTORS)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void createMovieDirectors(int movieId, int personId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_DIRECTORS)) {
            stmt.setInt(1, movieId);
            stmt.setInt(2, personId);

            stmt.executeUpdate();
        }
    }

    @Override
    public Set<Person> selectMovieDirectors(int id) throws Exception {
        Set<Person> people = new TreeSet<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE_DIRECTORS)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    people.add(new Person(
                            rs.getInt(ID_PERSON),
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME)
                    )
                    );
                }
            }
        }
        return people;
    }

    @Override
    public void deleteMovieDirectors(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE_DIRECTORS)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public int getOrCreateGenreId(Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(GET_OR_CREATE_GENRE_ID)) {
            stmt.setString(1, genre.getName());

            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();

            return stmt.getInt(2);
        }
    }

    @Override
    public void createMovieGenres(int movieId, int genreId) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE_GENRES)) {
            stmt.setInt(1, movieId);
            stmt.setInt(2, genreId);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Genre> selectGenre(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_GENRE)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Set<Genre> selectGenres() throws Exception {
        Set<Genre> genres = new TreeSet<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_GENRES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                genres.add(new Genre(
                        rs.getInt(ID_GENRE),
                        rs.getString(GENRE_NAME)
                ));
            }
        }
        return genres;
    }

    @Override
    public Set<Genre> selectMovieGenres(int id) throws Exception {
        Set<Genre> genres = new TreeSet<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE_GENRES)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME)
                    ));
                }
            }
        }
        return genres;
    }

    @Override
    public void updateGenre(int id, Genre data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_GENRE)) {
            stmt.setString(1, data.getName());
            stmt.setInt(2, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteMovieGenres(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE_GENRES)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getPublishedDate().format(Movie.DATE_FORMAT));
            stmt.setString(3, movie.getDescription());
            stmt.setString(4, movie.getOriginalTitle());
            stmt.setInt(5, movie.getLength());
            stmt.setString(6, movie.getPicturePath());
            stmt.setString(7, movie.getLink());
            stmt.setString(8, movie.getReleaseDate());

            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.executeUpdate();

            try {
                //Dodavanje zanrova
                if (!movie.getGenres().isEmpty()) {
                    for (Genre genre : movie.getGenres()) {
                        int genreId = getOrCreateGenreId(genre);
                        int movieId = stmt.getInt(9);

                        createMovieGenres(movieId, genreId);
                    }
                }
            } catch (Exception exception) {
                System.out.println("Movie " + movie.getTitle() + " doesn't have genre!");
            }

            try {
                //Dodavanje glumaca
                if (!movie.getActors().isEmpty()) {
                    for (Person actor : movie.getActors()) {
                        int personId = getOrCreatePersonId(actor);
                        int movieId = stmt.getInt(9);

                        createMovieActors(movieId, personId);
                    }
                }
            } catch (Exception exception) {
                System.out.println("Movie " + movie.getTitle() + " doesn't have actors!");
            }

            try {
                //Dodavanje direktora
                if (!movie.getDirectors().isEmpty()) {
                    for (Person director : movie.getDirectors()) {
                        int personId = getOrCreatePersonId(director);
                        int movieId = stmt.getInt(9);

                        createMovieDirectors(movieId, personId);
                    }
                }
            } catch (Exception exception) {
                System.out.println("Movie " + movie.getTitle() + " doesn't have directors!");
            }
            return stmt.getInt(9);
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            for (Movie movie : movies) {
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getPublishedDate().format(Movie.DATE_FORMAT));
                stmt.setString(3, movie.getDescription());
                stmt.setString(4, movie.getOriginalTitle());
                stmt.setInt(5, movie.getLength());
                stmt.setString(6, movie.getPicturePath());
                stmt.setString(7, movie.getLink());
                stmt.setString(8, movie.getReleaseDate());

                stmt.registerOutParameter(9, Types.INTEGER);
                stmt.executeUpdate();

                try {
                    //Dodavanje zanrova
                    if (!movie.getGenres().isEmpty()) {
                        for (Genre genre : movie.getGenres()) {
                            int genreId = getOrCreateGenreId(genre);
                            int movieId = stmt.getInt(9);

                            createMovieGenres(movieId, genreId);
                        }
                    }
                } catch (Exception exception) {
                    System.out.println("Movie " + movie.getTitle() + " doesn't have genre!");
                }

                try {
                    //Dodavanje glumaca
                    if (!movie.getActors().isEmpty()) {
                        for (Person actor : movie.getActors()) {
                            int personId = getOrCreatePersonId(actor);
                            int movieId = stmt.getInt(9);

                            createMovieActors(movieId, personId);
                        }
                    }
                } catch (Exception exception) {
                    System.out.println("Movie " + movie.getTitle() + " doesn't have actors!");
                }

                try {
                    //Dodavanje direktora
                    if (!movie.getDirectors().isEmpty()) {
                        for (Person director : movie.getDirectors()) {
                            int personId = getOrCreatePersonId(director);
                            int movieId = stmt.getInt(9);

                            createMovieDirectors(movieId, personId);
                        }
                    }
                } catch (Exception exception) {
                    System.out.println("Movie " + movie.getTitle() + " doesn't have directors!");
                }
            }
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Set<Person> directors = selectMovieDirectors(rs.getInt(ID_MOVIE));
                    Set<Person> actors = selectMovieActors(rs.getInt(ID_MOVIE));
                    Set<Genre> genres = selectMovieGenres(rs.getInt(ID_MOVIE));

                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMAT),
                            rs.getString(MOVIE_DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            directors,
                            actors,
                            rs.getInt(MOVIE_LENGTH),
                            genres,
                            rs.getString(PICTURE_PATH),
                            rs.getString(LINK),
                            rs.getString(RELEASE_DATE)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Set<Person> directors = selectMovieDirectors(rs.getInt(ID_MOVIE));
                Set<Person> actors = selectMovieActors(rs.getInt(ID_MOVIE));
                Set<Genre> genres = selectMovieGenres(rs.getInt(ID_MOVIE));

                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUBLISHED_DATE), Movie.DATE_FORMAT),
                        rs.getString(MOVIE_DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        directors,
                        actors,
                        rs.getInt(MOVIE_LENGTH),
                        genres,
                        rs.getString(PICTURE_PATH),
                        rs.getString(LINK),
                        rs.getString(RELEASE_DATE)
                ));
            }
        }
        return movies;
    }

    @Override
    public void updateMovie(int id, Movie data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            deleteMovieActors(id);
            deleteMovieDirectors(id);
            deleteMovieDirectors(id);

            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getPublishedDate().format(Movie.DATE_FORMAT));
            stmt.setString(3, data.getDescription());
            stmt.setString(4, data.getOriginalTitle());
            stmt.setInt(5, data.getLength());
            stmt.setString(6, data.getPicturePath());
            stmt.setString(7, data.getLink());
            stmt.setString(8, data.getReleaseDate());

            stmt.setInt(9, id);
            stmt.executeUpdate();

            try {
                if (!data.getGenres().isEmpty()) {
                    for (Genre genre : data.getGenres()) {
                        int genreId = getOrCreateGenreId(genre);
                        int movieId = id;

                        createMovieGenres(movieId, genreId);
                    }
                }
            } catch (Exception exception) {
                System.out.println("Movie " + data.getTitle() + " doesn't have genre!");
            }

            try {
                if (!data.getActors().isEmpty()) {
                    for (Person actor : data.getActors()) {
                        int personId = getOrCreatePersonId(actor);
                        int movieId = id;

                        createMovieActors(movieId, personId);
                    }
                }
            } catch (Exception exception) {
                System.out.println("Movie " + data.getTitle() + " doesn't have actors!");
            }

            try {
                if (!data.getDirectors().isEmpty()) {
                    for (Person director : data.getDirectors()) {
                        int personId = getOrCreatePersonId(director);
                        int movieId = id;

                        createMovieDirectors(movieId, personId);
                    }
                }
            } catch (Exception exception) {
                System.out.println("Movie " + data.getTitle() + " doesn't have directors!");
            }
        }
    }

    @Override
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ALL)) {

            for (Movie movie : selectMovies()) {
                if (movie.getPicturePath() != null) {
                    Files.delete(Paths.get(movie.getPicturePath()));
                }
            }
            stmt.executeUpdate();
        }
    }
}
