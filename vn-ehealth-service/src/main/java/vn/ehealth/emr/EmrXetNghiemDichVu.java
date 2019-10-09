package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_xet_nghiem_dich_vu")
public class EmrXetNghiemDichVu {
    
    @Id public int id;
    
    @Column public Integer idxetnghiem;
    
    @Column public Integer iddmxetnghiem;
    
    @Transient public EmrDm emrDmXetNghiem;
    
    @Transient public List<EmrXetNghiemKetQua> emrXetNghiemKetQuas = new ArrayList<>();
    
    @Column public Boolean daxoa;
    
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
