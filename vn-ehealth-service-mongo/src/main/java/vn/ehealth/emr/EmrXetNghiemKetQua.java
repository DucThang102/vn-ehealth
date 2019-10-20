package vn.ehealth.emr;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrXetNghiemKetQua {
    
    public EmrDmContent emrDmDichKetQuaXetNghiem;
    
    public EmrDmContent emrDmChiSoXetNghiem;
    
    //public EmrDmContent emrDmXetNghiem;

    public String giatrido;
}
