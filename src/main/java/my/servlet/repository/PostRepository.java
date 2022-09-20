package my.servlet.repository;

import my.servlet.exception.NotFoundException;
import my.servlet.model.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PostRepository {

    private static Long counter = Long.valueOf(0);

    //private final static Generator generator = new Generator();
    private final static Map<Long, Post> postMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(postMap.values());
    }

    public Optional<Post> getById(long id) {
        if (postMap.get(id) == null) throw new NotFoundException("post with id " + id + "not found");
        return Optional.ofNullable(postMap.get(id));
    }

    public Post save(Post post) {
        if (!postMap.containsKey(post.getId())) {
            /*var genId = getNewId();
            post.setId(genId);*/
            postMap.put(post.getId(), post);
        } else {
            long newID = getNewId();
            post.setId(newID);
            postMap.put(newID, post);
        }
        return post;
    }

    public void removeById(long id) {
        if (postMap.get(id) == null) throw new NotFoundException();
        postMap.remove(id);
    }

    public static synchronized Long getNewId() {
        return counter++;
    }
}
