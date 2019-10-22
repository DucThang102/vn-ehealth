package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.ehealth.emr.file.EmrFileDinhKem;

@Entity
@Table(name = "emr_cham_soc")
public class EmrChamSoc {

    @Id
    public int id;
    
    @Column
    public Integer idvaokhoa;
    
    @Column
    public String sotochamsoc;
        
    @Transient public List<EmrQuaTrinhChamSoc> emrQuaTrinhChamSocs = new ArrayList<>();
    
    @Column
    public Boolean daxoa;
    
    @Transient
    public List<EmrFileDinhKem> emrFileDinhKemChamSocs = new ArrayList<>();
    
    // From EmrVaoKhoa
    @Transient
    public String tenKhoa;
    
    @Transient
    public String giuong;
    
    @Transient
    public String phong;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdvaokhoa() {
        return idvaokhoa;
    }
    
    public String getSotochamsoc() {
        return sotochamsoc;
    }
    
    public List<EmrQuaTrinhChamSoc> getEmrQuaTrinhChamSocs() {
        return emrQuaTrinhChamSocs;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }
    
    public List<EmrFileDinhKem> getEmrQuanLyFileDinhKemChamSocs() {
        return emrFileDinhKemChamSocs;
    }
    
    public String getTenKhoa() {
        return tenKhoa;
    }
    
    public String getGiuong() {
        return giuong;
    }
    
    public String getPhong() {
        return phong;
    }    
}
