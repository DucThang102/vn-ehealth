package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

@Entity
@Table(name = "emr_tham_do_chuc_nang")
public class EmrThamDoChucNang {
    
    @Id public int id;
    
    @Column public Integer idhsba;
    
    @Column public Integer idthamdochucnang;
    @Transient public EmrDm emrDmThamDoChucNang;
    
    @Column public Integer idloaithamdochucnang;
    @Transient public EmrDm emrDmLoaiThamDoChucNang;
    
    @Column public Date ngayyeucau;
    @Column public String bacsiyeucau;
    @Column public String noidungyeucau;
    @Column public Date ngaythuchien;
    @Column public String ketqua;
    @Column public String ketluan;
    @Column public String loidan;
    @Column public String bacsichuyenkhoa;
    @Column public Boolean daxoa;
    
    @Transient public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemTdcns = new ArrayList<>();

    public int getId() {
        return id;
    }

    public Integer getIdhsba() {
        return idhsba;
    }

    public Integer getIdthamdochucnang() {
        return idthamdochucnang;
    }

    public EmrDm getEmrDmThamDoChucNang() {
        return emrDmThamDoChucNang;
    }

    public Integer getIdloaithamdochucnang() {
        return idloaithamdochucnang;
    }

    public EmrDm getEmrDmLoaiThamDoChucNang() {
        return emrDmLoaiThamDoChucNang;
    }

    public Date getNgayyeucau() {
        return ngayyeucau;
    }

    public String getBacsiyeucau() {
        return bacsiyeucau;
    }

    public String getNoidungyeucau() {
        return noidungyeucau;
    }

    public Date getNgaythuchien() {
        return ngaythuchien;
    }

    public String getKetqua() {
        return ketqua;
    }

    public String getKetluan() {
        return ketluan;
    }

    public String getLoidan() {
        return loidan;
    }

    public String getBacsichuyenkhoa() {
        return bacsichuyenkhoa;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }

    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemTdcns() {
        return emrQuanLyFileDinhKemTdcns;
    }
}
