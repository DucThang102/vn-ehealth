package vn.ehealth.emr;

import java.util.Date;

public class EmrYhctDonThuocChiTiet {
    
    public int id;
    public Integer idyhctdonthuoc;
    public EmrDm emrDmYhctViThuoc;
    public EmrDm emrDmDuongDungThuoc;
    public EmrDm emrDmChiDanDungThuoc;
    public EmrDm emrDmTanXuatDungThuoc;
    public Date ngaybatdau;
    public Date ngayketthuc;
    public String lieuluongdung;
    public Boolean daxoa;
    public String chidandungthuoc;
    
    public int getId() {
        return id;
    }
    public Integer getIdyhctdonthuoc() {
        return idyhctdonthuoc;
    }
    public EmrDm getEmrDmYhctViThuoc() {
        return emrDmYhctViThuoc;
    }
    public EmrDm getEmrDmDuongDungThuoc() {
        return emrDmDuongDungThuoc;
    }
    public EmrDm getEmrDmChiDanDungThuoc() {
        return emrDmChiDanDungThuoc;
    }
    public EmrDm getEmrDmTanXuatDungThuoc() {
        return emrDmTanXuatDungThuoc;
    }
    public Date getNgaybatdau() {
        return ngaybatdau;
    }
    public Date getNgayketthuc() {
        return ngayketthuc;
    }
    public String getLieuluongdung() {
        return lieuluongdung;
    }
    public Boolean getDaxoa() {
        return daxoa;
    }
    public String getChidandungthuoc() {
        return chidandungthuoc;
    }
}
