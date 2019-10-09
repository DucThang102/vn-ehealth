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
@Table(name = "emr_hinh_anh_ton_thuong")
public class EmrHinhAnhTonThuong {
    
    @Id public int id;
    
    @Column public Integer idhsba;
    
    @Column public String motatonthuong;
    @Column public String anhtonthuong;
    @Column public String dinhdanganh;
    @Column public Boolean daxoa;
    @Column public Date ngaytao;
    @Column public Integer idnguoitao;
    @Column public Date ngaysua;
    @Column public Integer idnguoisua;
    
    @Transient public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemHatts = new ArrayList<>();

    public int getId() {
        return id;
    }

    public Integer getIdhsba() {
        return idhsba;
    }

    public String getMotatonthuong() {
        return motatonthuong;
    }

    public String getAnhtonthuong() {
        return anhtonthuong;
    }

    public String getDinhdanganh() {
        return dinhdanganh;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public Integer getIdnguoitao() {
        return idnguoitao;
    }

    public Date getNgaysua() {
        return ngaysua;
    }

    public Integer getIdnguoisua() {
        return idnguoisua;
    }

    public List<EmrQuanLyFileDinhKem> getEmrQuanLyFileDinhKemHatts() {
        return emrQuanLyFileDinhKemHatts;
    }    
}
