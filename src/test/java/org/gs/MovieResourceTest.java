package org.gs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class MovieResourceTest {

  @InjectMock
  MovieRepository movieRepository;

  @Test
  void getAll() {
    when(movieRepository.listAll()).thenReturn(List.of(new Movie()));
    given()
        .when().get("/movies")
        .then()
        .statusCode(200)
        .body("$.size()", equalTo(1));
  }

  @Test
  void getByIdOK() {
    Movie movie = new Movie();
    movie.setId(1L);
    when(movieRepository.findByIdOptional(1L)).thenReturn(Optional.of(movie));
    given()
        .when().get("/movies/1")
        .then()
        .statusCode(200)
        .body("id", equalTo(1));
  }

  @Test
  void getByIdKO() {
    when(movieRepository.findByIdOptional(1L)).thenReturn(Optional.empty());
    given()
        .when().get("/movies/1")
        .then()
        .statusCode(404);
  }

  @Test
  void getByTitleOK() {
    Movie movie = new Movie();
    movie.setId(1L);
    movie.setTitle("The Shawshank Redemption");
    when(movieRepository.find("title", "The Shawshank Redemption")).thenReturn(Mockito.mock(PanacheQuery.class));
    when(movieRepository.find("title", "The Shawshank Redemption").singleResultOptional()).thenReturn(Optional.of(movie));
    given()
        .when().get("/movies/title/The Shawshank Redemption")
        .then()
        .statusCode(200)
        .body("id", equalTo(1));
  }

  @Test
  void getByTitleKO() {
    when(movieRepository.find("title", "The Shawshank Redemption")).thenReturn(Mockito.mock(PanacheQuery.class));
    when(movieRepository.find("title", "The Shawshank Redemption").singleResultOptional()).thenReturn(Optional.empty());
    given()
        .when().get("/movies/title/The Shawshank Redemption")
        .then()
        .statusCode(404);
  }

  @Test
  void getByCountry() {
    when(movieRepository.findByCountry("USA")).thenReturn(List.of(new Movie()));
    given()
        .when().get("/movies/country/USA")
        .then()
        .statusCode(200)
        .body("$.size()", equalTo(1));
  }

  @Test
  void createOK() {
    Movie movie = new Movie();
    movie.setId(1L);
    when(movieRepository.isPersistent(any())).thenReturn(true);
    given()
        .contentType("application/json")
        .body(movie)
        .when().post("/movies")
        .then()
        .statusCode(201)
        .header("Location", "/movies/1");
  }

  @Test
  void createKO() {
    Movie movie = new Movie();
    when(movieRepository.isPersistent(any())).thenReturn(false);
    given()
        .contentType("application/json")
        .body(movie)
        .when().post("/movies")
        .then()
        .statusCode(400);
  }

  @Test
  void updateByIdOK() {
    Movie movie = new Movie();
    movie.setId(1L);
    movie.setTitle("Updated Title");
    when(movieRepository.findByIdOptional(1L)).thenReturn(Optional.of(movie));
    given()
        .contentType("application/json")
        .body(movie)
        .when().put("/movies/1")
        .then()
        .statusCode(200)
        .body("id", equalTo(1))
        .body("title", equalTo("Updated Title"));
  }

  @Test
  void updateByIdKO() {
    when(movieRepository.findByIdOptional(1L)).thenReturn(Optional.empty());
    given()
        .contentType("application/json")
        .body(new Movie())
        .when().put("/movies/1")
        .then()
        .statusCode(404);
  }

  @Test
  void deleteByIdOK() {
    when(movieRepository.deleteById(1L)).thenReturn(true);
    given()
        .when().delete("/movies/1")
        .then()
        .statusCode(204);
  }

  @Test
  void deleteByIdKO() {
    when(movieRepository.deleteById(1L)).thenReturn(false);
    given()
        .when().delete("/movies/1")
        .then()
        .statusCode(404);
  }
}