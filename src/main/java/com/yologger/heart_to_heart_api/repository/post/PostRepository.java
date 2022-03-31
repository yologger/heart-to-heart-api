package com.yologger.heart_to_heart_api.repository.post;

import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long>, PostCustomRepository {
    public List<PostEntity> findAllByWriter(MemberEntity writer);
}
