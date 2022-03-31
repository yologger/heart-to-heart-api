package com.yologger.heart_to_heart_api.repository.post;

import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    public List<PostEntity> findAllByWriter(MemberEntity writer);
}
