package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrHinhAnhTonThuong {
    
    public int id;
    
    public Integer idhsba;
    
    public String motatonthuong;
    public String anhtonthuong;
    public String dinhdanganh;
    public Boolean daxoa;
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemHatts = new ArrayList<>();

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
