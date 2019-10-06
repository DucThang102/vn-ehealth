package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrHoiChan {

    public int id;
    
    public Integer idvaokhoa;
    
    public Date ngaythuchien;
    public String tomtatdienbien;
    public String ketluanhoichan;
    public String huongdieutri;
    public Boolean daxoa;
    public List<EmrHoiDongHoiChan> emrHoiDongHoiChans = new ArrayList<>();
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemHoiChans = new ArrayList<>();
    
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
    public Date getNgaythuchien() {
        return ngaythuchien;
    }
    public String getTomtatdienbien() {
        return tomtatdienbien;
    }
    public String getKetluanhoichan() {
        return ketluanhoichan;
    }
    public String getHuongdieutri() {
        return huongdieutri;
    }
    public Boolean getDaxoa() {
        return daxoa;
    }
    public List<EmrHoiDongHoiChan> getEmrHoiDongHoiChans() {
        return emrHoiDongHoiChans;
    }
    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemHoiChans() {
        return emrQuanLyFileDinhKemHoiChans;
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
