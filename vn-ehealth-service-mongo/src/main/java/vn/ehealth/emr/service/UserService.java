package vn.ehealth.emr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ehealth.emr.model.EmrPerson;
import vn.ehealth.emr.model.User;
import vn.ehealth.emr.repository.UserRepository;

@Service
public class UserService {

    @Autowired UserRepository userRepository;
    
    @Autowired PasswordEncoder passwordEncoder;
    
    public Optional<User> getById(ObjectId id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User createUser(@Nonnull ObjectId emrCoSoKhamBenhId, @Nonnull EmrPerson emrPerson, String password, List<String> roleIds) { 
    	var user = userRepository.findByUsername(emrPerson.email).orElse(null);
    	user.emrCoSoKhamBenhId = emrCoSoKhamBenhId;
    	user.fullName = emrPerson.tendaydu;
    	List<ObjectId> listRoles = new ArrayList<>();
    	if (!roleIds.isEmpty()) {
    		for(String roleId : roleIds ){
    			listRoles.add(new ObjectId(roleId));
    		 }
    	}
    	user.roleIds = listRoles;	
    	user.emrPersonId = emrPerson.id;
    	user.username = emrPerson.email;
    	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	user.password =  passwordEncoder.encode(password);
    	
    	user = userRepository.save(user);
        return user;
    }
}
