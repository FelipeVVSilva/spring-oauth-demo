package com.demo.oauth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.oauth.entities.Role;
import com.demo.oauth.entities.User;
import com.demo.oauth.projections.UserDetailsProjection;
import com.demo.oauth.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(email);
		
		if(!result.isEmpty()) {
			User user = new User();
			
			user.setEmail(email);
			user.setPassword(result.get(0).getPassword());
			
			for (UserDetailsProjection projection : result) {
				user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
			}
			
			
			
		}
		throw new UsernameNotFoundException("User not found");
	}

	
	
}
