package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_don_thuoc_chi_tiet")
public class EmrDonThuocChiTiet {

    @Id public int id;
    
    @Column public Integer iddonthuoc;    
    
    @Column public Integer idloaiduongdung;
    @Transient public EmrDm emrDmDuongDungThuoc;
    
    @Column public Integer idthuoc;
    @Transient public EmrDm emrDmThuoc;
    
    @Column public Integer idtanxuatdung;
    @Transient public EmrDm emrDmTanXuatDungThuoc;
    
    @Column public Date ngaybatdau;
    @Column public Date ngayketthuc;
    @Column public String lieuluongdung;
    
    @Column public String chidandungthuoc;
    
    @Column public Boolean daxoa;
    
    @Column public String bietduoc;
    
    @Column public Integer idchidandungthuoc;
    @Transient public EmrDm emrDmChiDanDungThuoc;

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
