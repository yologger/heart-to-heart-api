package com.yologger.heart_to_heart_api.controller.member;

import com.amazonaws.SdkClientException;
import com.yologger.heart_to_heart_api.controller.member.exception.AwsS3Exception;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidContentTypeException;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidMemberIdException;
import com.yologger.heart_to_heart_api.service.member.MemberService;
import com.yologger.heart_to_heart_api.service.member.model.*;
import com.yologger.heart_to_heart_api.service.post.model.ReportRequestDTO;
import com.yologger.heart_to_heart_api.service.post.model.ReportResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

        return new ResponseEntity<>(memberService.uploadAvatar(request), HttpStatus.CREATED);
    }

    @PostMapping(value = "/block")
    public ResponseEntity<BlockMemberResponseDTO> blockMember(@Valid @RequestBody BlockMemberRequestDTO request) throws InvalidMemberIdException, DataIntegrityViolationException {
        return new ResponseEntity<>(memberService.block(request), HttpStatus.OK);
    }

    @PostMapping(value = "/unblock")
    public ResponseEntity<UnblockMemberResponseDTO> unblockMember(@Valid @RequestBody UnblockMemberRequestDTO request) throws InvalidMemberIdException {
        return new ResponseEntity<>(memberService.unblock(request), HttpStatus.OK);
    }

    @GetMapping(value = "/getBlockingMembers")
    public ResponseEntity<GetBlockingMembersResponseDTO> getBlockingMembers(
            @Valid @NotNull @RequestParam(value = "member_id", required = true) Long memberId
    ) throws InvalidMemberIdException {
        return new ResponseEntity<>(memberService.getBlockingMember(memberId), HttpStatus.OK);
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<GetMemberInfoResponseDTO> getProfile(
            @Valid @NotNull @RequestParam(value = "member_id", required = true) Long memberId
    ) throws InvalidMemberIdException {
        return new ResponseEntity<>(memberService.getMemberInfo(memberId), HttpStatus.OK);
    }

    @PostMapping(value = "/report")
    public ResponseEntity<ReportResponseDTO> report(@Valid @RequestBody ReportRequestDTO request) {
        ReportResponseDTO response = ReportResponseDTO.builder()
                .memberId(request.getMemberId())
                .targetId(request.getTargetId())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<DeleteAccountResponseDTO> deleteMember(@Valid @NotNull @PathVariable Long id) throws InvalidMemberIdException, AwsS3Exception {
        return new ResponseEntity<>(memberService.deleteAccount(id), HttpStatus.NO_CONTENT);
    }
}