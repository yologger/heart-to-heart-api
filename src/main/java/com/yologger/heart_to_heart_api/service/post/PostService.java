package com.yologger.heart_to_heart_api.service.post;

import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.repository.post.PostEntity;
import com.yologger.heart_to_heart_api.repository.post.PostRepository;
import com.yologger.heart_to_heart_api.service.post.exception.InvalidWriterIdException;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostRequestDto;
import com.yologger.heart_to_heart_api.service.post.model.RegisterPostResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseEntity<RegisterPostResponseDto> registerPost(RegisterPostRequestDto request) throws InvalidWriterIdException {
        // Check if memberId is valid.
        Optional<MemberEntity> result = memberRepository.findById(request.getMemberId());
        if (!result.isPresent()) {
            throw new InvalidWriterIdException("Invalid Member Id.");
        }

        // Save post.
        MemberEntity writer = result.get();
        PostEntity newPost = PostEntity.builder()
                .writer(writer)
                .content(request.getContent())
                .build();

        PostEntity saved = postRepository.save(newPost);

        RegisterPostResponseDto responseDto = RegisterPostResponseDto.builder()
                .postId(saved.getId())
                .content(saved.getContent())
                .writerId(writer.getId())
                .build();

        return ResponseEntity.created(null).body(responseDto);
    }
}
