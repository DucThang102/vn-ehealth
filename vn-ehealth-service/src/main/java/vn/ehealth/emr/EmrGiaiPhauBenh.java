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
@Table(name = "emr_giai_phau_benh")
public class EmrGiaiPhauBenh {

    @Id public int id;
    
    @Column public Integer idhsba;
    
    @Column public Integer idvitrilaymau;
    @Transient public EmrDm emrDmViTriLayMau;
    
    @Column public Integer idloaigiaiphau;
    @Transient public EmrDm emrDmLoaiGiaiPhauBenh;
    @Transient public EmrDm emrDmKetQuaGiaiPhauBenh;
    
    @Column public Integer iddichvugiaiphau;
    @Transient public EmrDm emrDmGiaiPhauBenh;
    
    @Column public Date ngayyeucau;
    @Column public String bacsiyeucau;
    @Column public String bacsichuyenkhoa;
    @Column public Date ngaythuchien;
    @Column public String nhanxetdaithe;
    @Column public String nhanxetvithe;
    @Column public String motachandoangiaiphau;
    @Column public Date ngaylaymausinhthiet;
    @Column public Boolean daxoa;
    
    @Transient public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemGpbs = new ArrayList<>();

    public int getId() {
        return id;
    }

    public Integer getIdhsba() {
        return idhsba;
    }

    public Integer getIdvitrilaymau() {
        return idvitrilaymau;
    }

    public EmrDm getEmrDmViTriLayMau() {
        return emrDmViTriLayMau;
    }

    public Integer getIdloaigiaiphau() {
        return idloaigiaiphau;
    }

    public EmrDm getEmrDmLoaiGiaiPhauBenh() {
        return emrDmLoaiGiaiPhauBenh;
    }

    public EmrDm getEmrDmKetQuaGiaiPhauBenh() {
        return emrDmKetQuaGiaiPhauBenh;
    }

    public Integer getIddichvugiaiphau() {
        return iddichvugiaiphau;
    }

    public EmrDm getEmrDmGiaiPhauBenh() {
        return emrDmGiaiPhauBenh;
    }

    public Date getNgayyeucau() {
        return ngayyeucau;
    }

    public String getBacsiyeucau() {
        return bacsiyeucau;
    }

    public String getBacsichuyenkhoa() {
        return bacsichuyenkhoa;
    }

    public Date getNgaythuchien() {
        return ngaythuchien;
    }

    public String getNhanxetdaithe() {
        return nhanxetdaithe;
    }

    public String getNhanxetvithe() {
        return nhanxetvithe;
    }

    public String getMotachandoangiaiphau() {
        return motachandoangiaiphau;
    }

    public Date getNgaylaymausinhthiet() {
        return ngaylaymausinhthiet;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }

    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemGpbs() {
        return emrQuanLyFileDinhKemGpbs;
    }    
}
