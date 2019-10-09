package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "emr_quan_ly_nguoi_benh")
public class EmrQuanLyNguoiBenh {

    @Id
    public int idhsba;
    
    @Column
    public Integer idnoigioithieu;
    
    @Transient
    public EmrDm emrDmNoiGioiThieu;
    
    @Column
    public Integer iddoituongtaichinh;
    
    @Transient
    public EmrDm emrDmLoaiDoiTuongTaiChinh;
    
    @Column
    public Integer idloairavien;
    
    @Transient
    public EmrDm emrDmLoaiRaVien;
    
    @Column
    public Integer idnoitructiepvao; 
    
    @Transient
    public EmrDm emrDmNoiTrucTiepVao;
    
    @Column
    public Integer idloaichuyenvien;
    
    @Transient
    public EmrDm emrDmLoaiChuyenVien;
    
    @Column
    public Integer idnoichuyenden;
    
    @Transient
    public EmrDm emrDmCoSoKhamBenh;
    
    @Column
    public Integer idloaivaovien;
    
    @Transient
    public EmrDm emrDmLoaiVaoVien;
        
    @Column
    public String sovaovien;
    
    @Column
    public Date ngaygiovaovien;
    
    @Column
    public Integer vaovienlanthu;
    
    @Column
    public String tenbacsikham;
    
    @Column
    public Date ngaygioravien;
    
    @Column
    public Integer tongsongaydieutri;
    
    @Column
    public String tenbacsichoravien;
    
    @Column
    public String noichuyenden;
    
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
