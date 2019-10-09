package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Entity
@Table(name = "emr_dieu_tri")
public class EmrDieuTri {
    
    @Id public int id;
    
    @Column public Integer idvaokhoa;
    
    @Column public String sotodieutri;
    
    @Transient public List<EmrQuaTrinhDieuTri> emrQuaTrinhDieuTris = new ArrayList<>();
    
    @Column public Boolean daxoa;
    
    @Transient public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemDieuTris = new ArrayList<>();
    
    // From EmrVaoKhoa
    @Transient public String tenKhoa;
    @Transient public String giuong;
    @Transient public String phong;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdvaokhoa() {
        return idvaokhoa;
    }
    
    public String getSotodieutri() {
        return sotodieutri;
    }
    
    public List<EmrQuaTrinhDieuTri> getEmrQuaTrinhDieuTris() {
        return emrQuaTrinhDieuTris;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }
    
    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemDieuTris() {
        return emrQuanLyFileDinhKemDieuTris;
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
