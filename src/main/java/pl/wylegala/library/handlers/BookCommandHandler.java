package pl.wylegala.library.handlers;

import pl.wylegala.library.dao.BookDao;
import pl.wylegala.library.dao.CategoryDao;
import pl.wylegala.library.input.UserInputCommand;
import pl.wylegala.library.model.Book;
import pl.wylegala.library.model.Category;

import java.util.List;
import java.util.logging.Logger;

public class BookCommandHandler extends BaseCommandHandler {
    private static final String COMMAND_NAME = "book";
    private static final Logger LOG = Logger.getLogger(BookDao.class.getName());
    private CategoryDao categoryDao;
    private BookDao bookDao;

    public BookCommandHandler() {
        this.categoryDao = new CategoryDao();
        this.bookDao = new BookDao();
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
                    throw new IllegalArgumentException("Book list doesn't support any additional params");
                }
                LOG.info("List of books...");
                List<Book> books = bookDao.findAll();
                books.forEach(System.out::println);
            }
            case ADD -> {
                if (command.getParams().size() != 2) {
                    throw new IllegalArgumentException("Wrong command format. Check help for more information");
                }
                String bookName = command.getParams().get(0);
                String categoryName = command.getParams().get(1);
                Category category = categoryDao.findOne(categoryName)
                        .orElseThrow(() -> new IllegalArgumentException("Catgory not found " + categoryName));
                bookDao.add(new Book(bookName, category));
                LOG.info("Book added to category " + category.getName());
            }
            case DEL -> {
                String removedBook = command.getParams().get(0);
                String categoryName = command.getParams().get(1);
                Book book = bookDao.findOne(removedBook)
                        .orElseThrow(() -> new IllegalArgumentException("book not found: " + removedBook));
                bookDao.remove(book, categoryName);
                LOG.info("Book removed: " + book.toString());
            }
            default -> {
                throw new IllegalArgumentException(String.format("Unknow action %s from command %s",
                        command.getAction(), command.getCommand()));
            }
        }

    }
}
