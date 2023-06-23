package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    public static final String APPLICATION_JSON = "application/json"; //MY
    private final PostService service;

    private final Gson gson = new Gson();//MY


    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public void getById(@PathVariable long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        Post post = service.getById(id);
        response.getWriter().print(gson.toJson(post));
        //return service.getById(id);
    }

    @PostMapping
    public Post save(@RequestBody Post post) throws Exception {
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id, HttpServletResponse response) throws IOException {
        //response.setContentType("text/html;charset=utf-8");
        response.setContentType(APPLICATION_JSON);
        service.removeById(id);
        response.getWriter().print("Post with id = " + id + " is deleted");

        //service.removeById(id);
    }
}
