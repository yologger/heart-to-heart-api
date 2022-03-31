package com.yologger.heart_to_heart_api.service.member;

import com.amazonaws.SdkClientException;
import com.yologger.heart_to_heart_api.common.util.AwsS3Uploader;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidContentTypeException;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidMemberIdException;
import com.yologger.heart_to_heart_api.repository.block.BlockEntity;
import com.yologger.heart_to_heart_api.repository.block.BlockRepository;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.service.member.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BlockRepository blockRepository;
    private final AwsS3Uploader awsS3Uploader;

    @Transactional
    public ResponseEntity<UploadAvatarResponseDTO> uploadAvatar(UploadAvatarRequestDTO request) throws IOException, SdkClientException, EntityNotFoundException, InvalidContentTypeException {

        PageRequest pageRequest = PageRequest.of(0, 10);


        Long memberId = request.getMemberId();
        MultipartFile file = request.getFile();

        if (!file.getContentType().startsWith("image")) throw new InvalidContentTypeException("content type must start with image");

        MemberEntity member = memberRepository.getById(memberId);

        String imageUrl = awsS3Uploader.upload(file);

        member.setAvatarUrl(imageUrl);

        UploadAvatarResponseDTO response = UploadAvatarResponseDTO.builder()
                .avatarUrl(imageUrl)
                .memberId(memberId)
                .build();

        return ResponseEntity.created(null).body(response);
    }

    @Transactional
    public ResponseEntity<BlockMemberResponseDTO> block(BlockMemberRequestDTO request) throws InvalidMemberIdException, DataIntegrityViolationException {
        MemberEntity member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new InvalidMemberIdException("Invalid 'member_id'"));
        MemberEntity target = memberRepository.findById(request.getTargetId()).orElseThrow(() -> new InvalidMemberIdException("Invalid 'target_id'"));
        BlockEntity block = BlockEntity.builder()
                .member(member)
                .blocking(target)
                .build();

        blockRepository.save(block);

        BlockMemberResponseDTO response = BlockMemberResponseDTO.builder()
                .memberId(member.getId())
                .targetId(target.getId())
                .build();

        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<UnblockMemberResponseDTO> unblock(UnblockMemberRequestDTO request) throws InvalidMemberIdException {
        MemberEntity member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new InvalidMemberIdException("Invalid 'member_id'"));
        MemberEntity target = memberRepository.findById(request.getTargetId()).orElseThrow(() -> new InvalidMemberIdException("Invalid 'target_id'"));

        blockRepository.deleteByMemberAndTarget(member, target);

        UnblockMemberResponseDTO response = UnblockMemberResponseDTO.builder()
                .memberId(member.getId())
                .targetId(target.getId())
                .build();

        return ResponseEntity.ok(response);
    }
}
