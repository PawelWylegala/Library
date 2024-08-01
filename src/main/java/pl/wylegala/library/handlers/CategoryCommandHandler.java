package pl.wylegala.library.handlers;

import pl.wylegala.library.dao.CategoryDao;
import pl.wylegala.library.input.UserInputCommand;
import pl.wylegala.library.model.Category;

import java.util.List;
import java.util.logging.Logger;

public class CategoryCommandHandler extends BaseCommandHandler {

    private final static String COMMAND_NAME = "category";
    private static final Logger LOG = Logger.getLogger(CategoryCommandHandler.class.getName());
    private CategoryDao categoryDao;

    public CategoryCommandHandler() {
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

            case LIST:
                if (!command.getParams().isEmpty()) {
                    throw new IllegalArgumentException("Category list doesn't support any additional params");
                }

                LOG.info("Category list...");
                List<Category> categories = categoryDao.findAll();
                categories.forEach(System.out::println);
                break;

            case ADD:
                if (command.getParams().size() != 1) {
                    throw new IllegalArgumentException("Wrong command format. Check help for more information");


                }
                String categoryName = command.getParams().get(0);
                categoryDao.add(new Category(categoryName));
                LOG.info("Category added ...");
                break;
            case DEL:
                String removedCategory = command.getParams().get(0);
                Category category = categoryDao.findOne(removedCategory)
                        .orElseThrow(() -> new IllegalArgumentException("categoryName not found " + removedCategory));
                categoryDao.removeCategory(category);
                LOG.info("Category removed: " + removedCategory);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown action %s from command %s",
                        command.getAction(), command.getCommand()));

        }
    }


}
