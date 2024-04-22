package org.terasoluna.qp.domain.service.account;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.terasoluna.qp.app.common.ultils.FunctionCommon;
import org.terasoluna.qp.domain.model.Account;

public class UserDetailsServiceImpl implements UserDetailsService {
	

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Account account = new Account();
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_APP");
		authorities.add(grantedAuthority);
		account.setUsername(FunctionCommon.getOauth2Username());
		account.setPassword(FunctionCommon.getOauth2Password());
		account.setAccountNonExpired(true);
		account.setAccountNonLocked(true);
		account.setCredentialsNonExpired(true);
		account.setAuthorities((List<? extends GrantedAuthority>)authorities);
		
        AccountDetails accountDetails = new  AccountDetails(account);
		
		UserDetails user = (UserDetails) accountDetails;

		return new User(user.getUsername(), user.getPassword(),
				user.isEnabled(), user.isAccountNonExpired(),
				user.isCredentialsNonExpired(), user.isAccountNonLocked(),
				user.getAuthorities());	
	}

}
