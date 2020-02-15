package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;

@Service
public class EasifyService {
    private final PostRepository postRepository;

    public EasifyService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
