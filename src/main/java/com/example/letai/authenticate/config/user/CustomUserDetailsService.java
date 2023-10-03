package com.example.letai.authenticate.config.user;

import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.dto.converter.UserConverter;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.model.entity.enums.AppUserRole;
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

	@Autowired
	private UserConverter userConverter;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;
		
			
		Optional<UserEntity> user = userDao.findByEmail(username);
		if (user.isPresent()) {
			roles = Arrays.asList(new SimpleGrantedAuthority(user.get().getRole().toString()));
			return new User(user.get().getEmail(), user.get().getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);	}
	
	public UserEntity save(UserDTO user) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		UserEntity newUser = new UserEntity();
		newUser = userConverter.toEntity(user);
		return userDao.save(newUser);
	}

}
