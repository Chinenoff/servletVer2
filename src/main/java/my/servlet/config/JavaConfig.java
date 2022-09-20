package my.servlet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import my.servlet.controller.PostController;
import my.servlet.repository.PostRepository;
import my.servlet.service.PostService;

@Configuration
public class JavaConfig {
  @Bean
  // аргумент метода и есть DI
  // название метода - название бина
  public PostController postController(PostService service) {
    return new PostController(service);
  }

  @Bean
  public PostService postService(PostRepository repository) {
    return new PostService(repository);
  }

  @Bean
  public PostRepository postRepository() {
    return new PostRepository();
  }
}