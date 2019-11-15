package vn.ehealth.emr.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class EmrXetNghiemDichVu {
    
    public EmrDmContent emrDmXetNghiem;
    
    public List<EmrXetNghiemKetQua> emrXetNghiemKetQuas = new ArrayList<>();
      
}
