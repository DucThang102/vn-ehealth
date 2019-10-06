package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.List;
import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrChamSoc {

    public int id;
    
    public Integer idvaokhoa;
    
    public String sotochamsoc;
    public List<EmrQuaTrinhChamSoc> emrQuaTrinhChamSocs = new ArrayList<>();
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemChamSocs = new ArrayList<>();
    
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
    public String getSotochamsoc() {
        return sotochamsoc;
    }
    public List<EmrQuaTrinhChamSoc> getEmrQuaTrinhChamSocs() {
        return emrQuaTrinhChamSocs;
    }
    public Boolean getDaxoa() {
        return daxoa;
    }
    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemChamSocs() {
        return emrQuanLyFileDinhKemChamSocs;
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
