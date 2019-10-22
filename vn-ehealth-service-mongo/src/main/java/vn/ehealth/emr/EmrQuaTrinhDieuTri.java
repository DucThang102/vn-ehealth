package vn.ehealth.emr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrQuaTrinhDieuTri {

    public Date ngaydieutri;
    public String dienbien;
    public String chamsoc;
    public String ylenh;
    public String bacsiraylenh;        
}
