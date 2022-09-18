package repository;

import exception.NotFoundException;
import model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {
    private final static Map<Long, Post> postMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(postMap.values());
    }

    public Optional<Post> getById(long id) {
        if (postMap.get(id) == null) throw new NotFoundException("post with id " + id + "not found");
        return Optional.ofNullable(postMap.get(id));
    }

    public Post save(Post post) {
        if (postMap.get(post.getId()) == null) postMap.put(post.getId(), post);
        else {
            long newID = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
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
