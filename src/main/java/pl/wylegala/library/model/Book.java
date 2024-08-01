package pl.wylegala.library.model;

public class Book {

    private String name;
    private Category category;

    public Book() {
    }

    public Book(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", category=" + category +
                '}';
    }

}
