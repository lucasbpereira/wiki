package com.br.mestrainfo.wiki.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PostService {

    public final PostRepository repository;

    public PostResponse post(PostRequest request) {
        var post = Post.builder()
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .body(request.getBody())
                .category(request.getCategory())
                .created_at(new Date())
                .updated_at(new Date())
                .build();
        repository.save(post);
        return PostResponse
                .builder()
                .message("Postagem criada")
                .build();
    }
}
