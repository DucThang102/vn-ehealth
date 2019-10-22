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
@Table(name = "emr_don_thuoc")
public class EmrDonThuoc {
    
    @Id public int id;
    
    @Column public Integer idhsba;
    
    @Column public Date ngaykedon;
    @Column public String bacsikedon;
    @Column public String sodon;
    
    @Column public Boolean daxoa;
    
    @Transient public List<EmrDonThuocChiTiet> emrDonThuocChiTiets = new ArrayList<>(); 
    
    @Transient public List<EmrFileDinhKem> emrFileDinhKemDonThuocs = new ArrayList<>();

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

    public List<EmrDonThuocChiTiet> getEmrDonThuocChiTiets() {
        return emrDonThuocChiTiets;
    }

    public List<EmrFileDinhKem> getEmrQuanLyFileDinhKemDonThuocs() {
        return emrFileDinhKemDonThuocs;
    }
}
