package com.yologger.heart_to_heart_api.controller.member;

import com.amazonaws.SdkClientException;
import com.yologger.heart_to_heart_api.common.base.ErrorResponseDto;
import com.yologger.heart_to_heart_api.controller.member.exception.MemberErrorCode;
import com.yologger.heart_to_heart_api.service.member.MemberService;
import com.yologger.heart_to_heart_api.service.member.model.UploadAvatarRequestDTO;
import com.yologger.heart_to_heart_api.service.member.model.UploadAvatarResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/uploadAvatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadAvatarResponseDTO> uploadAvatar(
            @Valid @RequestPart(value = "file", required = true) MultipartFile file,
            @Valid @RequestParam("member_id") Long memberId
    ) throws IOException, SdkClientException, EntityNotFoundException {

        UploadAvatarRequestDTO request = UploadAvatarRequestDTO.builder()
                .memberId(memberId)
                .file(file)
                .build();

        return memberService.uploadAvatar(request);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDto> handleIOException(IOException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(MemberErrorCode.IO_ERROR.getCode())
                .message(MemberErrorCode.IO_ERROR.getMessage())
                .status(MemberErrorCode.IO_ERROR.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(MemberErrorCode.IO_ERROR.getStatus()));
    }

    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<ErrorResponseDto> handleSdkClientException(SdkClientException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(MemberErrorCode.FILE_UPLOAD_ERROR.getCode())
                .message(MemberErrorCode.FILE_UPLOAD_ERROR.getMessage())
                .status(MemberErrorCode.FILE_UPLOAD_ERROR.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(MemberErrorCode.FILE_UPLOAD_ERROR.getStatus()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException e) {
        final ErrorResponseDto response = ErrorResponseDto.builder()
                .code(MemberErrorCode.MEMBER_NOT_EXIST.getCode())
                .message(MemberErrorCode.MEMBER_NOT_EXIST.getMessage())
                .status(MemberErrorCode.MEMBER_NOT_EXIST.getStatus())
                .build();
        return new ResponseEntity(response, HttpStatus.valueOf(MemberErrorCode.MEMBER_NOT_EXIST.getStatus()));
    }
}