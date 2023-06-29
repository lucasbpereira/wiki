package com.br.mestrainfo.wiki.post;

import com.br.mestrainfo.wiki.auth.AuthenticationRequest;
import com.br.mestrainfo.wiki.auth.AuthenticationResponse;
import com.br.mestrainfo.wiki.auth.AuthenticationService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;
    private final PostRepository postRepository;

    @Autowired
    private ServletContext servletContext;

    @PostMapping
    public ResponseEntity<String> createPost(@ModelAttribute Post post,
                                             @RequestParam("imagemFile") MultipartFile imagemFile) {
        if (post.getTitle() == null || post.getBody() == null || post.getCategory() == null ||
                post.getCreated_at() == null || post.getUpdated_at() == null || imagemFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios.");
        }

        // Processa o arquivo de imagem
        try {
            String fileName = imagemFile.getOriginalFilename();
            String uploadDir = servletContext.getRealPath("/") + "/uploads/";
            File uploadPath = new File(uploadDir);

            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            Path filePath = uploadPath.toPath().resolve(fileName);
            Files.copy(imagemFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            post.setImage(uploadDir + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a imagem.");
        }

        postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body("Postagem criada com sucesso.");
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponse> create (
            @RequestBody PostRequest request
    ) {
        return ResponseEntity.ok(service.post(request));

    }

}
