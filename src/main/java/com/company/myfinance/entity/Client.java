package com.company.myfinance.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@JmixEntity
@Entity
public class Client extends User {
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "client")
	private User user;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}