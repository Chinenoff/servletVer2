package my.servlet.repository;

import my.servlet.exception.NotFoundException;
import my.servlet.model.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {

    private static Long counter= Long.valueOf(0);
    private AtomicLong counterAtomicLong = new AtomicLong(1);;

    private final static Map<Long, Post> postMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(postMap.values());
    }

    public Optional<Post> getById(long id) {
        if (postMap.get(id) == null) throw new NotFoundException("post with id " + id + "not found");
        return Optional.ofNullable(postMap.get(id));
    }

    public Post save(Post post) {
        System.out.println(post.getId());
        if (!postMap.containsKey(post.getId())) {
            postMap.put(post.getId(), post);
        } else {
            long newID = counterAtomicLong.getAndIncrement();
            while (postMap.containsKey(newID)){
                newID = counterAtomicLong.getAndIncrement();
            }
            post.setId(newID);
            postMap.put(newID, post);
        }
        return post;
    }

    public void removeById(long id) {
        if (postMap.get(id) == null) throw new NotFoundException();
        postMap.remove(id);
    }
}
