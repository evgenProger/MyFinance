package com.company.myfinance.entity;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

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
	
	@JpqlRowLevelPolicy(
			entityClass = Transaction.class,
			where = "({E}.from_acc is not null and {E}.from_acc.client.user.id = :current_user_id)"
					+ " or "
					+ "({E}.from_acc is not null and {E}.to_acc_id.client.user.id = :current_user_id")
	void tran();

	@JpqlRowLevelPolicy(
			entityClass = Type.class,
			where = "{E}.client.user.id = :current_user_id")
	void type();
}
