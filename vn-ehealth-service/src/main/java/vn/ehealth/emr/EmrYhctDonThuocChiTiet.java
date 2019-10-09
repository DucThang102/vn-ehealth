package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_yhct_don_thuoc_chi_tiet")
public class EmrYhctDonThuocChiTiet {
    
    @Id public int id;
    @Column public Integer idyhctdonthuoc;
    
    @Column public Integer idvithuoc;
    @Transient public EmrDm emrDmYhctViThuoc;
    
    @Column public Integer idloaiduongdung;
    @Transient public EmrDm emrDmDuongDungThuoc;
    
    @Column public Integer idchidandungthuoc;
    @Transient public EmrDm emrDmChiDanDungThuoc;
    
    @Column public Integer idtanxuatdung;
    @Transient public EmrDm emrDmTanXuatDungThuoc;
    
    @Column public Date ngaybatdau;
    @Column public Date ngayketthuc;
    @Column public String lieuluongdung;
    @Column public Boolean daxoa;
    @Column public String chidandungthuoc;
    
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
