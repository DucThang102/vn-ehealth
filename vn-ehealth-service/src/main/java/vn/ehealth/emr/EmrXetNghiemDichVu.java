package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

public class EmrXetNghiemDichVu {
    
    public int id;
    
    public Integer idxetnghiem;
    
    public Integer iddmxetnghiem;
    public EmrDm emrDmXetNghiem;
    
    public List<EmrXetNghiemKetQua> emrXetNghiemKetQuas = new ArrayList<>();
    public Boolean daxoa;
    
    public int getId() {
        return id;
    }
    public Integer getIdxetnghiem() {
        return idxetnghiem;
    }
    public Integer getIddmxetnghiem() {
        return iddmxetnghiem;
    }
    public EmrDm getEmrDmXetNghiem() {
        return emrDmXetNghiem;
    }
    public List<EmrXetNghiemKetQua> getEmrXetNghiemKetQuas() {
        return emrXetNghiemKetQuas;
    }
    public Boolean getDaxoa() {
        return daxoa;
    }   
    
}
