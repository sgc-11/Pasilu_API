package com.pasi.pasilu_api.dtos.wallets_members;

import java.util.UUID;

public record WalletMemberResponse(
        UUID memberId,
        UUID walletId,
        UUID userId,
        String userName,
        UUID roleId,
        String roleName
) {}
