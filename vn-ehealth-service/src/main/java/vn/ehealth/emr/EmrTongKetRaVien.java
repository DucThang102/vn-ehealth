package vn.ehealth.emr;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_tong_ket_ra_vien")
public class EmrTongKetRaVien {

    @Id
    public int idhsba;
   
    @Column
    public String dienbienlamsang;
    
    @Column
    public String canlamsang;
    
    @Column
    public String phuongphapdieutri;
    
    @Column
    public String tinhtrangnguoibenh;
    
    @Column
    public String chidandieutri;
    
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
    
    @Column
    public String nguoigiaohoso;
    
    @Column
    public Date ngaygiaohoso;
    
    @Column
    public String nguoinhanhoso;
    
    @Column
    public Date ngaynhanhoso;
    
    @Column
    public String bacsydieutri;
    
    @Column
    public Date ngaybacsydieutriky;
    
    @Column
    public Integer so_to_x_quang;
    
    @Column(name = "so_to_ct_scanner")  
    public Integer soToCTScanner;
    
    @Column(name = "so_to_sieu_am") 
    public Integer soToSieuAm;
    
    @Column(name = "so_to_xet_nghiem")
    public Integer soToXetNghiem;
    
    @Column(name = "so_to_khac")
    public Integer soToKhac;
}
