package pl.wylegala.library.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.wylegala.library.model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDao {
    private static final Logger LOG = Logger.getLogger(BookDao.class.getName());
    private ObjectMapper objectMapper;

    public BookDao() {
        this.objectMapper = new ObjectMapper();
    }

    public List<Book> findAll() {
        return getBooks();
    }

    private List<Book> getBooks() {
        try {
            return objectMapper.readValue(Files.readString(Paths.get("./books.txt")), new TypeReference<>() {
            });
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on getBooks", e);
            return new ArrayList<>();

        }
    }

    public void add(Book book) {

        List<Book> books = getBooks();
        books.add(book);
        save(books);
    }

    private void save(List<Book> books) {
        try {
            Files.writeString(Paths.get("./books.txt"), objectMapper.writeValueAsString(books));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on save a book");
        }
    }

    public Optional<Book> findOne(String removedBook) {
        return getBooks().stream().filter(b -> b.getName().equals(removedBook))
                .findAny();
    }

    public void remove(Book book, String categoryName) {
        try {
            List<Book> books = getBooks();
            books.removeIf(b -> b.getCategory().getName().equals(categoryName) && b.getName().equals(book.getName()));
            save(books);

        } catch (Exception e) {
            LOG.log(Level.WARNING, "Didn't remove a book ", e);
        }

    }
}
