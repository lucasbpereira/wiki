package com.br.mestrainfo.wiki.post;

import com.br.mestrainfo.wiki.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findAllByTitle(String title);

    List<Post> findByCategory(String category, Pageable pageable);
}
