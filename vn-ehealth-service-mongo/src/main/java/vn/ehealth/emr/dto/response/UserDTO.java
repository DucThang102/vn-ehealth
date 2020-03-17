package vn.ehealth.emr.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.ehealth.emr.model.EmrPerson;
import vn.ehealth.emr.model.Role;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String emrCoSoKhamBenhId;
    private String username;
    private String password;
    private List<Role> roles;
    private EmrPerson emrPerson;
}