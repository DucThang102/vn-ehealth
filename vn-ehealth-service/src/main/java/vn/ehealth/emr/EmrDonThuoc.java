package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrDonThuoc {
    public int id;
    
    public Integer idhsba;
    
    public Date ngaykedon;
    public String bacsikedon;
    public String sodon;
    
    public Boolean daxoa;
    
    public List<EmrDonThuocChiTiet> emrDonThuocChiTiets = new ArrayList<>(); 
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemDonThuocs = new ArrayList<>();

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

    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemDonThuocs() {
        return emrQuanLyFileDinhKemDonThuocs;
    }

    
    
}
