package me.func.adapter.controller;

import lombok.RequiredArgsConstructor;
import me.func.adapter.dto.transfer.TransferRequest;
import me.func.domain.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Void> transfer(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TransferRequest request
    ) {
        transferService.transfer(
                Long.parseLong(userDetails.getUsername()),
                request.getToUserId(),
                request.getAmount()
        );
        return ResponseEntity.ok().build();
    }
} 