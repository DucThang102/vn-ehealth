package vn.ehealth.emr.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ehealth.emr.model.EmrPerson;
import vn.ehealth.emr.model.Role;
import vn.ehealth.emr.model.User;
import vn.ehealth.emr.repository.EmrPersonRepository;
import vn.ehealth.emr.repository.UserRepository;
import vn.ehealth.emr.utils.UserUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmrPersonRepository emrPersonRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Optional<User> getById(ObjectId id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(@Nonnull EmrPerson emrPerson, List<String> roleIds) {
        EmrPerson savedEmrPerson = emrPersonRepository.save(emrPerson);
        User user = new User();
        user.emrCoSoKhamBenhId = UserUtil.getCurrentUser().get().emrCoSoKhamBenhId;
        user.fullName = emrPerson.tendaydu;
        List<ObjectId> listRoles = new ArrayList<>();
        if (!roleIds.isEmpty()) {
            for (String roleId : roleIds) {
                listRoles.add(new ObjectId(roleId));
            }
        }
        user.roleIds = listRoles;
        user.emrPersonId = savedEmrPerson.id;
        user.username = emrPerson.email;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UUID uuid = UUID.randomUUID();
        String randomPassword = uuid.toString();
        user.password = passwordEncoder.encode(randomPassword);

		user = userRepository.save(user);
		savedEmrPerson.userId = user.id;
		emrPersonRepository.save(emrPerson);

		sendMailNotifyAccountInfo(emrPerson.email, emrPerson.tendaydu, randomPassword);
		return user;
    }

    public List<Role> getRolesByUsername(String username){
        Optional<User> user =  userRepository.findByUsername(username);
        return user.map(User::getRoles).orElse(null);
    }

    public void sendMailNotifyAccountInfo(String email, String name, String password) {
        String content = "Xin chào " + name
                + "Đây là thông tin tài khoản của bạn: \n"
                + "username: " + email
                + "\npassword: " + password
                + "\nVui lòng không cung cấp cho bất kỳ ai để bảo vệ tài khoản của bạn."
                + ".\n Truy cập http://localhost:8081/login để đăng nhập vào hệ thống.";
        emailService.sendEmail(email, "Thông báo thông tin tài khoản", content);
    }

}
