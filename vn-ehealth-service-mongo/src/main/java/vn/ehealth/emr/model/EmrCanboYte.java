package vn.ehealth.emr.model;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrCanboYte {
    public String ten;
    public String chungchihanhnghe;
    public ObjectId userId;
    public ObjectId emrPersonId;
}
