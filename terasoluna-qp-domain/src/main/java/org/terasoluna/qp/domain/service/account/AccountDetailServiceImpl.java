package org.terasoluna.qp.domain.service.account;


import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.terasoluna.qp.domain.model.Account;
import org.terasoluna.qp.domain.repository.account.AccountRepository;

public class AccountDetailServiceImpl implements UserDetailsService {
	
	@Inject
	AccountRepository accountRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Account account = accountRepository.findOneByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username + " is not found."); // TODO to property file
        }
        
        return new AccountDetails(account);
	}

}
