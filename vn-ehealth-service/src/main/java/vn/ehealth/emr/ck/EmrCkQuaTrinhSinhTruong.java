package vn.ehealth.emr.ck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "emr_ck_qua_trinh_sinh_truong")
public class EmrCkQuaTrinhSinhTruong {

    @Id public int idhsba;
    @Column public Integer idchuyenkhoa;
    
    @Column public Integer conthu;
    
    @Column public Boolean duthang;
    
    @Column public Boolean denon;
    
    @Column public Boolean say;
    
    @Column public Boolean song;
    
    @Column public Boolean dethuong;
    
    @Column public Boolean forceps;
    
    @Column public Boolean giachut;
    
    @Column public Boolean dephauthuat;
    
    @Column public Boolean dechihuy;
    
    @Column public Boolean dekhac;
    
    @Column public Double cannangsinh;
    
    @Column public Boolean coditat;
    
    @Column public String motaditat;
    
    @Column public String phattrientinhthan;
    
    @Column public String phattrienvandong;
    
    @Column public String benhlykhac;
    
    @Column public Boolean suame;
    
    @Column public Boolean suagoai;
    
    @Column public Boolean honhop;
    
    @Column public Integer thangcaisua;
    
    @Column public Boolean tainha;
    
    @Column public Boolean vuontre;
    
    @Column public String paraDuthang;
    @Column public String paraDenon;
    @Column public String paraSay;
    @Column public String paraSong;
}
