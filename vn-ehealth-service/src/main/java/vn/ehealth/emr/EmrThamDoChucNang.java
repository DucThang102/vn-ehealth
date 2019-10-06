package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrThamDoChucNang {
    public int id;
    
    public Integer idhsba;
    
    public Integer idthamdochucnang;
    public EmrDm emrDmThamDoChucNang;
    
    public Integer idloaithamdochucnang;
    public EmrDm emrDmLoaiThamDoChucNang;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public String noidungyeucau;
    public Date ngaythuchien;
    public String ketqua;
    public String ketluan;
    public String loidan;
    public String bacsichuyenkhoa;
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemTdcns = new ArrayList<>();

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
