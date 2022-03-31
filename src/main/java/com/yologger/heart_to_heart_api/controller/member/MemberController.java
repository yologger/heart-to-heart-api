package com.yologger.heart_to_heart_api.controller.member;

import com.amazonaws.SdkClientException;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidContentTypeException;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidMemberIdException;
import com.yologger.heart_to_heart_api.service.member.MemberService;
import com.yologger.heart_to_heart_api.service.member.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/uploadAvatar")
    public ResponseEntity<UploadAvatarResponseDTO> uploadAvatar(
            @Valid @NotNull @RequestParam("member_id") Long memberId,
            @Valid @RequestPart(value = "file", required = true) MultipartFile file
    ) throws IOException, SdkClientException, EntityNotFoundException, InvalidContentTypeException {

        UploadAvatarRequestDTO request = UploadAvatarRequestDTO.builder()
                .memberId(memberId)
                .file(file)
                .build();

        return memberService.uploadAvatar(request);
    }

    @PostMapping(value = "/block")
    public ResponseEntity<BlockMemberResponseDTO> blockMember(@Valid @RequestBody BlockMemberRequestDTO request) throws InvalidMemberIdException, DataIntegrityViolationException {
        return memberService.block(request);
    }

    @PostMapping(value = "/unblock")
    public ResponseEntity<UnblockMemberResponseDTO> unblockMember(@Valid @RequestBody UnblockMemberRequestDTO request) throws InvalidMemberIdException {
        return memberService.unblock(request);
    }
}