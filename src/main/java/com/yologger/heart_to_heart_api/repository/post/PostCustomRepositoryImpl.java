package com.yologger.heart_to_heart_api.repository.post;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.yologger.heart_to_heart_api.repository.post.QPostEntity.postEntity;
import static com.yologger.heart_to_heart_api.repository.block.QBlockEntity.blockEntity;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PostEntity> findAllPostsOrderByCreatedAtDescExceptBlocking(Long memberId, int page, int size) {
        return jpaQueryFactory.selectFrom(postEntity)
                .where(postEntity.writer.id.notIn(
                        JPAExpressions
                                .select(blockEntity.blocking.id)
                                .from(blockEntity)
                                .where(blockEntity.member.id.eq(memberId))
                ))
                .offset(page*size)
                .limit(size)
                .orderBy(postEntity.createdAt.desc())
                .fetch();
    }

    @Override
    public List<PostEntity> findAllByWriterId(Long memberId, int page, int size) {
        return jpaQueryFactory.selectFrom(postEntity)
                .where(postEntity.writer.id.eq(memberId))
                .offset(page*size)
                .limit(size)
                .orderBy(postEntity.createdAt.desc())
                .fetch();
    }
}
