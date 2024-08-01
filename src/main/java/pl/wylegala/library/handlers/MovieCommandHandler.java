package pl.wylegala.library.handlers;

import pl.wylegala.library.dao.CategoryDao;
import pl.wylegala.library.dao.MovieDao;
import pl.wylegala.library.input.UserInputCommand;
import pl.wylegala.library.model.Category;
import pl.wylegala.library.model.Movie;

import java.util.List;
import java.util.logging.Logger;

public class MovieCommandHandler extends BaseCommandHandler {
    private static final String COMMAND_NAME = "movie";

    private static Logger LOG = Logger.getLogger(MovieCommandHandler.class.getName());
    private MovieDao movieDao;
    private CategoryDao categoryDao;

    public MovieCommandHandler() {
        this.movieDao = new MovieDao();
        this.categoryDao = new CategoryDao();
    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void handle(UserInputCommand command) {
        if (command.getAction() == null) {
            throw new IllegalArgumentException("action can't be null");
        }
        switch (command.getAction()) {
            case LIST -> {
                if (!command.getParams().isEmpty()) {
                    throw new IllegalArgumentException("Movie list doesn't support any additional params");
                }
                List<Movie> books = movieDao.findAll();
                books.forEach(System.out::println);
                LOG.info("List of movies..");
            }
            case ADD -> {
                if (command.getParams().size() != 2) {
                    throw new IllegalArgumentException("Wrong command format. Check help for more information");
                }
                String movieName = command.getParams().get(0);
                String categoryName = command.getParams().get(1);
                Category category = categoryDao.findOne(categoryName)
                        .orElseThrow(() -> new IllegalArgumentException("Catgory not found " + categoryName));
                movieDao.add(new Movie(movieName, category));
                LOG.info("Movie added to category " + category.getName());
            }
            case DEL -> {
                String removedMovie = command.getParams().get(0);
                String categoryName = command.getParams().get(1);
                Movie movie = movieDao.findOne(removedMovie)
                        .orElseThrow(() -> new IllegalArgumentException("movie not found: " + removedMovie));
                movieDao.remove(movie, categoryName);
                LOG.info("Movie removed: " + movie.toString());
            }
            default -> {
                throw new IllegalArgumentException(String.format("Unknow action %s from command %s",
                        command.getAction(), command.getCommand()));
            }
        }

    }
}
