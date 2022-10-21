package com.company.myfinance.security;

import com.company.myfinance.entity.BancAccount;
import com.company.myfinance.entity.Transaction;
import com.company.myfinance.entity.Type;
import com.company.myfinance.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Client", code = "client", scope = "UI")
public interface ClientRole {
    @MenuPolicy(menuIds = {"BancAccount.browse", "Transaction_.browse"})
    @ScreenPolicy(screenIds = {"BancAccount.browse", "Transaction_.browse", "Transaction_.edit", "BancAccount.edit"})
    void screens();

    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"username", "bank_account"}, action = EntityAttributePolicyAction.VIEW)
    void user();

    @EntityAttributePolicy(entityClass = Type.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void type();

    @EntityPolicy(entityClass = Transaction.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = Transaction.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void transaction();

    @EntityAttributePolicy(entityClass = BancAccount.class, attributes = {"amount", "user", "name"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = BancAccount.class, attributes = {"id", "version"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = BancAccount.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void bancAccount();
}