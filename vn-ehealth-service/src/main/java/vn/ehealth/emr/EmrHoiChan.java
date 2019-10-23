package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.ehealth.emr.file.EmrFileDinhKem;

@Entity
@Table(name = "emr_hoi_chan")
public class EmrHoiChan {

    @Id
    public int id;
    
    @Column
    public Integer idvaokhoa;
    
    @Column
    public Date ngaythuchien;
    
    @Column
    public String tomtatdienbien;
    
    @Column
    public String ketluanhoichan;
    
    @Column
    public String huongdieutri;
    
    @Column
    public Boolean daxoa;
    
    @Transient public List<EmrHoiDongHoiChan> emrThanhVienHoiChans = new ArrayList<>();
    
    @Transient
    public List<EmrFileDinhKem> emrFileDinhKemHoiChans = new ArrayList<>();
    
    @Transient public String tenKhoa;
    @Transient public String giuong;
    @Transient public String phong;
    
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
        return emrThanhVienHoiChans;
    }
    
    public List<EmrFileDinhKem> getEmrQuanLyFileDinhKemHoiChans() {
        return emrFileDinhKemHoiChans;
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
