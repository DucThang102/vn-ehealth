package vn.ehealth.emr.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.ehealth.emr.dto.response.ListResultDTO;
import vn.ehealth.emr.dto.response.UserDTO;
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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmrPersonRepository emrPersonRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> getById(ObjectId id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(@Nonnull EmrPerson emrPerson, List<String> roleIds, String password) {
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
        user.password = passwordEncoder.encode(password);

        user = userRepository.save(user);
        savedEmrPerson.userId = user.id;
        emrPersonRepository.save(emrPerson);

        return user;
    }

    public List<Role> getRolesByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getRoles).orElse(null);
    }

    public ListResultDTO<UserDTO> findAll(Integer page, Integer pageSize) {
        ListResultDTO<UserDTO> resultDTO = new ListResultDTO<>();
        List<UserDTO> listUserDTO = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<User> rawData = userRepository.findAll(pageable);
        if (rawData.hasContent()) {
            rawData.getContent().forEach(user -> {
                EmrPerson emrPerson = emrPersonRepository.findById(user.emrPersonId).orElse(new EmrPerson());
                UserDTO userDTO = new UserDTO();
                userDTO.setEmrCoSoKhamBenhId(user.emrCoSoKhamBenhId.toHexString());
                userDTO.setId(user.id.toHexString());
                userDTO.setUsername(user.username);
                userDTO.setPassword(user.password);
                userDTO.setRoles(user.getRoles());
                userDTO.setEmrPerson(emrPerson);
                listUserDTO.add(userDTO);
            });
            resultDTO = new ListResultDTO<>(listUserDTO, rawData.getTotalElements(), rawData.getTotalPages());
        }
        return resultDTO;
    }

    public ListResultDTO<UserDTO> search(String roleId, String keyword, Integer page, Integer pageSize) {
        ListResultDTO<UserDTO> resultDTO;
        List<UserDTO> listUserDTO = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<EmrPerson> rawData = emrPersonRepository.findByEmailOrDienthoaiOrTendaydu(keyword, keyword, keyword, pageable);
        if (rawData.hasContent()) {
            rawData.getContent().forEach(emrPerson -> {
                User user = userRepository.findById(emrPerson.userId).orElse(new User());
                user.getRoles().forEach(role -> {
                    if (role.id.toHexString().equals(roleId)) {
                        UserDTO userDTO = new UserDTO();
                        userDTO.setEmrCoSoKhamBenhId(user.emrCoSoKhamBenhId.toHexString());
                        userDTO.setId(user.id.toHexString());
                        userDTO.setUsername(user.username);
                        userDTO.setPassword(user.password);
                        userDTO.setRoles(user.getRoles());
                        userDTO.setEmrPerson(emrPerson);
                        listUserDTO.add(userDTO);
                    }
                });
            });
        }
        resultDTO = new ListResultDTO<>(listUserDTO, rawData.getTotalElements(), rawData.getTotalPages());
        return resultDTO;
    }

    public UserDTO findById(String id) {
        UserDTO userDTO = new UserDTO();
        Optional<User> optionalUser = userRepository.findById(new ObjectId(id));
        optionalUser.ifPresent(user -> {
            EmrPerson emrPerson = emrPersonRepository.findById(user.emrPersonId).orElse(new EmrPerson());
            userDTO.setEmrCoSoKhamBenhId(user.emrCoSoKhamBenhId.toHexString());
            userDTO.setId(user.id.toHexString());
            userDTO.setUsername(user.username);
            userDTO.setPassword(user.password);
            userDTO.setRoles(user.getRoles());
            userDTO.setEmrPerson(emrPerson);
        });
        return userDTO;
    }
}
