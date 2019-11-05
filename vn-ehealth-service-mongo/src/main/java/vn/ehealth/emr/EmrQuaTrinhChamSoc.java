package vn.ehealth.emr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrQuaTrinhChamSoc {
    
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    public Date ngaychamsoc;

    public String ytachamsoc;

    public String theodoidienbien;

    public String thuchienylenh;
}
