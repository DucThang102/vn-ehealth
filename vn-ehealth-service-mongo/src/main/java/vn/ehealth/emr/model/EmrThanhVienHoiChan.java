package vn.ehealth.emr.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrThanhVienHoiChan {
    
    public String tenbacsi;

    //public Integer idvaitro;
    
    public EmrDmContent emrDmVaiTro;

}
