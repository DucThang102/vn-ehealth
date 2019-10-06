package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrChanDoanHinhAnh {

    public int id;
    
    public Integer idhsba;
    
    public Integer idloaichandoan;
    public EmrDm emrDmLoaiChanDoanHinhAnh;
    
    public Integer iddichvuchandoan;
    public EmrDm emrDmChanDoanHinhAnh;
    
    public Date ngayyeucau;
    public String bacsiyeucau;
    public Date ngaythuchien;
    public String ketqua;
    public String ketluan;
    public String loidan;
    public String bacsichuyenkhoa;
    public Boolean daxoa;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemCdha = new ArrayList<>();

    public int getId() {
        return id;
    }

    public Integer getIdhsba() {
        return idhsba;
    }

    public Integer getIdloaichandoan() {
        return idloaichandoan;
    }

    public EmrDm getEmrDmLoaiChanDoanHinhAnh() {
        return emrDmLoaiChanDoanHinhAnh;
    }

    public Integer getIddichvuchandoan() {
        return iddichvuchandoan;
    }

    public EmrDm getEmrDmChanDoanHinhAnh() {
        return emrDmChanDoanHinhAnh;
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

    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemCdha() {
        return emrQuanLyFileDinhKemCdha;
    }

}
