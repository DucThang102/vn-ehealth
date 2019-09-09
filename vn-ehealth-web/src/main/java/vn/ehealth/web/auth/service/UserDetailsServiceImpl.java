package vn.ehealth.web.auth.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	static class User {
		public String username;
		public String password;
		public List<String> roles;
		
		public User(String username, String password, List<String> roles) {
			this.username = username;
			this.password = password;
			this.roles = roles;					
		}
	}
	
	static Map<String, User> users = Map.of(
								"user", new User("user", "$2a$10$Kg1NNGVZ9K1YlNrgJsy6Q.TdqZfrC0Z5FmkjLXolLxQeOM0encmAa", List.of("USER")),
								 "admin", new User("admin", "$2a$10$Lp6eay3fKk9PcRUuDJ9CAedXtd9g6voBsdULDDyfWOiOPy1J1Xtqe", List.of("USER", "ADMIN")));

    
    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = users.get(username);
        if (user == null) throw new UsernameNotFoundException(username);
        
        var grantedAuthorities = new HashSet<GrantedAuthority>();
        for(var role : user.roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));            
        }

        return new org.springframework.security.core.userdetails.User(user.username, user.password, grantedAuthorities);
    }
}
