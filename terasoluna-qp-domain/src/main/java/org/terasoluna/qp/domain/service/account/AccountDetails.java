package org.terasoluna.qp.domain.service.account;

import java.util.Collection;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.terasoluna.qp.domain.model.Account;

public class AccountDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private final Account account;
	
	public AccountDetails(Account account) {
		this.account = account;
	}

	@Override
	public String getPassword() {
		return this.account.getPassword();
	}

	@Override
	public String getUsername() {
		return this.account.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.account.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.account.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.account.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		boolean enabled = this.isAccountNonExpired() && this.isAccountNonLocked() && this.isCredentialsNonExpired();
		return enabled;
	}

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return DEFAULT_AUTHORITIES;
//	}
	
	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.account.getAuthorities();
	}
	
	public Account getAccount()
	{
		return this.account;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0 == null || this.account == null){
			return false;
		}
		if(arg0.getClass()==AccountDetails.class){
			AccountDetails checkAccountDetails = ((AccountDetails)arg0); 
			if(checkAccountDetails.account == null){
				return false;
			}
			return this.getUsername().equals(checkAccountDetails.getUsername());
		} 
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.getUsername().hashCode();
	}
}
