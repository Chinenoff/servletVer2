package my.servlet.servlet;

import my.servlet.controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import my.servlet.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private AnnotationConfigApplicationContext context;
    private PostService service;

    @Override
    public void init() {

        final var context = new AnnotationConfigApplicationContext("my.servlet");

        final var repository = context.getBean("postRepository");
        final var service = context.getBean(PostService.class);
        controller = context.getBean(PostController.class);

    /*var context = new AnnotationConfigApplicationContext();
    context.scan("my.servlet");
    context.refresh();*//*
    // отдаём список пакетов, в которых нужно искать аннотированные классы
    context = new AnnotationConfigApplicationContext("my.servlet"); //ru.netology

    // получаем по имени бина
    controller = context.getBean("postController");

    // получаем по классу бина
    service = context.getBean(PostService.class);

    // по умолчанию создаётся лишь один объект на BeanDefinition
    //final var isSame = service == context.getBean("postService");

    *//*final var my.servlet.repository = new PostRepository();
    final var my.servlet.service = new PostService(my.servlet.repository);
    my.servlet.controller = new PostController(my.servlet.service);*/
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals("GET") && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }
            if (method.equals("GET") && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
                return;
            }
            if (method.equals("POST") && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals("DELETE") && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}

