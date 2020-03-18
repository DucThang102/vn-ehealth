package vn.ehealth.emr.dto.request;

import lombok.Getter;
import lombok.Setter;
import vn.ehealth.emr.model.EmrPerson;

import java.util.List;

@Getter
@Setter
public class UserUpdateDTO {
    private String id;
    private List<String> roleIds;
    private EmrPerson emrPerson;
}
