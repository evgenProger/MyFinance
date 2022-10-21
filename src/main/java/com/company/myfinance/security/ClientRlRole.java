package com.company.myfinance.security;

import com.company.myfinance.entity.BancAccount;
import com.company.myfinance.entity.User;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(
        name = "client bank",
        code = "client-rl")
public interface ClientRlRole {
    @JpqlRowLevelPolicy(
            entityClass = BancAccount.class,
            where = "{E}.user.id = :current_user_id")
    void bank_account();

    @JpqlRowLevelPolicy(
            entityClass = User.class,
            where = "{E}.bank_account.user.id = :current_user_id")
    void user();
}
