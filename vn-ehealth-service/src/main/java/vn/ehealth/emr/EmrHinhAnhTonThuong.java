package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrHinhAnhTonThuong {
    
    public int id;
    
    public Integer idhsba;
    //public EmrDanhSachHoSoBenhAn emrDanhSachHoSoBenhAn;
    
    public String motatonthuong;
    public String anhtonthuong;
    public String dinhdanganh;
    public Boolean daxoa;
    public Date ngaytao;
    public Integer idnguoitao;
    public Date ngaysua;
    public Integer idnguoisua;
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemHatts = new ArrayList<>();

}
