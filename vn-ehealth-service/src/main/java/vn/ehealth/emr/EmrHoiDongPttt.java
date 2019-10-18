package vn.ehealth.emr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Transient;

@Entity
@Table(name = "emr_hoi_dong_pttt")
public class EmrHoiDongPttt {

    @Id public int id;
    
    @Column public Integer idpttt;
    
    @Column public Integer idvaitro;
    
    @Transient public EmrDm emrDmVaiTro;
    
    @Column public String tenbacsi;
    
    @Column public Boolean daxoa;

    public int getId() {
        return id;
    }

    public Integer getIdpttt() {
        return idpttt;
    }

    public Integer getIdvaitro() {
        return idvaitro;
    }

    public String getTenbacsi() {
        return tenbacsi;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }
}
