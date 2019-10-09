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
@Table(name = "emr_chan_doan_hinh_anh")
public class EmrChanDoanHinhAnh {

    @Id public int id;
    
    @Column public Integer idhsba;
    
    @Column public Integer idloaichandoan;
    @Transient public EmrDm emrDmLoaiChanDoanHinhAnh;
    
    @Column public Integer iddichvuchandoan;
    @Transient public EmrDm emrDmChanDoanHinhAnh;
    
    @Column public Date ngayyeucau;
    @Column public String bacsiyeucau;
    @Column public Date ngaythuchien;
    @Column public String ketqua;
    @Column public String ketluan;
    @Column public String loidan;
    @Column public String bacsichuyenkhoa;
    @Column public Boolean daxoa;
    
    @Transient public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemCdha = new ArrayList<>();

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
