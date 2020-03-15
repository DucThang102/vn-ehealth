package vn.ehealth.emr.model;

import java.util.List;

public class UserRequestDTO {
    private List<String> roleIds;
    private EmrPerson emrPerson;

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public EmrPerson getEmrPerson() {
        return emrPerson;
    }

    public void setEmrPerson(EmrPerson emrPerson) {
        this.emrPerson = emrPerson;
    }
}
