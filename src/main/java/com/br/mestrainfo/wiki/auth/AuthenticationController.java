package com.br.mestrainfo.wiki.auth;

import com.br.mestrainfo.wiki.post.Post;
import com.br.mestrainfo.wiki.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final PostRepository postRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/posts")
    public List<Post> getPosts () {
        return postRepository.findAll();
    }

    @GetMapping("/slider")
    public List<Post> getPostsSlider(@RequestParam(defaultValue = "3") int quantity, @RequestParam(defaultValue = "slider") String category) {
        Pageable pageable = PageRequest.of(0, quantity, Sort.by("id").descending());
        return postRepository.findByCategory(category, pageable);
    }
}
