package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_tinh_trang_so_sinh")
public class EmrCkTinhTrangSoSinh {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    
    @Column public Boolean sskhocngay;
    
    @Column public Boolean ssngat;
    
    @Column public Boolean sskhac;
    
    @Column public String nguoidode;
    
    @Column public Integer apgar01;
    
    @Column public Integer apgar05;
    
    @Column public Integer apgar10;
    
    @Column public Double cannang;
    
    @Column public String tinhtrangdinhduong;
    
    @Column public String tennguoichuyensosinh;
    
    @Column public Integer tuoi;
}
