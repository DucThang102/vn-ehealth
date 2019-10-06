package vn.ehealth.emr;

import java.util.Date;

public class EmrDonThuocChiTiet {

    public int id;
    
    public Integer iddonthuoc;    
    
    public Integer idloaiduongdung;
    public EmrDm emrDmDuongDungThuoc;
    
    public Integer idthuoc;
    public EmrDm emrDmThuoc;
    
    public Integer idtanxuatdung;
    public EmrDm emrDmTanXuatDungThuoc;
    
    public Date ngaybatdau;
    public Date ngayketthuc;
    public String lieuluongdung;
    
    public String chidandungthuoc;
    
    public Boolean daxoa;
    
    public String bietduoc;
    
    public Integer idchidandungthuoc;

    public int getId() {
        return id;
    }

    public Integer getIddonthuoc() {
        return iddonthuoc;
    }

    public Integer getIdloaiduongdung() {
        return idloaiduongdung;
    }

    public EmrDm getEmrDmDuongDungThuoc() {
        return emrDmDuongDungThuoc;
    }

    public Integer getIdthuoc() {
        return idthuoc;
    }

    public EmrDm getEmrDmThuoc() {
        return emrDmThuoc;
    }

    public Integer getIdtanxuatdung() {
        return idtanxuatdung;
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

    public String getChidandungthuoc() {
        return chidandungthuoc;
    }

    public Boolean getDaxoa() {
        return daxoa;
    }

    public String getBietduoc() {
        return bietduoc;
    }

    public Integer getIdchidandungthuoc() {
        return idchidandungthuoc;
    }
    
    
}
