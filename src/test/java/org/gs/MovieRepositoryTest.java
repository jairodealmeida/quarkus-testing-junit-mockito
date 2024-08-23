package org.gs;

import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@QuarkusTest
class MovieRepositoryTest {

  @Inject
  MovieRepository movieRepository;

  @Test
  void findByCountryOK() {
    List<Movie> movies = movieRepository.findByCountry("USA");
    assertNotNull(movies);
    assertFalse(movies.isEmpty());
    assertEquals(3, movies.size());


  }

  @Test
  void findByCountryKO() {
    List<Movie> movies = movieRepository.findByCountry("Spain");
    assertNotNull(movies);
    assertTrue(movies.isEmpty());
  }
}
