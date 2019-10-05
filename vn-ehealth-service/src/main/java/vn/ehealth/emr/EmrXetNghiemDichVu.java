package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class EmrXetNghiemDichVu {
    
    public int id;
    
    public Integer idxetnghiem;
    
    public Integer iddmxetnghiem;
    public EmrDm emrDmXetNghiem;
    
    public List<EmrXetNghiemKetQua> emrXetNghiemKetQuas = new ArrayList<>();
    public Boolean daxoa;

}
