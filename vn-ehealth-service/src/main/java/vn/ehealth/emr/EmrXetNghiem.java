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
@Table(name = "emr_xet_nghiem")
public class EmrXetNghiem {
    
    @Id public int id;
    
    @Column public Integer idloaixetnghiem;
    @Transient public EmrDm emrDmLoaiXetNghiem;
    
    @Column public Integer idhsba;
    
    @Column public Integer idxetnghiem;
    
    @Column public Date ngayyeucau;
    @Column public String bacsiyeucau;
    @Column public Date ngaythuchien;
    @Column public String noidungyeucau;
    @Column public String tailieudinhkem;
    @Column public String nhanxet;
    @Column public String bacsixetnghiem;
    
    @Transient public List<EmrXetNghiemDichVu> emrXetNghiemDichVus = new ArrayList<>();
    
    @Transient public List<EmrFileDinhKem> emrFileDinhKemXetNghiems = new ArrayList<>();
    
    @Column public Boolean daxoa;

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

    public List<EmrFileDinhKem> getEmrQuanLyFileDinhKemXn() {
        return emrFileDinhKemXetNghiems;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }    
    
    
}
