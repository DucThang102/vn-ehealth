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
@Table(name = "emr_yhct_don_thuoc")
public class EmrYhctDonThuoc {

    @Id public int id;
    @Column public Integer idhsba;
    @Column public Date ngaykedon;
    @Column public String bacsikedon;
    @Column public String sodon;
    @Column public Boolean daxoa;
    
    @Transient public List<EmrYhctDonThuocChiTiet> emrYhctDonThuocChiTiets = new ArrayList<>();
    
    @Transient public List<EmrFileDinhKem> emrFileDinhKemYhctDonThuocs = new ArrayList<>();
    
    // NGAY 14.4.2015
    @Column public Date ngaybatdaudung;
    @Column public Date ngayketthuc;
    @Column public Integer soluongthang;
    @Column public String chidan;
    
    public int getId() {
        return id;
    }
    
    public Integer getIdhsba() {
        return idhsba;
    }
    
    public Date getNgaykedon() {
        return ngaykedon;
    }
    
    public String getBacsikedon() {
        return bacsikedon;
    }
    
    public String getSodon() {
        return sodon;
    }
    
    public Boolean getDaxoa() {
        return daxoa;
    }
    
    public List<EmrYhctDonThuocChiTiet> getEmrYhctDonThuocChiTiets() {
        return emrYhctDonThuocChiTiets;
    }
    
    public List<EmrFileDinhKem> getEmrQuanLyFileDinhKemDonThuocYhcts() {
        return emrFileDinhKemYhctDonThuocs;
    }
    
    public Date getNgaybatdaudung() {
        return ngaybatdaudung;
    }
    
    public Date getNgayketthuc() {
        return ngayketthuc;
    }
    
    public Integer getSoluongthang() {
        return soluongthang;
    }
    
    public String getChidan() {
        return chidan;
    }    
}
