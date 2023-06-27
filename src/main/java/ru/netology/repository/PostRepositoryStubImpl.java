package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {

    private final ConcurrentHashMap<Long, Post> postRepo = new ConcurrentHashMap<>();//MY
    private final AtomicLong idCounter = new AtomicLong(1);//MY

    public List<Post> all() {

        //return Collections.emptyList();
        return new ArrayList<>(postRepo.values());
    }

    public Optional<Post> getById(long id) {

        return Optional.of(postRepo.get(id));
    }

    public Post save(Post post) throws Exception {

        long postID = post.getId();

        //long postIndexMax = Collections.max(postRepo.keySet());

        //     if (postID == 0) {
        //          long newPostId = idCounter.getAndIncrement();
        //          post.setId(newPostId);
        //          postRepo.put(newPostId, post);
        //      } else {
        //          if (postRepo.containsKey(postID)) {
        //              throw new Exception("Error write!!! Such ID already occupied");
        //          }

        if (postID == 0 && postRepo.size() == 0) {
            long newPostId = idCounter.getAndIncrement();
            post.setId(newPostId);
            postRepo.put(newPostId, post);
        } else if (postRepo.containsKey(postID) || postID == 0) {
            long newPostID = idCounter.getAndIncrement();
            post.setId(newPostID);
            postRepo.put(newPostID, post);
        } else {
            //Привязываемся к idCounter,исключаем ситуацию удаления поста по id и потом заведение поста с таким же id (переписывание поста)
            long newPostId = idCounter.getAndIncrement();
            post.setId(newPostId);
            postRepo.put(newPostId, post);
        }
        return post;
    }

    public void removeById(long id) {
        postRepo.remove(id);
    }
}