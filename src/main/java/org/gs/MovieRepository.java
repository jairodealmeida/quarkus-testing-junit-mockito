package org.gs;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Repository class for managing Movie entities.
 */
@ApplicationScoped
public class MovieRepository implements PanacheRepository<Movie> {

  /**
   * Retrieves a list of movies based on the specified country.
   *
   * @param country The country to filter the movies by.
   * @return A list of movies matching the specified country, ordered by ID in descending order.
   */
  public List<Movie> findByCountry(String country) {
    return list("SELECT m FROM Movie m WHERE m.country = ?1 ORDER BY id DESC", country);
  }
}
