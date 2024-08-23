package org.gs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents a movie entity.
 */
@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(length = 200)
    private String description;

    private String director;

    private String country;

    /**
     * Retrieves the title of the movie.
     *
     * @return The title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title The title of the movie.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the description of the movie.
     *
     * @return The description of the movie.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the movie.
     *
     * @param description The description of the movie.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the director of the movie.
     *
     * @return The director of the movie.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Sets the director of the movie.
     *
     * @param director The director of the movie.
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Retrieves the country of the movie.
     *
     * @return The country of the movie.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the movie.
     *
     * @param country The country of the movie.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the ID of the movie.
     *
     * @return The ID of the movie.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the movie.
     *
     * @param id The ID of the movie.
     */
    public void setId(Long id) {
        this.id = id;
    }
}