package my.servlet.controller;

import com.google.gson.Gson;
import my.servlet.exception.NotFoundException;
import my.servlet.model.Post;
import my.servlet.service.PostService;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@Controller
public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.all();
    final var gson = new Gson();
    response.getWriter().print("List saved posts: " + gson.toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    Post post = null;
    try {
      post = service.getById(id);
    } catch (NotFoundException ex) {
      response.setStatus(SC_NOT_FOUND);
    }
    response.getWriter().print("Post was found: " + gson.toJson(post));
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var post = gson.fromJson(body, Post.class);
    final var data = service.save(post);
    response.getWriter().print("Post saved: " +  gson.toJson(data));
  }

  public void removeById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    try {
      service.removeById(id);
    } catch (NotFoundException ex) {
      response.setStatus(SC_NOT_FOUND);
    }
    response.getWriter().print("Post [" + id + "] removed");
  }
}
