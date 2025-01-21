package me.func.adapter.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import me.func.adapter.dto.phone.request.PhoneDeleteRequest;
import me.func.adapter.dto.phone.request.PhonePutRequest;
import me.func.adapter.dto.phone.request.PhoneUpdateRequest;
import me.func.domain.service.PhoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import me.func.util.SecurityUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/phones")
@RequiredArgsConstructor
public class PhoneController {
    private final PhoneService phoneService;

    @PutMapping
    public ResponseEntity<Void> updatePhone(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PhoneUpdateRequest request
    ) {
        phoneService.updatePhone(SecurityUtil.extractUserId(userDetails), request.getOldPhone(), request.getNewPhone());
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> addPhone(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PhonePutRequest request
    ) {
        phoneService.addPhone(SecurityUtil.extractUserId(userDetails), request.getPhone());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePhone(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody PhoneDeleteRequest request
    ) {
        phoneService.deletePhone(SecurityUtil.extractUserId(userDetails), request.getPhone());
        return ResponseEntity.ok().build();
    }
} 