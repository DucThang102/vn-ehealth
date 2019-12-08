package vn.ehealth.emr.auth.service;

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
	
	public static class User {
		public String username;
		public String password;
		public String fullName;
		public String maDonVi;
		public List<String> roles;
		
		public User(String username, String password, List<String> roles, String fullName, String maDonVi) {
			this.username = username;
			this.password = password;
			this.roles = roles;					
			this.fullName = fullName;
			this.maDonVi = maDonVi;
		}
	}
	
	static Map<String, User> users = Map.of(
	                            "bvct", new User("bvct", "$2a$10$i0mK.rl/oG5GMKcdlDChY.VeVdYOVjJIXrexUcFcNp8tVGKetFhfa", List.of("USER"), "Bệnh viện đa khoa Cần Thơ", "92000"),
								"user", new User("user", "$2a$10$Kg1NNGVZ9K1YlNrgJsy6Q.TdqZfrC0Z5FmkjLXolLxQeOM0encmAa", List.of("USER"), "admin", "5980"),
								"admin", new User("admin", "$2a$10$Lp6eay3fKk9PcRUuDJ9CAedXtd9g6voBsdULDDyfWOiOPy1J1Xtqe", List.of("USER", "ADMIN"), "user", "5980"));

	public User getUser(String username) {
	    return users.get(username);
	}
    
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
