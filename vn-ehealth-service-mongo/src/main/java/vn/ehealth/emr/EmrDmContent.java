package vn.ehealth.emr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrDmContent {
    
    public String ma = "";
    public String ten = "";
    
    public String maicd = "";

}
