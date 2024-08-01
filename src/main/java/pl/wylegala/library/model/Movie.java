package pl.wylegala.library.model;

public class Movie {
    private String name;
    private Category category;

    public Movie() {
    }

    public Movie(String name, Category category) {
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
        return "Movie{" +
                "name='" + name + '\'' +
                ", category=" + category +
                '}';
    }

}
