package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_tinh_trang_ra_vien")
public class EmrTinhTrangRaVien {

    @Id
    public int idhsba;
    
    @Column(name = "idicd_nguyennhantuvong")
    public Integer idicdNguyennhantuvong;
    
    @Transient
    public EmrDm emrDmNguyennhantuvong;
    
    @Column
    public Integer idketquadieutri;
    
    @Transient
    public EmrDm emrDmKetQuaDieuTri;
    
    @Column
    public Integer idyhctketquadieutri;
    
    @Transient
    public EmrDm emrDmYhctKetQuaDieuTri;
    
    @Column(name = "idicd_giaiphaututhi")
    public Integer idicdGiaiphaututhi;
    
    @Transient
    public EmrDm emrDmGiaiphaututhi;
    
    @Column
    public Integer idgiaiphaubenh;
    
    @Transient
    public EmrDm emrDmKetQuaGiaiPhauBenh;
    
    @Column
    public Integer idthoidiemtuvong;
    
    @Transient
    public EmrDm emrDmThoiDiemTuVong;
    
    @Column
    public Integer idlydotuvong;
    
    @Transient
    public EmrDm emrDmLyDoTuVong;
    
    @Column
    public Date ngaygiotuvong;
    
    @Column
    public Boolean khamnghiemtuthi;
    
    @Column
    public String motanguyennhantuvong;
    
    @Column
    public String motagiaiphaututhi;
    
    @Column
    public Boolean daxoa;
    
    @Column
    public Date ngaytao;
    
    @Column
    public Integer idnguoitao;
    
    @Column
    public Date ngaysua;
    
    @Column
    public Integer idnguoisua;
}
