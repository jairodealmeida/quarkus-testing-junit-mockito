package org.gs;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * Represents a RESTful resource for managing movie entities.
 * <p>
 * This class handles HTTP requests related to movie entities, such as retrieving all movies,
 * retrieving a movie by its ID or title, creating a new movie, updating a movie, and deleting a movie.
 * </p>
 * <p>
 * The class is annotated with the JAX-RS Path annotation to specify the base path of the resource ("/movies").
 * It is also annotated with the JAX-RS Produces and Consumes annotations to specify the media type of the request and response bodies (JSON).
 * </p>
 * <p>
 * The class uses the MovieRepository bean to interact with the database and perform CRUD operations on movie entities.
 * </p>
 */
@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

  /**
   * The MovieRepository bean that provides the persistence logic for movie entities.
   */
  @Inject MovieRepository movieRepository;

  /**
   * Handles HTTP GET requests to retrieve all movie entities.
   * <p>
   * This method handles HTTP GET requests to the base path of the resource.
   * It retrieves all movie entities from the database and returns them in the response body.
   * </p>
   * <p>
   * The method is annotated with the JAX-RS GET annotation to specify that it handles HTTP GET requests.
   * The method is also annotated with the JAX-RS Path annotation to specify the base path of the resource.
   * The method is annotated with the JAX-RS Produces annotation to specify the media type of the response body.
   * </p>
   * <p>
   * The method uses the MovieRepository bean to retrieve all movie entities from the database.
   * The method returns a response with the list of movie entities in the response body.
   * </p>
   * 
   * 
   */
  @GET
  public Response getAll() {
    List<Movie> movies = movieRepository.listAll();
    return Response.ok(movies).build();
  }

  /**
   * Handles HTTP GET requests to retrieve a movie entity by its ID.
   * <p>
   * This method handles HTTP GET requests to the path of the resource with an ID parameter.
   * It retrieves the movie entity with the specified ID from the database and returns it in the response body.
   * </p>
   * <p>
   * The method is annotated with the JAX-RS GET annotation to specify that it handles HTTP GET requests.
   * The method is also annotated with the JAX-RS Path annotation to specify the path of the resource with an ID parameter.
   * The method is annotated with the JAX-RS Produces annotation to specify the media type of the response body.
   * </p>
   * <p>
   * The method uses the MovieRepository bean to find the movie entity by its ID.
   * If the movie entity is found, the method returns a response with the movie entity in the response body.
   * If the movie entity is not found, the method returns a response with a 404 Not Found status.
   * </p>
   * 
   */
  @GET
  @Path("{id}")
  public Response getById(@PathParam("id") Long id) {
    return movieRepository
        .findByIdOptional(id)
        .map(movie -> Response.ok(movie).build())
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Handles HTTP GET requests to retrieve a movie entity by its title.
   * <p>
   * This method handles HTTP GET requests to the path of the resource with a title parameter.
   * It retrieves the movie entity with the specified title from the database and returns it in the response body.
   * </p>
   * <p>
   * The method is annotated with the JAX-RS GET annotation to specify that it handles HTTP GET requests.
   * The method is also annotated with the JAX-RS Path annotation to specify the path of the resource with a title parameter.
   * The method is annotated with the JAX-RS Produces annotation to specify the media type of the response body.
   * </p>
   * <p>
   * The method uses the MovieRepository bean to find the movie entity by its title.
   * If the movie entity is found, the method returns a response with the movie entity in the response body.
   * If the movie entity is not found, the method returns a response with a 404 Not Found status.
   * </p>
   * 
   */
  @GET
  @Path("title/{title}")
  public Response getByTitle(@PathParam("title") String title) {
    return movieRepository
        .find("title", title)
        .singleResultOptional()
        .map(movie -> Response.ok(movie).build())
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Handles HTTP GET requests to retrieve movie entities by their country.
   * <p>
   * This method handles HTTP GET requests to the path of the resource with a country parameter.
   * It retrieves all movie entities with the specified country from the database and returns them in the response body.
   * </p>
   * <p>
   * The method is annotated with the JAX-RS GET annotation to specify that it handles HTTP GET requests.
   * The method is also annotated with the JAX-RS Path annotation to specify the path of the resource with a country parameter.
   * The method is annotated with the JAX-RS Produces annotation to specify the media type of the response body.
   * </p>
   * <p>
   * The method uses the MovieRepository bean to find all movie entities by their country.
   * The method returns a response with the list of movie entities in the response body.
   * </p>
   * 
   */
  @GET
  @Path("country/{country}")
  public Response getByCountry(@PathParam("country") String country) {
    List<Movie> movies = movieRepository.findByCountry(country);
    return Response.ok(movies).build();
  }

  /**
   * Handles HTTP POST requests to create a movie entity.
   * <p>
   * This method handles HTTP POST requests to the base path of the resource.
   * It creates a movie entity with the specified data in the request body and persists it to the database.
   * </p>
   * <p>
   * The method is annotated with the JAX-RS POST annotation to specify that it handles HTTP POST requests.
   * The method is also annotated with the JAX-RS Path annotation to specify the base path of the resource.
   * The method is annotated with the JAX-RS Consumes annotation to specify the media type of the request body.
   * </p>
   * <p>
   * The method uses the MovieRepository bean to persist the movie entity to the database.
   * If the movie entity is successfully persisted, the method returns a response with a 201 Created status
   * and the URI of the created movie entity in the Location header.
   * If the movie entity is not successfully persisted, the method returns a response with a 400 Bad Request status.
   * </p>
   * 
   */
  @POST
  @Transactional
  public Response create(Movie movie) {
    movieRepository.persist(movie);
    if (movieRepository.isPersistent(movie)) {
      return Response.created(URI.create("/movies/" + movie.getId())).build();
    }
    return Response.status(BAD_REQUEST).build();
  }

  /** 
   * Handles HTTP PUT requests to put a movie entity by its ID.
   * PUT /movies/{id} 
   * <p>
   * This method handles HTTP PUT requests to the path of the resource with an ID parameter.
   * It updates the movie entity with the specified ID using the data in the request body. 
   * </p>
   * <p>
   * The method is annotated with the JAX-RS PUT annotation to specify that it handles HTTP PUT requests.
   * The method is also annotated with the JAX-RS Path annotation to specify the path of the resource with an ID parameter.
   * The method is annotated with the JAX-RS Consumes annotation to specify the media type of the request body.
   * </p>
   * <p>
   * The method uses the MovieRepository bean to update the movie entity by its ID.
   * If the movie entity is successfully updated, the method returns a response with the updated movie entity in the response body.
   * If the movie entity is not found, the method returns a response with a 404 Not Found status.
   * </p>
   * 
   */

  @PUT
  @Path("{id}")
  @Transactional
  public Response updateById(@PathParam("id") Long id, Movie movie) {
    return movieRepository
        .findByIdOptional(id)
        .map(
            m -> {
              m.setTitle(movie.getTitle());
              return Response.ok(m).build();
            })
        .orElse(Response.status(NOT_FOUND).build());
  }

  /**
   * Handles HTTP DELETE requests to delete a movie entity by its ID.
   * DELETE /movies/{id} 
   * <p>
   * This method handles HTTP DELETE requests to the path of the resource with an ID parameter.
   * It deletes the movie entity with the specified ID from the database.
   * </p>
   * <p>
   * The method is annotated with the JAX-RS DELETE annotation to specify that it handles HTTP DELETE requests.
   * The method is also annotated with the JAX-RS Path annotation to specify the path of the resource with an ID parameter.
   * </p>
   * <p>
   * The method uses the MovieRepository bean to delete the movie entity by its ID.
   * If the movie entity is successfully deleted, the method returns a response with a 204 No Content status.
   * If the movie entity is not found, the method returns a response with a 404 Not Found status.
   * </p>
  */

  @DELETE
  @Path("{id}")
  @Transactional
  public Response deleteById(@PathParam("id") Long id) {
    boolean deleted = movieRepository.deleteById(id);
    return deleted ? Response.noContent().build() : Response.status(NOT_FOUND).build();
  }
}
