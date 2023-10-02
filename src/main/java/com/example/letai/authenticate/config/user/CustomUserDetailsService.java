package com.example.letai.authenticate.config.user;


import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;
		
			
		Optional<UserEntity> user = userDao.findByEmail(username);
		if (user.isPresent()) {
			roles = Arrays.asList(new SimpleGrantedAuthority(String.valueOf(user.get().getAppUserRole())));
			return new User(user.get().getEmail(), user.get().getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);	}

	public UserEntity save(UserDTO user) {
		UserEntity newUser = new UserEntity();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setAppUserRole(user.getAppUserRole());
		return userDao.save(newUser);
	}

}
