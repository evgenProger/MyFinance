package com.company.myfinance.security;

import com.company.myfinance.entity.*;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.model.RowLevelPredicate;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

@RowLevelRole(name = "ClientRlRole", code = "client-rl-role")
public interface ClientRlRole {
    @JpqlRowLevelPolicy(
            entityClass = BankAccount.class,
            where = "{E}.client.user.id = :current_user_id")
    void bankAccount();

    @JpqlRowLevelPolicy(
            entityClass = Client.class,
            where = "{E}.user.id = :current_user_id")
    void client();


    @PredicateRowLevelPolicy(
            entityClass = Transaction.class,
            actions = {RowLevelPolicyAction.READ})
    default RowLevelPredicate<Transaction> customerDetailNotConfidential(CurrentAuthentication auth) {
        return transaction -> {
            UserDetails userDetails = auth.getUser();
            if (userDetails instanceof User) {
                UUID id =  ((User) userDetails).getId();
                BankAccount to = transaction.getTo_acc();
                BankAccount from = transaction.getFrom_acc();
                if (to == null)  {
                    return id.equals(from.getClient().getUser().getId());
                }
                if (from == null) {
                    return id.equals(to.getClient().getUser().getId());
                }
                return id.equals(from.getClient().getUser().getId())
                        || id.equals(to.getClient().getUser().getId());
            }
            return false;
        };
    }

    @JpqlRowLevelPolicy(
            entityClass = Type.class,
            where = "{E}.client.user.id = :current_user_id")
    void type();
}
