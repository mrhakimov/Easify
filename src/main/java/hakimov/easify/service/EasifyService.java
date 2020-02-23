package hakimov.easify.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EasifyService {
    private final PostRepository postRepository;

    public EasifyService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
