package me.func.adapter.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import me.func.adapter.dto.email.request.EmailDeleteRequest;
import me.func.adapter.dto.email.request.EmailPutRequest;
import me.func.adapter.dto.email.request.EmailUpdateRequest;
import me.func.domain.service.EmailService;
import me.func.util.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PutMapping
    public ResponseEntity<Void> updateEmail(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody EmailUpdateRequest request
    ) {
        emailService.updateEmail(SecurityUtil.extractUserId(userDetails), request.getOldEmail(), request.getNewEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> addEmail(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody EmailPutRequest request
    ) {
        emailService.addEmail(SecurityUtil.extractUserId(userDetails), request.getEmail());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmail(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody EmailDeleteRequest request
    ) {
        emailService.deleteEmail(SecurityUtil.extractUserId(userDetails), request.getEmail());
        return ResponseEntity.ok().build();
    }
} 