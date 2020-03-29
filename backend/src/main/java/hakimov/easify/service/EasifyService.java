package hakimov.easify.service;

import org.springframework.stereotype.Service;
import hakimov.easify.repository.PostRepository;

@Service
public class EasifyService {
    private final PostRepository postRepository;

    public EasifyService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
