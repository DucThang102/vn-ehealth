package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrGiaiPhauBenh {

    public int id;
    
    public Integer idhsba;
    
    public Integer idvitrilaymau;
    public EmrDm emrDmViTriLayMau;
    
    public Integer idloaigiaiphau;
    public EmrDm emrDmLoaiGiaiPhauBenh;
    public EmrDm emrDmKetQuaGiaiPhauBenh;
    
    public Integer iddichvugiaiphau;
    public EmrDm emrDmGiaiPhauBenh;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public String bacsichuyenkhoa;
    public Date ngaythuchien;
    public String nhanxetdaithe;
    public String nhanxetvithe;
    public String motachandoangiaiphau;
    public Date ngaylaymausinhthiet;
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemGpbs = new ArrayList<>();

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
