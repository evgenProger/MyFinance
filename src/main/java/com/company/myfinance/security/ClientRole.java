package com.company.myfinance.security;

import com.company.myfinance.entity.BankAccount;
import com.company.myfinance.entity.Client;
import com.company.myfinance.entity.Transaction;
import com.company.myfinance.entity.Type;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "ClientRole", code = "client-role", scope = "UI")
public interface ClientRole {
	@MenuPolicy(menuIds = {"BankAccount.browse", "Type_.browse", "Client.browse", "Report", "Transaction_.browse"})
	@ScreenPolicy(screenIds = {"BankAccount.browse", "BankAccount.edit", "LoginScreen", "MainScreen", "Type_.edit", "Type_.browse", "Client.browse", "Report", "ui_AddConditionScreen", "ui_GroupFilterCondition.edit", "ui_JpqlFilterCondition.edit", "ui_PropertyFilterCondition.edit", "ui_FilterConfigurationModel.fragment", "sec_RoleFilterFragment", "Transaction_.browse", "Transaction_.edit"})
	void screens();
	
	@EntityAttributePolicy(entityClass = Type.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
	@EntityPolicy(entityClass = Type.class, actions = EntityPolicyAction.ALL)
	void type();
	
	@EntityAttributePolicy(entityClass = Transaction.class, attributes = {"from_acc", "to_acc", "create_date",
			"transfer_amount", "types"}, action = EntityAttributePolicyAction.MODIFY)
	@EntityAttributePolicy(entityClass = Transaction.class, attributes = {"id", "version"}, action =
			EntityAttributePolicyAction.VIEW)
	@EntityPolicy(entityClass = Transaction.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
	void transaction();
	
	@EntityAttributePolicy(entityClass = BankAccount.class, attributes = {"name", "client", "amount"}, action = EntityAttributePolicyAction.MODIFY)
	@EntityAttributePolicy(entityClass = BankAccount.class, attributes = {"id", "version"}, action = EntityAttributePolicyAction.VIEW)
	@EntityPolicy(entityClass = BankAccount.class, actions = EntityPolicyAction.ALL)
	void bankAccount();
	
	@EntityAttributePolicy(entityClass = Client.class, attributes = {"client", "username"}, action = EntityAttributePolicyAction.MODIFY)
	@EntityAttributePolicy(entityClass = Client.class, attributes = {"id", "version", "password", "firstName", "lastName", "email", "active", "timeZoneId", "user"}, action = EntityAttributePolicyAction.VIEW)
	@EntityPolicy(entityClass = Client.class, actions = EntityPolicyAction.ALL)
	void client();
}