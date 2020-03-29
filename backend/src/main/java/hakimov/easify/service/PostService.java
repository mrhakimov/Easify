package hakimov.easify.service;

import org.springframework.stereotype.Service;
import hakimov.easify.domain.Post;
import hakimov.easify.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }
}
