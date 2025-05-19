package com.pasi.pasilu_api.dtos.wallets_members;

import java.util.UUID;

public record WalletMemberUpdateRequest(
        UUID roleId
) {}
