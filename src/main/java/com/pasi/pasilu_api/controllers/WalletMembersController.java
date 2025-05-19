package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.wallets_members.*;
import com.pasi.pasilu_api.services.WalletMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets/{walletId}/members")
@RequiredArgsConstructor
public class WalletMembersController {

    private final WalletMemberService memberService;

    /* ---- LIST ---- */
    @GetMapping
    public List<WalletMemberResponse> list(@PathVariable UUID walletId) {
        return memberService.listMembers(walletId);
    }

    /* ---- ADD ---- */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletMemberResponse add(@PathVariable UUID walletId,
                                    @Valid @RequestBody WalletMemberCreateRequest body) {
        return memberService.addMember(walletId, body);
    }

    /* ---- UPDATE ROLE ---- */
    @PutMapping("/{memberId}")
    public WalletMemberResponse updateRole(@PathVariable UUID walletId,
                                           @PathVariable UUID memberId,
                                           @Valid @RequestBody WalletMemberUpdateRequest body) {
        return memberService.updateRole(walletId, memberId, body);
    }

    /* ---- REMOVE ---- */
    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID walletId,
                       @PathVariable UUID memberId) {
        memberService.removeMember(walletId, memberId);
    }
}
