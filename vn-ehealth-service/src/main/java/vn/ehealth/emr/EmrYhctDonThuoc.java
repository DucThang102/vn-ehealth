package vn.ehealth.emr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.ehealth.emr.file.EmrQuanLyFileDinhKem;

public class EmrYhctDonThuoc {

    public int id;
    public Integer idhsba;
    public Date ngaykedon;
    public String bacsikedon;
    public String sodon;
    public Boolean daxoa;
    public List<EmrYhctDonThuocChiTiet> emrYhctDonThuocChiTiets = new ArrayList<>();
    
    public List<EmrQuanLyFileDinhKem> emrQuanLyFileDinhKemDonThuocYhcts = new ArrayList<>();
    
    // NGAY 14.4.2015
    public Date ngaybatdaudung;
    public Date ngayketthuc;
    public Integer soluongthang;
    public String chidan;
}
