package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_qua_trinh_dieu_tri")
public class EmrQuaTrinhDieuTri {

    @Id public int id;
    
    @Column public Integer iddieutri;
    
    @Column public Date ngaydieutri;
    @Column public String dienbien;
    @Column public String chamsoc;
    @Column public String ylenh;
    @Column public String bacsiraylenh;
    @Column public Boolean daxoa;
    
    public int getId() {
        return id;
    }
    
    public Integer getIddieutri() {
        return iddieutri;
    }
    
    public Date getNgaydieutri() {
        return ngaydieutri;
    }
    
    public String getDienbien() {
        return dienbien;
    }
    
    public String getChamsoc() {
        return chamsoc;
    }
    
    public String getYlenh() {
        return ylenh;
    }
    
    public String getBacsiraylenh() {
        return bacsiraylenh;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }
}
