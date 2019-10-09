package vn.ehealth.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_xet_nghiem_ket_qua")
public class EmrXetNghiemKetQua {

    @Id public int id;
    
    @Column public Integer idthongdich;
    @Transient public EmrDm emrDmDichKetQuaXetNghiem;
    
    @Column public Integer idchisoxetnghiem;
    @Transient public EmrDm emrDmChiSoXetNghiem;
    
    @Column public Integer iddmxetnghiem;
    @Transient public EmrDm emrDmXetNghiem;
    
    @Column public Integer iddichvuxetnghiem;
    
    @Column public Integer idxetnghiem;
    
    @Column public String giatrido;
    
    @Column public Boolean daxoa;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdthongdich() {
        return idthongdich;
    }
    
    public EmrDm getEmrDmDichKetQuaXetNghiem() {
        return emrDmDichKetQuaXetNghiem;
    }
    
    public Integer getIdchisoxetnghiem() {
        return idchisoxetnghiem;
    }
    
    public EmrDm getEmrDmChiSoXetNghiem() {
        return emrDmChiSoXetNghiem;
    }
    
    public Integer getIddmxetnghiem() {
        return iddmxetnghiem;
    }
    
    public EmrDm getEmrDmXetNghiem() {
        return emrDmXetNghiem;
    }
    
    public Integer getIddichvuxetnghiem() {
        return iddichvuxetnghiem;
    }
    public Integer getIdxetnghiem() {
        return idxetnghiem;
    }
    
    public String getGiatrido() {
        return giatrido;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }    
    
}
