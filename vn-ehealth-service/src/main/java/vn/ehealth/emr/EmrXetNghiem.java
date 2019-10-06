package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrXetNghiem {
    
    public int id;
    
    public Integer idloaixetnghiem;
    public EmrDm emrDmLoaiXetNghiem;
    
    public Integer idhsba;
    
    public Integer idxetnghiem;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public Date ngaythuchien;
    public String noidungyeucau;
    public String tailieudinhkem;
    public String nhanxet;
    public String bacsixetnghiem;
    
    public List<EmrXetNghiemDichVu> emrXetNghiemDichVus = new ArrayList<>();
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemXn = new ArrayList<>();
    
    public Boolean daxoa;

    public int getId() {
        return id;
    }

    public Integer getIdloaixetnghiem() {
        return idloaixetnghiem;
    }

    public EmrDm getEmrDmLoaiXetNghiem() {
        return emrDmLoaiXetNghiem;
    }

    public Integer getIdhsba() {
        return idhsba;
    }

    public Integer getIdxetnghiem() {
        return idxetnghiem;
    }

    public Date getNgayyeucau() {
        return ngayyeucau;
    }

    public String getBacsiyeucau() {
        return bacsiyeucau;
    }

    public Date getNgaythuchien() {
        return ngaythuchien;
    }

    public String getNoidungyeucau() {
        return noidungyeucau;
    }

    public String getTailieudinhkem() {
        return tailieudinhkem;
    }

    public String getNhanxet() {
        return nhanxet;
    }

    public String getBacsixetnghiem() {
        return bacsixetnghiem;
    }

    public List<EmrXetNghiemDichVu> getEmrXetNghiemDichVus() {
        return emrXetNghiemDichVus;
    }

    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemXn() {
        return emrQuanLyFileDinhKemXn;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }    
    
    
}
