package com.yologger.heart_to_heart_api.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long>, PostCustomRepository {
}
