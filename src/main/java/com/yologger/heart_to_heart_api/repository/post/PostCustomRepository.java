package com.yologger.heart_to_heart_api.repository.post;

import java.util.List;

public interface PostCustomRepository {
    List<PostEntity> findAllPostsOrderByCreatedAtDescExceptBlocking(Long memberId, int offset, int limit);
}
