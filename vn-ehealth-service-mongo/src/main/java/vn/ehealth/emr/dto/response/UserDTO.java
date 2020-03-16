package vn.ehealth.emr.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import vn.ehealth.emr.model.EmrPerson;
import vn.ehealth.emr.model.Role;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private ObjectId id;
    private ObjectId emrCoSoKhamBenhId;
    private String username;
    private String password;
    private List<Role> roles;
    private EmrPerson emrPerson;
}