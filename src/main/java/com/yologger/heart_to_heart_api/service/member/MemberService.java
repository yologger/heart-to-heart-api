package com.yologger.heart_to_heart_api.service.member;

import com.amazonaws.SdkClientException;
import com.yologger.heart_to_heart_api.common.util.AwsS3Uploader;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.repository.member.MemberRepository;
import com.yologger.heart_to_heart_api.service.member.model.UploadAvatarRequestDTO;
import com.yologger.heart_to_heart_api.service.member.model.UploadAvatarResponseDTO;
import lombok.RequiredArgsConstructor;
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
    private final AwsS3Uploader awsS3Uploader;

    @Transactional
    public ResponseEntity<UploadAvatarResponseDTO> uploadAvatar(UploadAvatarRequestDTO request) throws IOException, SdkClientException, EntityNotFoundException {

        Long memberId = request.getMemberId();
        MultipartFile file = request.getFile();

        if (!file.getContentType().startsWith("image")) {
            // Invalid Content Type
        }

        MemberEntity member = memberRepository.getById(memberId);

        String imageUrl = awsS3Uploader.upload(file);

        member.setAvatarUrl(imageUrl);

        UploadAvatarResponseDTO response = UploadAvatarResponseDTO.builder()
                .avatarUrl(imageUrl)
                .memberId(memberId)
                .build();

        return ResponseEntity.created(null).body(response);
    }
}
