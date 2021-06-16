package za.co.iherridge0.microservices.netflixzuulapigatewayserver.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import za.co.iherridge0.microservices.netflixzuulapigatewayserver.entity.Login;
import za.co.iherridge0.microservices.netflixzuulapigatewayserver.repository.UserRepository;

@Service
public class JwtJPAUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Login> findUser = userRepository.findById(username);

		if (!findUser.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return new JwtUserDetails(1L, username, findUser.get().getPassword(), "ROLE_USER_2");

	}

}
