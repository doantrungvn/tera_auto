package org.terasoluna.qp.app.account;

import java.io.Serializable;

public class AccountSearchForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private boolean[] accountNonLocked = new boolean[0];
	private boolean[] accountNonExpired = new boolean[0];
	private boolean[] credentialsNonExpired = new boolean[0];

	public boolean[] getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean[] accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean[] getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean[] accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean[] getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean[] credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
