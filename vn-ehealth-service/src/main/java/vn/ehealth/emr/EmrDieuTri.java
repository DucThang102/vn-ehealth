package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrDieuTri {
    
    public int id;
    
    public Integer idvaokhoa;
    
    public String sotodieutri;
    public List<EmrQuaTrinhDieuTri> emrQuaTrinhDieuTris = new ArrayList<>();
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemDieuTris = new ArrayList<>();
    
    // From EmrVaoKhoa
    public String tenKhoa;
    public String giuong;
    public String phong;
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
