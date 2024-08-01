package pl.wylegala.library.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.wylegala.library.model.Movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieDao {
    private static final Logger LOG = Logger.getLogger(MovieDao.class.getName());
    private ObjectMapper objectMapper;

    public MovieDao() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Movie> findAll() {
        return getMovies();
    }

    private List<Movie> getMovies() {
        try {
            return objectMapper.readValue(Files.readString(Paths.get("./movies.txt")), new TypeReference<>() {
            });
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on getMovies", e);
            return new ArrayList<>();

        }
    }

    public void add(Movie book) {

        List<Movie> movies = getMovies();
        movies.add(book);
        save(movies);
    }

    private void save(List<Movie> movies) {
        try {
            Files.writeString(Paths.get("./movies.txt"), objectMapper.writeValueAsString(movies));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on save a movie");
        }
    }

    public Optional<Movie> findOne(String movieName) {
        return getMovies().stream().filter(b -> b.getName().equals(movieName))
                .findAny();
    }

    public void remove(Movie movie, String categoryName) {
        try {
            List<Movie> movies = getMovies();
            movies.removeIf(m -> m.getCategory().getName().equals(categoryName) && m.getName().equals(movie.getName()));
            save(movies);

        } catch (Exception e) {
            LOG.log(Level.WARNING, "Movie didn't remove ", e);
        }

    }
}
